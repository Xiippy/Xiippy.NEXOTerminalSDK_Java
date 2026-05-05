// *******************************************************************************************
// Copyright © 2019 Xiippy.ai. All rights reserved. Australian patents awarded. PCT patent pending.
//
// NOTES:
//
// - No payment gateway SDK function is consumed directly. Interfaces are defined out of such
//   interactions and then the interface is implemented for payment gateways. Design the interface
//   with the most common members and data structures between different gateways.
// - A proper factory or provider must instantiate an instance of the interface that is interacted with.
// - Any major change made to SDKs should begin with the C# SDK with the mindset to keep the
//   high-level syntax, structures and class names the same to minimise porting efforts to other languages.
//   Do not use language specific features that do not exist in other languages.
// - Pascal Case for naming conventions should be used for all languages.
// - No secret or passwords or keys must exist in the code when checked in.
//
// *******************************************************************************************

package io.github.xiippy.nexoterminalsdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xiippy.nexoterminalsdk.models.NexoAbortRequest;
import io.github.xiippy.nexoterminalsdk.models.NexoCompletionRequest;
import io.github.xiippy.nexoterminalsdk.models.NexoPaymentRequest;
import io.github.xiippy.nexoterminalsdk.models.NexoReversalRequest;
import io.github.xiippy.nexoterminalsdk.utils.RequestSignatureHandlerClientSide;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * HTTP client for the Xiippy NEXO Terminal API.
  * Mirrors the C# NexoApiClient class.  Every outbound request is signed with
 * an Ed25519 private key and carries three custom headers:
 * <ul>
 *   <li>{@code client-id} – the caller's client identifier</li>
 *   <li>{@code client-request-signature} – hex-encoded Ed25519 signature of {@code body#moment}</li>
 *   <li>{@code request-moment} – Unix timestamp in milliseconds at the time of the call</li>
 * </ul>
 * Responses from payment, reversal, and completion endpoints are delivered
 * asynchronously via webhook; only abort returns a synchronous outcome.
 
 */
public class NexoApiClient {

    private static final String ApplicationJson = "application/json";

    private final String ApiBaseUrl;
    private final String ClientId;
    private final byte[] Ed25519PrivateKey;
    private final HttpClient HttpClientInstance;
    private final ObjectMapper JsonMapper;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new NexoApiClient.
     *
     * @param ApiBaseUrl       The base URL of the NEXO API (e.g. {@code https://api.example.com}).
     * @param ClientId         The caller's client identifier.
     * @param Ed25519PrivateKey The raw Ed25519 private key bytes used to sign requests.
     */
    public NexoApiClient(String ApiBaseUrl, String ClientId, byte[] Ed25519PrivateKey) {
        if (ApiBaseUrl == null || ApiBaseUrl.isBlank())
            throw new IllegalArgumentException("API base URL cannot be null or empty");
        if (ClientId == null || ClientId.isBlank())
            throw new IllegalArgumentException("Client ID cannot be null or empty");
        if (Ed25519PrivateKey == null || Ed25519PrivateKey.length == 0)
            throw new IllegalArgumentException("Ed25519 private key cannot be null or empty");

        this.ApiBaseUrl = ApiBaseUrl.replaceAll("/+$", ""); // strip trailing slashes
        this.ClientId = ClientId;
        this.Ed25519PrivateKey = Ed25519PrivateKey;
        this.HttpClientInstance = HttpClient.newHttpClient();
        this.JsonMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // -------------------------------------------------------------------------
    // Public API methods
    // -------------------------------------------------------------------------

    /**
     * Creates a payment request. The response is sent asynchronously via webhook.
     *
     * @param PaymentRequest The payment request containing transaction details.
     * @return The {@link HttpResponse} carrying the initial API response.
     * @throws IOException          If serialisation or I/O fails.
     * @throws InterruptedException If the calling thread is interrupted.
     */
    public HttpResponse<String> CreatePaymentAsync(NexoPaymentRequest PaymentRequest)
            throws IOException, InterruptedException {
        if (PaymentRequest == null)
            throw new IllegalArgumentException("PaymentRequest must not be null");

        return SendRequest("POST", "/api/Nexo/payment", PaymentRequest);
    }

    /**
     * Creates a reversal request to cancel or reverse a previous transaction.
     * The response is sent asynchronously via webhook.
     *
     * @param ReversalRequest The reversal request.
     * @return The {@link HttpResponse} carrying the initial API response.
     * @throws IOException          If serialisation or I/O fails.
     * @throws InterruptedException If the calling thread is interrupted.
     */
    public HttpResponse<String> CreateReversalAsync(NexoReversalRequest ReversalRequest)
            throws IOException, InterruptedException {
        if (ReversalRequest == null)
            throw new IllegalArgumentException("ReversalRequest must not be null");

        return SendRequest("POST", "/api/Nexo/reversal", ReversalRequest);
    }

    /**
     * Creates a completion request to finalise a previously authorised transaction.
     * The response is sent asynchronously via webhook.
     *
     * @param CompletionRequest The completion request.
     * @return The {@link HttpResponse} carrying the initial API response.
     * @throws IOException          If serialisation or I/O fails.
     * @throws InterruptedException If the calling thread is interrupted.
     */
    public HttpResponse<String> CreateCompletionAsync(NexoCompletionRequest CompletionRequest)
            throws IOException, InterruptedException {
        if (CompletionRequest == null)
            throw new IllegalArgumentException("CompletionRequest must not be null");

        return SendRequest("POST", "/api/Nexo/completion", CompletionRequest);
    }

    /**
     * Creates an abort request to cancel an in-progress terminal transaction.
     *
     * @param AbortRequest The abort request.
     * @return The {@link HttpResponse} carrying the API response.
     * @throws IOException          If serialisation or I/O fails.
     * @throws InterruptedException If the calling thread is interrupted.
     */
    public HttpResponse<String> CreateAbortionAsync(NexoAbortRequest AbortRequest)
            throws IOException, InterruptedException {
        if (AbortRequest == null)
            throw new IllegalArgumentException("AbortRequest must not be null");

        return SendRequest("POST", "/api/Nexo/abort", AbortRequest);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Serialises {@code RequestBody} to JSON, signs it, attaches the required
     * custom headers, and sends the HTTP request.
     */
    private HttpResponse<String> SendRequest(String Method, String Endpoint, Object RequestBody)
            throws IOException, InterruptedException {

        String JsonContent = JsonMapper.writeValueAsString(RequestBody);
        byte[] BodyBytes   = JsonContent.getBytes(StandardCharsets.UTF_8);

        long RequestMoment = Instant.now().toEpochMilli();

        byte[] Signature    = RequestSignatureHandlerClientSide.GenerateSignatureForRequest(
                BodyBytes, RequestMoment, Ed25519PrivateKey);
        String SignatureHex = ByteArrayToHex(Signature);

        HttpRequest Request = HttpRequest.newBuilder()
                .uri(URI.create(ApiBaseUrl + Endpoint))
                .header("Content-Type", ApplicationJson)
                .header("Accept",       ApplicationJson)
                .header("client-id",                ClientId)
                .header("client-request-signature", SignatureHex)
                .header("request-moment",           Long.toString(RequestMoment))
                .method(Method, HttpRequest.BodyPublishers.ofByteArray(BodyBytes))
                .build();

        return HttpClientInstance.send(Request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Converts a byte array to a lowercase hexadecimal string.
     * Mirrors C# {@code BitConverter.ToString(bytes).Replace("-","").ToLower()}.
     */
    private static String ByteArrayToHex(byte[] Bytes) {
        StringBuilder Sb = new StringBuilder(Bytes.length * 2);
        for (byte B : Bytes) {
            Sb.append(String.format("%02x", B));
        }
        return Sb.toString();
    }
}
