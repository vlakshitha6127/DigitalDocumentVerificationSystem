package com.docverify.app;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.TamperDetectedException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;
import com.docverify.model.Issuer;
import com.docverify.model.User;
import com.docverify.model.VerificationResult;
import com.docverify.model.Verifier;
import com.docverify.service.DocumentService;
import com.docverify.service.VerificationService;
import com.docverify.util.HashUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DocumentService documentService =
                new DocumentService();

        VerificationService verificationService =
                new VerificationService(documentService);

        List<User> users = new ArrayList<>();

        users.add(new Issuer(
                "U001",
                "Lakshitha",
                "lakshitha@email.com"
        ));

        users.add(new Verifier(
                "U002",
                "Arun",
                "arun@email.com"
        ));

        System.out.println("User Actions:");

        for (User user : users) {
            user.performAction();
        }

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== DIGITAL DOCUMENT VERIFICATION SYSTEM ====="
            );

            System.out.println("1. Register Document");
            System.out.println("2. Verify Document");
            System.out.println("3. View Verification History");
            System.out.println("4. View All Documents");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    registerDocument(
                            scanner,
                            documentService
                    );

                    break;

                case "2":

                    verifyDocument(
                            scanner,
                            documentService,
                            verificationService
                    );

                    break;

                case "3":

                    viewVerificationHistory(
                            scanner,
                            documentService
                    );

                    break;

                case "4":

                    viewAllDocuments(
                            documentService
                    );

                    break;

                case "5":

                    running = false;

                    System.out.println(
                            "Exiting system..."
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }

    private static void registerDocument(
            Scanner scanner,
            DocumentService documentService) {

        System.out.print("Enter document name: ");
        String documentName = scanner.nextLine();

        System.out.print("Enter holder name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter issuer name: ");
        String issuerName = scanner.nextLine();

        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        try {

            String documentHash =
                    HashUtil.generateFileHash(filePath);

            Document document = new Document(
                    documentName,
                    holderName,
                    issuerName,
                    LocalDate.now(),
                    filePath,
                    documentHash
            );

            documentService.registerDocument(document);

            System.out.println(
                    "\nDocument registered successfully."
            );

            System.out.println(
                    "Document ID: " +
                    document.getDocumentId()
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Validation error: " +
                    e.getMessage()
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "File/Hash error: " +
                    e.getMessage()
            );
        }
    }

    private static void verifyDocument(
            Scanner scanner,
            DocumentService documentService,
            VerificationService verificationService) {

        System.out.print("Enter document ID: ");
        String documentId = scanner.nextLine();

        try {

            Document document =
                    documentService.findDocumentById(
                            documentId
                    );

            String currentHash =
                    HashUtil.generateFileHash(
                            document.getFilePath()
                    );

            try {

                VerificationResult result =
                        verificationService.verifyDocument(
                                documentId,
                                currentHash
                        );

                System.out.println(
                        "\nStatus: " +
                        result.getStatus()
                );

                System.out.println(
                        "Message: " +
                        result.getMessage()
                );

                System.out.println(
                        "Verification Time: " +
                        result.getVerificationTime()
                );

            } catch (TamperDetectedException e) {

                System.out.println(
                        "Tampering error: " +
                        e.getMessage()
                );
            }

        } catch (DocumentNotFoundException e) {

            System.out.println(
                    e.getMessage()
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "File/Hash error: " +
                    e.getMessage()
            );
        }
    }

    private static void viewVerificationHistory(
            Scanner scanner,
            DocumentService documentService) {

        System.out.print("Enter document ID: ");
        String documentId = scanner.nextLine();

        try {

            List<VerificationResult> history =
                    documentService.getVerificationHistory(
                            documentId
                    );

            if (history.isEmpty()) {

                System.out.println(
                        "No verification history found."
                );

                return;
            }

            System.out.println(
                    "\n===== VERIFICATION HISTORY ====="
            );

            for (VerificationResult result : history) {

                System.out.println(
                        "Document ID: " +
                        result.getDocumentId()
                );

                System.out.println(
                        "Status: " +
                        result.getStatus()
                );

                System.out.println(
                        "Message: " +
                        result.getMessage()
                );

                System.out.println(
                        "Time: " +
                        result.getVerificationTime()
                );

                System.out.println(
                        "-----------------------------"
                );
            }

        } catch (DocumentNotFoundException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    private static void viewAllDocuments(
            DocumentService documentService) {

        List<Document> documents =
                documentService.getAllDocuments();

        if (documents.isEmpty()) {

            System.out.println(
                    "No documents registered."
            );

            return;
        }

        System.out.println(
                "\n===== REGISTERED DOCUMENTS ====="
        );

        for (Document document : documents) {

            System.out.println(
                    "Document ID: " +
                    document.getDocumentId()
            );

            System.out.println(
                    "Document Name: " +
                    document.getDocumentName()
            );

            System.out.println(
                    "Holder: " +
                    document.getHolderName()
            );

            System.out.println(
                    "Issuer: " +
                    document.getIssuerName()
            );

            System.out.println(
                    "Issue Date: " +
                    document.getIssueDate()
            );

            System.out.println(
                    "File Path: " +
                    document.getFilePath()
            );

            System.out.println(
                    "-----------------------------"
            );
        }
    }
}