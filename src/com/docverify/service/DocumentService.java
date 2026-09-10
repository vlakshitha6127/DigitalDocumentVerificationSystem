package com.docverify.service;
 
import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;
 
import java.util.ArrayList;
import java.util.List;
 
public class DocumentService {
 
    private final DocumentStorage documentStorage;
    private final List<Document> documents;
 
    public DocumentService() {
        this.documentStorage = new DocumentStorage();
        this.documents = documentStorage.loadDocuments();
    }
 
    public void registerDocument(Document document) throws ValidationException {
        validate(document);
        documents.add(document);
        documentStorage.saveAll(documents);
    }
 
    private void validate(Document document) throws ValidationException {
        if (document == null) {
            throw new ValidationException("Document cannot be null.");
        }
        if (document.getDocumentId() == null || document.getDocumentId().isBlank()) {
            throw new ValidationException("Document ID cannot be empty.");
        }
        if (document.getDocumentName() == null || document.getDocumentName().isBlank()) {
            throw new ValidationException("Document name cannot be empty.");
        }
        boolean duplicateHash = documents.stream()
                .anyMatch(d -> d.getDocumentHash().equals(document.getDocumentHash()));
        if (duplicateHash) {
            throw new ValidationException("A document with this exact content is already registered.");
        }
    }
 
    public Document findDocumentById(String documentId) throws DocumentNotFoundException {
        return documents.stream()
                .filter(d -> d.getDocumentId().equals(documentId))
                .findFirst()
                .orElseThrow(() -> new DocumentNotFoundException(
                        "Document not found with ID: " + documentId));
    }
 
    public List<Document> getAllDocuments() {
        return new ArrayList<>(documents);
    }
 
    public List<VerificationResult> getVerificationHistory(String documentId)
            throws DocumentNotFoundException {
        return new ArrayList<>(findDocumentById(documentId).getVerificationHistory());
    }
 
    /**
     * Called by VerificationService after a verification attempt so the
     * updated history is persisted, not just held in memory.
     */
    void persist() {
        documentStorage.saveAll(documents);
    }
}
