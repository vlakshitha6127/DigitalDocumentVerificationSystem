package com.docverify.service;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {

    private List<Document> documents;

    public DocumentService() {
        documents = new ArrayList<>();
    }

    public void registerDocument(Document document) {
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