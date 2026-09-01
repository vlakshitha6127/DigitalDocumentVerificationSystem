package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {

    private List<Document> documents;

    public DocumentService() {
        documents = new ArrayList<>();
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
}