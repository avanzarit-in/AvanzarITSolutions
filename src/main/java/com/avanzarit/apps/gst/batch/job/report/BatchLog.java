package com.avanzarit.apps.gst.batch.job.report;

/**
 * Created by AVANZAR on 6/12/2017.
 */
public class BatchLog {

    private StringBuilder sb;

    public BatchLog() {
        this.sb = new StringBuilder();
    }

    public void log(String message) {
        sb.append(message).append("\n");
    }

    public String getLog() {
        return sb.toString();
    }
}
