package com.docverify.app;
 
import com.docverify.Exception.DocumentNotFoundException;
import com.docverify.Exception.TamperDetectedException;
import com.docverify.Exception.ValidationException;
import com.docverify.model.Issuer;
import com.docverify.model.User;
import com.docverify.model.Verifier;
import com.docverify.service.DocumentService;
import com.docverify.service.VerificationService;
import com.docverify.ui.ConsoleUI;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Entry point. Wires up services and hands control to the UI layer.
 * No business logic or I/O lives here anymore - see ConsoleUI.
 */
public class Main {
 
    public static void main(String[] args) {
        DocumentService documentService = new DocumentService();
        VerificationService verificationService = new VerificationService(documentService);
 
        List<User> users = new ArrayList<>();
        users.add(new Issuer("U001", "Lakshitha", "lakshitha@email.com"));
        users.add(new Verifier("U002", "Arun", "arun@email.com"));
 
        System.out.println("User Actions:");
        for (User user : users) {
            user.performAction();
        }
 
        ConsoleUI ui = new ConsoleUI(documentService, verificationService);
        ui.run();
    }
}
