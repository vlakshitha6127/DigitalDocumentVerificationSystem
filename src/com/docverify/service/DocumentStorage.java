package com.docverify.service;
 
import com.docverify.Exception.FileAccessException;
import com.docverify.model.Document;
 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Persists the FULL document list on every save (saveAll), instead of the
 * old load -> append -> write pattern. That old pattern silently duplicated
 * every document, because DocumentService had already added it to its own
 * in-memory list before calling storage - so the same document ended up in
 * the file twice. Overwriting with the complete, already-correct in-memory
 * list removes that entire class of bug.
 */
public class DocumentStorage {
 
    private static final String FILE_NAME = "documents.dat";
 
    public void saveAll(List<Document> documents) {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(new ArrayList<>(documents));
        } catch (IOException e) {
            throw new FileAccessException("Unable to save documents.", e);
        }
    }
 
    @SuppressWarnings("unchecked")
    public List<Document> loadDocuments() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Document>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileAccessException("Unable to load documents.", e);
        }
    }
}
