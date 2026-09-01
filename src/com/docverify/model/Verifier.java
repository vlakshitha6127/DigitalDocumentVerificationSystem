package com.docverify.model;

public class Verifier extends User {

    public Verifier(String userId, String name, String email) {
        super(userId, name, email);
    }

    public void verifyDocument() {
        System.out.println("Document verification initiated by " + getName());
    }

    @Override
    public void performAction() {
        System.out.println(getName() + " is performing document verification.");
    }
}