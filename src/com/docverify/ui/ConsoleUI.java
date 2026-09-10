package com.docverify.ui;
 
import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.FileAccessException;
import com.docverify.Exception.TamperDetectedException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;
import com.docverify.service.DocumentService;
import com.docverify.service.VerificationService;
import com.docverify.util.HashUtil;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
 
/**
 * Thin console/menu layer. Owns the Scanner and all System.out calls.
 * Delegates every real decision to the service layer so this class
 * stays trivial to read and (if needed) swap for a GUI/REST layer later.
 */
public class ConsoleUI {
 
    private final Scanner scanner = new Scanner(System.in);
    private final DocumentService documentService;
    private final VerificationService verificationService;
 
    public ConsoleUI(DocumentService documentService, VerificationService verificationService) {
        this.documentService = documentService;
        this.verificationService = verificationService;
    }
 
    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> registerDocument();
                case "2" -> verifyDocument();
                case "3" -> viewVerificationHistory();
                case "4" -> viewAllDocuments();
                case "5" -> {
                    running = false;
                    System.out.println("Exiting system...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
 
    private void printMenu() {
        System.out.println("\n===== DIGITAL DOCUMENT VERIFICATION SYSTEM =====");
        System.out.println("1. Register Document");
        System.out.println("2. Verify Document");
        System.out.println("3. View Verification History");
        System.out.println("4. View All Documents");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
 
    private void registerDocument() {
        System.out.print("Enter document name: ");
        String documentName = scanner.nextLine();
        System.out.print("Enter holder name: ");
        String holderName = scanner.nextLine();
        System.out.print("Enter issuer name: ");
        String issuerName = scanner.nextLine();
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();
 
        try {
            String documentHash = HashUtil.generateFileHash(filePath);
            Document document = new Document(
                    documentName, holderName, issuerName,
                    LocalDate.now(), filePath, documentHash
            );
            documentService.registerDocument(document);
            System.out.println("\nDocument registered successfully.");
            System.out.println("Document ID: " + document.getDocumentId());
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (FileAccessException e) {
            System.out.println("File/Hash error: " + e.getMessage());
        }
    }
 
    private void verifyDocument() {
        System.out.print("Enter document ID: ");
        String documentId = scanner.nextLine();
 
        try {
            Document document = documentService.findDocumentById(documentId);
            String currentHash = HashUtil.generateFileHash(document.getFilePath());
 
            try {
                VerificationResult result = verificationService.verifyDocument(documentId, currentHash);
                System.out.println("\nStatus: " + result.getStatus());
                System.out.println("Message: " + result.getMessage());
                System.out.println("Verification Time: " + result.getVerificationTime());
            } catch (TamperDetectedException e) {
                System.out.println("Tampering error: " + e.getMessage());
            }
        } catch (DocumentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FileAccessException e) {
            System.out.println("File/Hash error: " + e.getMessage());
        }
    }
 
    private void viewVerificationHistory() {
        System.out.print("Enter document ID: ");
        String documentId = scanner.nextLine();
 
        try {
            List<VerificationResult> history = documentService.getVerificationHistory(documentId);
            if (history.isEmpty()) {
                System.out.println("No verification history found.");
                return;
            }
            System.out.println("\n===== VERIFICATION HISTORY =====");
            for (VerificationResult result : history) {
                System.out.println("Document ID: " + result.getDocumentId());
                System.out.println("Status: " + result.getStatus());
                System.out.println("Message: " + result.getMessage());
                System.out.println("Time: " + result.getVerificationTime());
                System.out.println("-----------------------------");
            }
        } catch (DocumentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private void viewAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        if (documents.isEmpty()) {
            System.out.println("No documents registered.");
            return;
        }
        System.out.println("\n===== REGISTERED DOCUMENTS =====");
        for (Document document : documents) {
            System.out.println("Document ID: " + document.getDocumentId());
            System.out.println("Document Name: " + document.getDocumentName());
            System.out.println("Holder: " + document.getHolderName());
            System.out.println("Issuer: " + document.getIssuerName());
            System.out.println("Issue Date: " + document.getIssueDate());
            System.out.println("File Path: " + document.getFilePath());
            System.out.println("-----------------------------");
        }
    }
}
