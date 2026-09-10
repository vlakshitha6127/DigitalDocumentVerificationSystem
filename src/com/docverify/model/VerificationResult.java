package com.docverify.model;
 
import java.io.Serializable;
import java.time.LocalDateTime;
 
public class VerificationResult implements Serializable {
    private static final long serialVersionUID = 1L;
 
    private final String documentId;
    private final VerificationStatus status;
    private final String message;
    private final LocalDateTime verificationTime;
 
    public VerificationResult(String documentId, VerificationStatus status,
                               String message, LocalDateTime verificationTime) {
        this.documentId = documentId;
        this.status = status;
        this.message = message;
        this.verificationTime = verificationTime;
    }
 
    public String getDocumentId() { return documentId; }
    public VerificationStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getVerificationTime() { return verificationTime; }
}
