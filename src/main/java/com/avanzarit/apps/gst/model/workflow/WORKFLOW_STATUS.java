package com.avanzarit.apps.gst.model.workflow;

public enum WORKFLOW_STATUS {
    MAKER {
        @Override
        public WORKFLOW_STATUS getNextWorkflowStatus() {
            return CHEAKER;
        }
    },
    CHEAKER {
        @Override
        public WORKFLOW_STATUS getNextWorkflowStatus() {
            return COMPLETE;
        }
    },
    COMPLETE {
        @Override
        public WORKFLOW_STATUS getNextWorkflowStatus() {
            return COMPLETE;
        }
    };

    public abstract WORKFLOW_STATUS getNextWorkflowStatus();
}
