package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.batch.job.annotations.Export;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer implements Model {

    @Export(order = 1, title = "LFA1#LIFNR")
    private String customerId;
    @Export(order = 2, title = "LFA1#NAME1")
    private String customerName1;
    @Export(order = 3, title = "LFA1#NAME2")
    private String customerName2;
    @Export(order = 4, title = "LFA1#NAME3")
    private String customerName3;
    @Export(order = 5, title = "LFA1#TELF1")
    private String telephoneNumberExtn;
    private String telephoneNumber;
    private String telephoneExtn;
    @Export(order = 6, title = "LFA1#TELF2")
    private String mobileNo;
    @Export(order = 7, title = "ADR6#SMTP_ADDR")
    private String email;
    private String faxNumber;
    private String faxExtn;
    @Export(order = 8, title = "LFA1#TELFX")
    private String faxNumberExtn;
    @Export(order = 9, title = "ADRC#BUILDING")
    private String buildingNo;
    @Export(order = 10, title = "LFA1#STRAS")
    private String address1;
    @Export(order = 11, title = "ADRC#STR_SUPPL1")
    private String address2;
    @Export(order = 12, title = "ADRC#STR_SUPPL2")
    private String address3;
    @Export(order = 13, title = "ADRC#STR_SUPPL3")
    private String address4;
    @Export(order = 14, title = "ADRC#LOCATION")
    private String address5;
    @Export(order = 15, title = "LFA1#ORT01")
    private String city;
    @Export(order = 16, title = "LFA1#PSTLZ")
    private String postCode;
    @Export(order = 17, title = "LFA1#REGIO")
    private String region;
    @Export(order = 18, title = "LFA1#LAND1")
    private String country;
    private String railwayStation;
    @Export(order = 21, title = "J_1IMOVEND#J_1IPANNO")
    private String pan;
    @Export(order = 22, title = "LFA1#STCEG")
    private String vatNumber;
    private String gstRegistrationStatus;
    private String noOfGstRegistration;
    private String state;
    private String gstNumber;
    private String submityn = "N";
    private List<CustomerContactPersonMaster> customerContactPersonMasters = new ArrayList<>();
    @CopyOver
    private CustomerStatusEnum customerStatus;
    @CopyOver
    private String createdBy;
    @CopyOver
    private Date createdOn;
    @CopyOver
    private String modifiedBy;
    @CopyOver
    private Date lastModifiedOn;
    @CopyOver
    private String submittedBy;
    @CopyOver
    private Date lastSubmittedOn;

    @CopyOver
    private String approvedBy;
    @CopyOver
    private Date lastApprovedOn;
    @CopyOver
    private int approveCount;
    @CopyOver
    private String approveReason;

    @CopyOver
    private String rejectedBy;
    @CopyOver
    private Date lastRejectedOn;
    @CopyOver
    private String rejectReason;
    @CopyOver
    private int rejectCount;

    @CopyOver
    private String lastRevertedBy;
    @CopyOver
    private Date lastRevertedOn;
    @CopyOver
    private int revertCount;
    @CopyOver
    private String revertReason;

    @CopyOver
    private String acceptTermsAndCondition;
    @CopyOver
    private String tncAcceptBy;
    @CopyOver
    private Date tncAcceptedOn;
    @CopyOver
    private Date sapSyncDate;


    @Id
    @Column(name = "customerid")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Column(name = "customername1")
    public String getCustomerName1() {
        return customerName1;
    }

    public void setCustomerName1(String customerName1) {
        this.customerName1 = customerName1;
    }

    @Column(name = "customername2")
    public String getCustomerName2() {
        return customerName2;
    }

    public void setCustomerName2(String customerName2) {
        this.customerName2 = customerName2;
    }

    @Column(name = "customername3")
    public String getCustomerName3() {
        return customerName3;
    }

    public void setCustomerName3(String customerName3) {
        this.customerName3 = customerName3;
    }

    @Column(name = "telephonenumberextn")
    public String getTelephoneNumberExtn() {
        return telephoneNumberExtn;
    }

    public void setTelephoneNumberExtn(String telephoneNumberExtn) {
        this.telephoneNumberExtn = telephoneNumberExtn;
    }

    @Column(name = "mobileno")
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains(",")) {
            this.email = email.substring(0, email.indexOf(','));
        } else {
            this.email = email;
        }

    }

    @Column(name = "faxnumberextn")
    public String getFaxNumberExtn() {
        return faxNumberExtn;
    }

    public void setFaxNumberExtn(String faxNumberExtn) {
        this.faxNumberExtn = faxNumberExtn;
    }

    @Column(name = "buildingno")
    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    @Column(name = "address1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "address3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Column(name = "address4")
    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    @Column(name = "address5")
    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "postcode")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "railwaystation")
    public String getRailwayStation() {
        return railwayStation;
    }

    public void setRailwayStation(String railwayStation) {
        this.railwayStation = railwayStation;
    }

    @Column(name = "pan")
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Column(name = "vatnumber")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Column(name = "gstregistrationstatus")
    public String getGstRegistrationStatus() {
        return gstRegistrationStatus;
    }

    public void setGstRegistrationStatus(String gstRegistrationStatus) {
        this.gstRegistrationStatus = gstRegistrationStatus;
    }

    @Column(name = "noofgstregistration")
    public String getNoOfGstRegistration() {
        return noOfGstRegistration;
    }

    public void setNoOfGstRegistration(String noOfGstRegistration) {
        this.noOfGstRegistration = noOfGstRegistration;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "gstnumber")
    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }


    @Column(name = "submityn")
    public String getSubmityn() {
        return submityn;
    }

    public void setSubmityn(String submityn) {
        this.submityn = submityn;
    }

    @Transient
    public String getTelephoneNumber() {
        if (telephoneNumberExtn != null && telephoneNumberExtn.contains("-")) {
            telephoneNumber = telephoneNumberExtn.substring(0, telephoneNumberExtn.indexOf("-"));
        } else {
            telephoneNumber = telephoneNumberExtn;
        }
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Transient
    public String getTelephoneExtn() {
        if (telephoneNumberExtn != null && telephoneNumberExtn.contains("-")) {
            telephoneExtn = telephoneNumberExtn.substring(telephoneNumberExtn.indexOf("-") + 1, telephoneNumberExtn.length());
        }
        return telephoneExtn;
    }

    public void setTelephoneExtn(String telephoneExtn) {
        this.telephoneExtn = telephoneExtn;
    }

    @Transient
    public String getFaxNumber() {

        if (faxNumberExtn != null && faxNumberExtn.contains("-")) {
            faxNumber = faxNumberExtn.substring(0, faxNumberExtn.indexOf("-"));
        } else {
            faxNumber = faxNumberExtn;
        }
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Transient
    public String getFaxExtn() {

        if (faxNumberExtn != null && faxNumberExtn.contains("-")) {
            faxExtn = faxNumberExtn.substring(faxNumberExtn.indexOf("-") + 1, faxNumberExtn.length());
        }
        return faxExtn;

    }

    public void setFaxExtn(String faxExtn) {
        this.faxExtn = faxExtn;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    public List<CustomerContactPersonMaster> getCustomerContactPersonMaster() {

        return customerContactPersonMasters;
    }

    public void setCustomerContactPersonMaster(List<CustomerContactPersonMaster> customerContactPersonMasters) {
        this.customerContactPersonMasters = customerContactPersonMasters;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "customerstatus")
    public CustomerStatusEnum getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
    }

    @Column(name = "createdby")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "createdon")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }


    @Column(name = "modifiedby")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Column(name = "modifiedon")
    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Column(name = "submittedby")
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Column(name = "lastsubmittedon")
    public Date getLastSubmittedOn() {
        return lastSubmittedOn;
    }

    public void setLastSubmittedOn(Date lastSubmittedOn) {
        this.lastSubmittedOn = lastSubmittedOn;
    }

    @Column(name = "approvedby")
    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Column(name = "lastapprovedon")
    public Date getLastApprovedOn() {
        return lastApprovedOn;
    }

    public void setLastApprovedOn(Date lastApprovedOn) {
        this.lastApprovedOn = lastApprovedOn;
    }

    @Column(name = "rejectedby")
    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    @Column(name = "lastrejectedon")
    public Date getLastRejectedOn() {
        return lastRejectedOn;
    }

    public void setLastRejectedOn(Date lastRejectedOn) {
        this.lastRejectedOn = lastRejectedOn;
    }

    @Column(name = "lastrevertedby")
    public String getLastRevertedBy() {
        return lastRevertedBy;
    }

    public void setLastRevertedBy(String lastRevertedBy) {
        this.lastRevertedBy = lastRevertedBy;
    }

    @Column(name = "lastrevertedon")
    public Date getLastRevertedOn() {
        return lastRevertedOn;
    }

    public void setLastRevertedOn(Date lastRevertedOn) {
        this.lastRevertedOn = lastRevertedOn;
    }

    @Column(name = "revertcount")
    public int getRevertCount() {
        return revertCount;
    }

    public void setRevertCount(int revertCount) {
        this.revertCount = revertCount;
    }

    @Column(name = "rejectreason")
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Column(name = "revertreason")
    public String getRevertReason() {
        return revertReason;
    }

    public void setRevertReason(String revertReason) {
        this.revertReason = revertReason;
    }

    @Column(name = "isaccepttnc")
    public String isAcceptTermsAndCondition() {
        return acceptTermsAndCondition;
    }

    public void setAcceptTermsAndCondition(String acceptTermsAndCondition) {
        this.acceptTermsAndCondition = acceptTermsAndCondition;
    }

    @Column(name = "tncacceptedby")
    public String getTncAcceptBy() {
        return tncAcceptBy;
    }

    public void setTncAcceptBy(String tncAcceptBy) {
        this.tncAcceptBy = tncAcceptBy;
    }

    @Column(name = "tncacceptedon")
    public Date getTncAcceptedOn() {
        return tncAcceptedOn;
    }

    public void setTncAcceptedOn(Date tncAcceptedOn) {
        this.tncAcceptedOn = tncAcceptedOn;
    }

    @Column(name = "sapsyncdate")
    public Date getSapSyncDate() {
        return sapSyncDate;
    }

    public void setSapSyncDate(Date sapSyncDate) {
        this.sapSyncDate = sapSyncDate;
    }

    @Column(name = "approvecount")
    public int getApproveCount() {
        return approveCount;
    }

    public void setApproveCount(int approveCount) {
        this.approveCount = approveCount;
    }

    @Column(name = "approvereason")
    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }

    @Column(name = "rejectcount")
    public int getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(int rejectCount) {
        this.rejectCount = rejectCount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName1='" + customerName1 + '\'' +
                '}';
    }

}
