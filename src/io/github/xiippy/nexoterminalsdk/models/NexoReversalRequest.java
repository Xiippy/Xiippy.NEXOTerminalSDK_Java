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

import jakarta.validation.constraints.NotNull;

/**
 * Represents a request to reverse (cancel) a previously processed NEXO terminal transaction.
 */
public class NexoReversalRequest {

    @NotNull
    private String RandomStatementID;

    @NotNull
    private String StatementTimeStamp;

    @NotNull
    private String MerchantGroupID;

    /** Optional partial-reversal amount; null reverses the full original amount. */
    private Float Amount;

    private String ExternalUniqueID;

    /** Optional human-readable reason for the reversal. */
    private String Reason;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public NexoReversalRequest() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    public String getMerchantGroupID() {
        return MerchantGroupID;
    }

    public void setMerchantGroupID(String MerchantGroupID) {
        this.MerchantGroupID = MerchantGroupID;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float Amount) {
        this.Amount = Amount;
    }

    public String getExternalUniqueID() {
        return ExternalUniqueID;
    }

    public void setExternalUniqueID(String ExternalUniqueID) {
        this.ExternalUniqueID = ExternalUniqueID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
}
