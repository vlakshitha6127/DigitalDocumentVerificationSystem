package com.docverify.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String generateHash(String content) {
        try {
            MessageDigest messageDigest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = messageDigest.digest(
                    content.getBytes()
            );

            StringBuilder hash = new StringBuilder();

            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }

            return hash.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Hashing algorithm not found", e
            );
        }
    }

    public static String generateFileHash(String filePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(
                    Path.of(filePath)
            );

            MessageDigest messageDigest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    messageDigest.digest(fileBytes);

            StringBuilder hash = new StringBuilder();

            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }

            return hash.toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read file", e
            );

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Hashing algorithm not found", e
            );
        }
    }
}