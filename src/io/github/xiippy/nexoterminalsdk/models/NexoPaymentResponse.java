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

package io.github.xiippy.nexoterminalsdk.models;

import java.util.Map;

/**
 * Represents the initial response returned when a NEXO payment request is submitted.
 * The final outcome is delivered asynchronously via webhook.
 */
public class NexoPaymentResponse {

    private String ClientSecret;

    private String ClientAuthenticator;

    private String RandomStatementID;

    private String StatementTimeStamp;

    private String ExternalUniqueID;

    private Map<String, String> Data;

    private String NexoPaymentResponseID;

    private long Timestamp;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public NexoPaymentResponse() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getClientSecret() {
        return ClientSecret;
    }

    public void setClientSecret(String ClientSecret) {
        this.ClientSecret = ClientSecret;
    }

    public String getClientAuthenticator() {
        return ClientAuthenticator;
    }

    public void setClientAuthenticator(String ClientAuthenticator) {
        this.ClientAuthenticator = ClientAuthenticator;
    }

    public String getRandomStatementID() {
        return RandomStatementID;
    }

    public void setRandomStatementID(String RandomStatementID) {
        this.RandomStatementID = RandomStatementID;
    }

    public String getStatementTimeStamp() {
        return StatementTimeStamp;
    }

    public void setStatementTimeStamp(String StatementTimeStamp) {
        this.StatementTimeStamp = StatementTimeStamp;
    }

    public String getExternalUniqueID() {
        return ExternalUniqueID;
    }

    public void setExternalUniqueID(String ExternalUniqueID) {
        this.ExternalUniqueID = ExternalUniqueID;
    }

    public Map<String, String> getData() {
        return Data;
    }

    public void setData(Map<String, String> Data) {
        this.Data = Data;
    }

    public String getNexoPaymentResponseID() {
        return NexoPaymentResponseID;
    }

    public void setNexoPaymentResponseID(String NexoPaymentResponseID) {
        this.NexoPaymentResponseID = NexoPaymentResponseID;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long Timestamp) {
        this.Timestamp = Timestamp;
    }
}
