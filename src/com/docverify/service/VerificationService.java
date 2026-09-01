package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;
import com.docverify.model.VerificationStatus;

import java.time.LocalDateTime;

public class VerificationService {

    private DocumentService documentService;

    public VerificationService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public VerificationResult verifyDocument(String documentId)
            throws DocumentNotFoundException {

        Document document =
                documentService.findDocumentById(documentId);

        VerificationResult result = new VerificationResult(
                document.getDocumentId(),
                VerificationStatus.GENUINE,
                "Document found successfully.",
                LocalDateTime.now()
        );

        document.addVerification(result);

        return result;
    }
}