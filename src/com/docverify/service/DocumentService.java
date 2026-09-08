package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {

    private DocumentStorage documentStorage;

    private List<Document> documents;

    public DocumentService() {
        documentStorage = new DocumentStorage();
        documents = documentStorage.loadDocuments();
    }

    public void registerDocument(Document document)
            throws ValidationException {

        if (document == null) {
            throw new ValidationException(
                    "Document cannot be null."
            );
        }

        if (document.getDocumentId() == null ||
                document.getDocumentId().isBlank()) {

            throw new ValidationException(
                    "Document ID cannot be empty."
            );
        }

        if (document.getDocumentName() == null ||
                document.getDocumentName().isBlank()) {

            throw new ValidationException(
                    "Document name cannot be empty."
            );
        }

        documents.add(document);
        documentStorage.saveDocument(document);
    }

    public Document findDocumentById(String documentId)
            throws DocumentNotFoundException {

        for (Document document : documents) {

            if (document.getDocumentId().equals(documentId)) {
                return document;
            }
        }

        throw new DocumentNotFoundException(
                "Document not found with ID: " + documentId
        );
    }

    public List<Document> getAllDocuments() {
        return new ArrayList<>(documents);
    }

    public List<VerificationResult> getVerificationHistory(
            String documentId)
            throws DocumentNotFoundException {

        Document document =
                findDocumentById(documentId);

        return new ArrayList<>(
                document.getVerificationHistory()
        );
    }
}