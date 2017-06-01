package com.avanzarit.apps.gst.batch.job.model;

import com.avanzarit.apps.gst.Model;

import java.util.Date;

/**
 * Created by SPADHI on 6/1/2017.
 */

public class ExceptionItem {

    private ENTITY_TYPE entityType;
    private Model model;
    private String exceptionReason;
    private String jobId;
    private Date jobRunDate;

    public ENTITY_TYPE getEntityType() {
        return entityType;
    }

    public void setEntityType(ENTITY_TYPE entityType) {
        this.entityType = entityType;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }
}
