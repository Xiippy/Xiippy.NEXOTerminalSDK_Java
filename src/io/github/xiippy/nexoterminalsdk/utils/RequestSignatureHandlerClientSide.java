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

package io.github.xiippy.nexoterminalsdk.utils;

import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

import java.nio.charset.StandardCharsets;

/**
 * Handles Ed25519 request signing on the client side.
 * Mirrors the C# RequestSignatureHandlerClientSide class exactly.
 */
public class RequestSignatureHandlerClientSide {

    /**
     * Generates a detached signature for an HTTP request by combining the
     * request body and timestamp, then signing with an Ed25519 private key.
     *
     * @param Body                 The byte array containing the HTTP request body.
     * @param MomentInMilliseconds The current time in milliseconds (Unix timestamp).
     * @param PrivateKey           The Ed25519 private key used for signing.
     * @return A byte array containing the detached Ed25519 signature.
     */
    public static byte[] GenerateSignatureForRequest(byte[] Body, long MomentInMilliseconds, byte[] PrivateKey) {
        String moment = Long.toString(MomentInMilliseconds);
        byte[] MomentBytes = moment.getBytes(StandardCharsets.UTF_8);

        byte[] DataToSign = CombineBodyAndMoment(Body, MomentBytes);

        return SignDetached(DataToSign, PrivateKey);
    }

    /**
     * Combines the request body and moment (timestamp) using the format: {body}#{moment}.
     *
     * @param Body        The byte array containing the request body.
     * @param MomentBytes The byte array containing the timestamp.
     * @return A combined byte array in the format body#moment.
     */
    public static byte[] CombineBodyAndMoment(byte[] Body, byte[] MomentBytes) {
        byte[] Separator = "#".getBytes(StandardCharsets.UTF_8);

        byte[] Combined = new byte[Body.length + Separator.length + MomentBytes.length];
        System.arraycopy(Body,        0, Combined, 0,                              Body.length);
        System.arraycopy(Separator,   0, Combined, Body.length,                    Separator.length);
        System.arraycopy(MomentBytes, 0, Combined, Body.length + Separator.length, MomentBytes.length);

        return Combined;
    }

    /**
     * Signs the message using an Ed25519 private key via BouncyCastle.
     *
     * @param Message    The data to sign.
     * @param PrivateKey The raw 32-byte (or 64-byte extended) Ed25519 private key.
     * @return The 64-byte detached Ed25519 signature.
     */
    public static byte[] SignDetached(byte[] Message, byte[] PrivateKey) {
        // BouncyCastle expects only the 32-byte seed; if a 64-byte extended key
        // is supplied (seed + public key), extract the seed portion.
        byte[] Seed = (PrivateKey.length == 64)
                ? java.util.Arrays.copyOf(PrivateKey, 32)
                : PrivateKey;

        Ed25519PrivateKeyParameters KeyParams = new Ed25519PrivateKeyParameters(Seed, 0);
        Ed25519Signer Signer = new Ed25519Signer();
        Signer.init(true, KeyParams);
        Signer.update(Message, 0, Message.length);
        return Signer.generateSignature();
    }
}
