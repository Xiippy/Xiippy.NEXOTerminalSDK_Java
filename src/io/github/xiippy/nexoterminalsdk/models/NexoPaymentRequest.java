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
import java.math.BigDecimal;

import io.github.xiippy.posecomsdk.light.models.IssuerStatementRecord;

/**
 * Represents a NEXO terminal payment request.
 * C# used decimal for Amount; Java equivalent is BigDecimal for precision.
 */
public class NexoPaymentRequest {

    @NotNull
    private String MerchantGroupID;

    @NotNull
    private String TerminalD;

    @NotNull
    private BigDecimal Amount;

    @NotNull
    private String Currency;

    @NotNull
    private Boolean IsPreAuth;

    /** Optional issuer statement record attached to this payment. */
    private IssuerStatementRecord IssuerStatementRecord;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public NexoPaymentRequest() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getMerchantGroupID() {
        return MerchantGroupID;
    }

    public void setMerchantGroupID(String MerchantGroupID) {
        this.MerchantGroupID = MerchantGroupID;
    }

    public String getTerminalD() {
        return TerminalD;
    }

    public void setTerminalD(String TerminalD) {
        this.TerminalD = TerminalD;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public Boolean getIsPreAuth() {
        return IsPreAuth;
    }

    public void setIsPreAuth(Boolean IsPreAuth) {
        this.IsPreAuth = IsPreAuth;
    }

    public IssuerStatementRecord getIssuerStatementRecord() {
        return IssuerStatementRecord;
    }

    public void setIssuerStatementRecord(IssuerStatementRecord IssuerStatementRecord) {
        this.IssuerStatementRecord = IssuerStatementRecord;
    }
}
