package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.TamperDetectedException;
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
            throws DocumentNotFoundException, TamperDetectedException {

        Document document =
                documentService.findDocumentById(documentId);

        if (!Objects.equals(
                document.getDocumentHash(),
                currentHash)) {

            VerificationResult result = new VerificationResult(
                    document.getDocumentId(),
                    VerificationStatus.MODIFIED,
                    "Document has been modified.",
                    LocalDateTime.now()
            );

            document.addVerification(result);

            throw new TamperDetectedException(
                    "Tampering detected for document: " + documentId
            );
        }

        VerificationResult result = new VerificationResult(
                document.getDocumentId(),
                VerificationStatus.GENUINE,
                "Document is genuine.",
                LocalDateTime.now()
        );

        document.addVerification(result);

        return result;
    }
}