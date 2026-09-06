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

        DocumentService documentService = new DocumentService();

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

        System.out.println("\nDigital Document Verification System");

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

            System.out.println("\nDocument registered successfully.");
            System.out.println(
                    "Document ID: " + document.getDocumentId()
            );

            String currentHash =
                    HashUtil.generateFileHash(filePath);

            try {

                VerificationResult result =
                        verificationService.verifyDocument(
                                document.getDocumentId(),
                                currentHash
                        );

                System.out.println(
                        "\nStatus: " + result.getStatus()
                );

                System.out.println(
                        "Message: " + result.getMessage()
                );

            } catch (TamperDetectedException e) {

                System.out.println(
                        "Tampering error: " + e.getMessage()
                );
            }

            try {

                VerificationResult result =
                        verificationService.verifyDocument(
                                document.getDocumentId(),
                                "tampered_hash"
                        );

                System.out.println(
                        "\nStatus: " + result.getStatus()
                );

                System.out.println(
                        "Message: " + result.getMessage()
                );

            } catch (TamperDetectedException e) {

                System.out.println(
                        "Tampering error: " + e.getMessage()
                );
            }

            System.out.println("\nVerification History:");

            List<VerificationResult> history =
                    documentService.getVerificationHistory(
                            document.getDocumentId()
                    );

            for (VerificationResult verification : history) {

                System.out.println(
                        "Document ID: " +
                        verification.getDocumentId()
                );

                System.out.println(
                        "Status: " +
                        verification.getStatus()
                );

                System.out.println(
                        "Message: " +
                        verification.getMessage()
                );

                System.out.println(
                        "Time: " +
                        verification.getVerificationTime()
                );

                System.out.println(
                        "-----------------------------"
                );
            }

        } catch (ValidationException e) {

            System.out.println(
                    "Validation error: " + e.getMessage()
            );

        } catch (DocumentNotFoundException e) {

            System.out.println(e.getMessage());

        } catch (RuntimeException e) {

            System.out.println(
                    "File/Hash error: " + e.getMessage()
            );
        }

        scanner.close();
    }
}