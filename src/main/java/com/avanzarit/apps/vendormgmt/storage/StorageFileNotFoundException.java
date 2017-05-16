package com.avanzarit.apps.vendormgmt.storage;

/**
 * Created by SPADHI on 5/16/2017.
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
