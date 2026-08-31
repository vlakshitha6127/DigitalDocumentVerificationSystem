# Digital Document Verification System

## Project Overview

The Digital Document Verification System is a Java-based application developed using Object-Oriented Programming principles. The system is designed to manage and verify digital documents by validating their authenticity and integrity.

It provides a structured approach to document registration, verification, and validation while handling situations such as missing documents, invalid data, and potential document tampering.

## Objectives

- Apply Object-Oriented Programming concepts using Java.
- Manage digital document information in a structured manner.
- Verify the authenticity and integrity of documents.
- Detect potential document tampering.
- Handle exceptional situations using custom exceptions.
- Develop a modular and maintainable application structure.

## Technologies Used

- Java
- Object-Oriented Programming
- Visual Studio Code
- Git
- GitHub

## Project Structure

```text
DigitalDocumentVerificationSystem
│
├── src
│   └── com
│       └── docverify
│           │
│           ├── app
│           │   └── Main.java
│           │
│           ├── exception
│           │   ├── DocumentNotFoundException.java
│           │   ├── TamperDetectedException.java
│           │   └── ValidationException.java
│           │
│           ├── model
│           │   ├── Document.java
│           │   ├── Issuer.java
│           │   ├── User.java
│           │   ├── VerificationResult.java
│           │   ├── VerificationStatus.java
│           │   └── Verifier.java
│           │
│           ├── service
│           │   ├── DocumentService.java
│           │   └── VerificationService.java
│           │
│           └── util
│               └── HashUtil.java
│
├── .gitignore
└── README.md

OOP Concepts Covered

The project is intended to demonstrate the following concepts:

Classes and Objects
Encapsulation
Inheritance
Polymorphism
Abstraction
Interfaces
Exception Handling
Collections
Enumerations

Project Status

The project is currently under development.

The initial project structure has been established, and the implementation will be developed in subsequent phases.

Author
Lakshitha