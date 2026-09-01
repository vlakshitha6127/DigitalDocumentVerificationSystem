package com.docverify.model;

public class Issuer extends User {

    public Issuer(String userId, String name, String email) {
        super(userId, name, email);
    }

    public void registerDocument() {
        System.out.println("Document registration initiated by " + getName());
    }

    public void issueDocument() {
        System.out.println("Document issued by " + getName());
    }

    @Override
    public void performAction() {
        System.out.println(getName() + " is performing issuer-related actions.");
    }
}