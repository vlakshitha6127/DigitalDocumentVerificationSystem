package com.docverify.service;

import com.docverify.model.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DocumentStorage {

    private static final String FILE_NAME = "documents.dat";

    public void saveDocument(Document document) {

        List<Document> documents = loadDocuments();

        documents.add(document);

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {

            outputStream.writeObject(documents);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to save documents.", e
            );
        }
    }

    @SuppressWarnings("unchecked")
    public List<Document> loadDocuments() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(
                             new FileInputStream(FILE_NAME))) {

            return (List<Document>) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {

            throw new RuntimeException(
                    "Unable to load documents.", e
            );
        }
    }
}