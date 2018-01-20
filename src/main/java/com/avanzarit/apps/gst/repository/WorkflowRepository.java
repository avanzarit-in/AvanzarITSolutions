package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.workflow.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkflowRepository extends JpaRepository<Workflow, String> {
    List<Workflow> findByAssignedGroup(String group);
    Workflow findByVendor(Vendor vendor);

}
