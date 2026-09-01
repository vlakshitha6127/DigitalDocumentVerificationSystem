package com.docverify.app;

import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.model.Document;
import com.docverify.model.VerificationResult;
import com.docverify.service.DocumentService;
import com.docverify.service.VerificationService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        DocumentService documentService = new DocumentService();

        VerificationService verificationService =
                new VerificationService(documentService);

        Document document = new Document(
                "DOC001",
                "Java Certificate",
                "Lakshitha",
                "ABC College",
                LocalDate.now(),
                "sample.txt",
                null
        );

        documentService.registerDocument(document);

        System.out.println("Document registered successfully.");

        try {
            VerificationResult result =
                    verificationService.verifyDocument("DOC001");

            System.out.println("Document ID: "
                    + result.getDocumentId());

            System.out.println("Status: "
                    + result.getStatus());

            System.out.println("Message: "
                    + result.getMessage());

        } catch (DocumentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}