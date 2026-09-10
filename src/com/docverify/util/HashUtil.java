package com.docverify.util;
 
import com.docverify.Exception.FileAccessException;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class HashUtil {
 
    private HashUtil() {}
 
    public static String generateHash(String content) {
        return hashBytes(content.getBytes());
    }
 
    public static String generateFileHash(String filePath) {
        try {
            return hashBytes(Files.readAllBytes(Path.of(filePath)));
        } catch (IOException e) {
            throw new FileAccessException("Unable to read file: " + filePath, e);
        }
    }
 
    private static String hashBytes(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input);
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new FileAccessException("Hashing algorithm not found", e);
        }
    }
}
