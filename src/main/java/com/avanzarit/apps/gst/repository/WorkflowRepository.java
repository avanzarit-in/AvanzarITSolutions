package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.workflow.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkflowRepository extends JpaRepository<Workflow, String> {
    Workflow findByChangeRequestId(Long changeRequestId);
    List<Workflow> findByAssignedGroup(SYSTEM_ROLES group);
    List<Workflow> findByVendor(Vendor vendor);
    Workflow findByVendorAndAssignedGroup(Vendor vendor,SYSTEM_ROLES assignedGroup);
}
