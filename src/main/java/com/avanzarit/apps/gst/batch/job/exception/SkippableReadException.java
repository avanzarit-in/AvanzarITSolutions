package com.avanzarit.apps.gst.batch.job.exception;

public class SkippableReadException extends RuntimeException{

    public SkippableReadException(String message){
        super(message);
    }
}

