package com.avanzarit.apps.vendormgmt.storage;

/**
 * Created by SPADHI on 5/16/2017.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}