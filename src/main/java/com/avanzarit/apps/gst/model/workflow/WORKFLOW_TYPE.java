package com.avanzarit.apps.gst.model.workflow;

import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;

public enum WORKFLOW_TYPE {
    PO_GENERAL {
        @Override
        public SYSTEM_ROLES getGroupToAssign(WORKFLOW_STATUS workflowStatus) {
            if (workflowStatus == WORKFLOW_STATUS.MAKER) {
                return SYSTEM_ROLES.PO_ADMIN;
            } else if (workflowStatus == WORKFLOW_STATUS.CHEAKER) {
                return SYSTEM_ROLES.PO_ADMIN_MANAGER;
            }
            return null;
        }

        @Override
        public String getWorkflowType() {
            return "Basic Details Workflow";
        }
    },

    PO_FINANCE {
        @Override
        public SYSTEM_ROLES getGroupToAssign(WORKFLOW_STATUS workflowStatus) {
            if (workflowStatus == WORKFLOW_STATUS.MAKER) {
                return SYSTEM_ROLES.PO_FINANCE;
            } else if (workflowStatus == WORKFLOW_STATUS.CHEAKER) {
                return SYSTEM_ROLES.PO_FINANCE_MANAGER;
            }
            return null;
        }

        @Override
        public String getWorkflowType() {
            return "Finance Details Workflow";
        }
    },

    PO_TAX {
        @Override
        public SYSTEM_ROLES getGroupToAssign(WORKFLOW_STATUS workflowStatus) {
            if (workflowStatus == WORKFLOW_STATUS.MAKER) {
                return SYSTEM_ROLES.PO_TAX;
            } else if (workflowStatus == WORKFLOW_STATUS.CHEAKER) {
                return SYSTEM_ROLES.PO_TAX_MANAGER;
            }
            return null;
        }

        @Override
        public String getWorkflowType() {
            return "Tax Details Workflow";
        }
    },

    PO_ORG {
        @Override
        public SYSTEM_ROLES getGroupToAssign(WORKFLOW_STATUS workflowStatus) {
            if (workflowStatus == WORKFLOW_STATUS.MAKER) {
                return SYSTEM_ROLES.PO_ORG;
            } else if (workflowStatus == WORKFLOW_STATUS.CHEAKER) {
                return SYSTEM_ROLES.PO_ORG_MANAGER;
            }
            return null;
        }

        @Override
        public String getWorkflowType() {
            return "Purchase Organization Workflow";
        }
    };

    public abstract SYSTEM_ROLES getGroupToAssign(WORKFLOW_STATUS workflowStatus);

    public abstract String getWorkflowType();
}
