package com.avanzarit.apps.gst.service;

import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_GROUP;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_STATUS;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_TYPE;
import com.avanzarit.apps.gst.model.workflow.Workflow;
import com.avanzarit.apps.gst.repository.VendorRepository;
import com.avanzarit.apps.gst.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class WorkflowManagementService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public void createWorkflowItemsFor(Vendor vendor, WORKFLOW_GROUP workflowGroup){
        List<WORKFLOW_TYPE> workflowTypes = workflowGroup.getWorkflowList();
        workflowTypes.forEach(item -> createWorkflowItem(item, vendor));
    }


    private void createWorkflowItem(WORKFLOW_TYPE item, Vendor vendor) {
        Workflow workflowItem = new Workflow();

        workflowItem.setStatus(WORKFLOW_STATUS.MAKER);
        switch (item) {
            case PO_GENERAL:
                workflowItem.setAssignedGroup(SYSTEM_ROLES.PO_ADMIN);
                workflowItem.setType(WORKFLOW_TYPE.PO_GENERAL);
                break;
            case PO_FINANCE:
                workflowItem.setAssignedGroup(SYSTEM_ROLES.PO_FINANCE);
                workflowItem.setType(WORKFLOW_TYPE.PO_FINANCE);
                break;
            case PO_TAX:
                workflowItem.setAssignedGroup(SYSTEM_ROLES.PO_TAX);
                workflowItem.setType(WORKFLOW_TYPE.PO_TAX);
                break;
            case PO_ORG:
                workflowItem.setAssignedGroup(SYSTEM_ROLES.PO_ORG);
                workflowItem.setType(WORKFLOW_TYPE.PO_ORG);
                break;

        }
        workflowItem=workflowRepository.save(workflowItem);

            workflowItem.setVendor(vendor);
            if(StringUtils.isEmpty(vendor.getVendorId())) {
                vendor.setVendorId(workflowItem.getChangeRequestId().toString());
            }
            workflowRepository.save(workflowItem);

    }
}
