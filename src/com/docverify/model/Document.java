package com.docverify.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Document {

    private final String documentId;
    private String documentName;
    private String holderName;
    private String issuerName;
    private LocalDate issueDate;
    private String filePath;
    private String documentHash;

    private List<VerificationResult> verificationHistory;

    public Document(
            String documentId,
            String documentName,
            String holderName,
            String issuerName,
            LocalDate issueDate,
            String filePath,
            String documentHash) {

        this.documentId = documentId;
        this.documentName = documentName;
        this.holderName = holderName;
        this.issuerName = issuerName;
        this.issueDate = issueDate;
        this.filePath = filePath;
        this.documentHash = documentHash;
        this.verificationHistory = new ArrayList<>();
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDocumentHash() {
        return documentHash;
    }

    public List<VerificationResult> getVerificationHistory() {
        return verificationHistory;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDocumentHash(String documentHash) {
        this.documentHash = documentHash;
    }

    public void addVerification(VerificationResult result) {
        verificationHistory.add(result);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Document)) {
            return false;
        }

        Document other = (Document) obj;

        return Objects.equals(documentId, other.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId);
    }
}