package com.docverify.Exception;
 
/**
 * Wraps low-level IO/hashing failures (bad path, unreadable file,
 * missing algorithm) behind one checked exception, consistent with
 * the rest of the exception hierarchy - instead of leaking raw
 * RuntimeExceptions up to the UI layer.
 */
public class FileAccessException extends RuntimeException {
    public FileAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
