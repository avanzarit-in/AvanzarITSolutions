package com.avanzarit.apps.gst.model.workflow;

import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;
import com.avanzarit.apps.gst.model.Vendor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "workflow")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "changerequestid")
    private Long changeRequestId;

    @OneToOne(targetEntity = Vendor.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(nullable = false, name = "vendorid")
    private Vendor vendor;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignedgroup")
    private SYSTEM_ROLES assignedGroup;

    @Column(name = "assigneduser")
    private String assignedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WORKFLOW_STATUS status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WORKFLOW_TYPE type;

    public Long getChangeRequestId() {
        return changeRequestId;
    }

    public void setChangeRequestId(Long changeRequestId) {
        this.changeRequestId = changeRequestId;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public SYSTEM_ROLES getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(SYSTEM_ROLES assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public WORKFLOW_STATUS getStatus() {
        return status;
    }

    public void setStatus(WORKFLOW_STATUS status) {
        this.status = status;
    }

    public WORKFLOW_TYPE getType() {
        return type;
    }

    public void setType(WORKFLOW_TYPE type) {
        this.type = type;
    }
}
