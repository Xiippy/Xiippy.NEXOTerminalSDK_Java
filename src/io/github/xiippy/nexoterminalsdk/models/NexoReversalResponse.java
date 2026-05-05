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
 * Represents a response to a NEXO reversal request.
 */
public class NexoReversalResponse {

    private String NexoReversalResponseID;

    private String RandomStatementID;

    private String StatementTimeStamp;

    private String ExternalUniqueID;

    private boolean Success;

    private String Message;

    private Map<String, String> Data;

    private long Timestamp;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public NexoReversalResponse() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getNexoReversalResponseID() {
        return NexoReversalResponseID;
    }

    public void setNexoReversalResponseID(String NexoReversalResponseID) {
        this.NexoReversalResponseID = NexoReversalResponseID;
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

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Map<String, String> getData() {
        return Data;
    }

    public void setData(Map<String, String> Data) {
        this.Data = Data;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long Timestamp) {
        this.Timestamp = Timestamp;
    }
}
