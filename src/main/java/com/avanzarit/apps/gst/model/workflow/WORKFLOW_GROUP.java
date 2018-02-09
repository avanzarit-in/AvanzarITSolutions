package com.avanzarit.apps.gst.model.workflow;

import java.util.Arrays;
import java.util.List;

public enum WORKFLOW_GROUP {
    PO_WORKFLOW {
        @Override
        public List<WORKFLOW_TYPE> getWorkflowList() {
            return Arrays.asList(
                    WORKFLOW_TYPE.PO_GENERAL,
                    WORKFLOW_TYPE.PO_FINANCE,
                    WORKFLOW_TYPE.PO_TAX,
                    WORKFLOW_TYPE.PO_ORG
            );
        }
    };

    public abstract List<WORKFLOW_TYPE> getWorkflowList();
}
