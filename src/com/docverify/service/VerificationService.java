package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;
import com.docverify.model.VerificationStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class VerificationService {

    private final DocumentService documentService;

    public VerificationService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public VerificationResult verifyDocument(
            String documentId,
            String currentHash)
            throws DocumentNotFoundException {

        Document document =
                documentService.findDocumentById(documentId);

        VerificationStatus status;
        String message;

        if (Objects.equals(
                document.getDocumentHash(),
                currentHash)) {

            status = VerificationStatus.GENUINE;
            message = "Document is genuine.";

        } else {

            status = VerificationStatus.MODIFIED;
            message = "Document has been modified.";
        }

        VerificationResult result = new VerificationResult(
                document.getDocumentId(),
                status,
                message,
                LocalDateTime.now()
        );

        document.addVerification(result);

        return result;
    }
}