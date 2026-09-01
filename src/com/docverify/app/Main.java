package com.docverify.app;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Document;
import com.docverify.model.Issuer;
import com.docverify.model.User;
import com.docverify.model.VerificationResult;
import com.docverify.model.Verifier;
import com.docverify.service.DocumentService;
import com.docverify.service.VerificationService;

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

        // Polymorphism demonstration
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

        // Console-based document registration
        System.out.println("\nDigital Document Verification System");

        System.out.print("Enter document name: ");
        String documentName = scanner.nextLine();

        System.out.print("Enter holder name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter issuer name: ");
        String issuerName = scanner.nextLine();

        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        Document document = new Document(
                documentName,
                holderName,
                issuerName,
                LocalDate.now(),
                filePath,
                null
        );

        try {
            documentService.registerDocument(document);

            System.out.println("\nDocument registered successfully.");
            System.out.println(
                    "Document ID: " + document.getDocumentId()
            );

            VerificationResult result =
                    verificationService.verifyDocument(
                            document.getDocumentId()
                    );

            System.out.println(
                    "Status: " + result.getStatus()
            );

            System.out.println(
                    "Message: " + result.getMessage()
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Validation error: " + e.getMessage()
            );

        } catch (DocumentNotFoundException e) {

            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}