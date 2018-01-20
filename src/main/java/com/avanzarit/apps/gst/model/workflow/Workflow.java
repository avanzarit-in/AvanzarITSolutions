package com.avanzarit.apps.gst.model.workflow;

import com.avanzarit.apps.gst.model.Vendor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workflow")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "changerequestid")
    private Long changeRequestId;

    @OneToOne(targetEntity = Vendor.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "vendorid")
    private Vendor vendor;

    @Column(name = "assignedgroup")
    private String assignedGroup;

    @Column(name = "assigneduser")
    private String assignedUser;

    @Column(name = "status")
    private String status;

    @Column(name = "stage")
    private String stage;

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

    public String getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(String assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
