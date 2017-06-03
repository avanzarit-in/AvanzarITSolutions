package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.batch.job.annotations.Export;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Entity
@Table(name = "vendor")
public class Vendor implements Model {


    @Export(order = 1, title = "LFA1#LIFNR")
    private String vendorId;
    @Export(order = 2, title = "LFA1#NAME1")
    private String vendorName1;
    @Export(order = 3, title = "LFA1#NAME2")
    private String vendorName2;
    @Export(order = 4, title = "LFA1#NAME3")
    private String vendorName3;

    private String contactPerson;
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
    @Export(order = 19, title = "LFBK#KOINH")
    private String accountHolderName;
    @Export(order = 20, title = "LFBK#BANKN")
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String branchLocation;
    @Export(order = 21, title = "J_1IMOVEND#J_1IPANNO")
    private String pan;
    @Export(order = 22, title = "LFA1#STCEG")
    private String vatNumber;
    private String gstRegistrationStatus;
    private String noOfGstRegistration;
    private String state;
    private String gstNumber;
    private String submityn="N";
    private List<ContactPersonMaster> contactPersonMaster = new ArrayList<>();
    private List<MaterialMaster> materialMaster = new ArrayList<>();
    private VendorStatusEnum vendorStatus;
    private String modifiedBy;
    private Date lastModifiedOn;
    private String submittedBy;
    private Date lastSubmittedOn;
    private String approvedBy;
    private Date lastApprovedOn;
    private String rejectedBy;
    private Date lastRejectedOn;
    private String lastRevertedBy;
    private Date lastRevertedOevertedOn;
    private int revertCount;
    private String rejectReason;
    private String revertReason;
    private String acceptTermsAndCondition;
    private String tncAcceptBy;
    private Date tncAcceptedOn;
    private Date sapSyncDate;



    @Id
    @Column(name="vendorid")
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Column(name="vendorname1")
    public String getVendorName1() {
        return vendorName1;
    }

    public void setVendorName1(String vendorName1) {
        this.vendorName1 = vendorName1;
    }

    @Column(name="vendorname2")
    public String getVendorName2() {
        return vendorName2;
    }

    public void setVendorName2(String vendorName2) {
        this.vendorName2 = vendorName2;
    }

    @Column(name="vendorname3")
    public String getVendorName3() {
        return vendorName3;
    }

    public void setVendorName3(String vendorName3) {
        this.vendorName3 = vendorName3;
    }

    @Column(name="contactperson")
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Column(name="telephonenumberextn")
    public String getTelephoneNumberExtn() {
        return telephoneNumberExtn;
    }

    public void setTelephoneNumberExtn(String telephoneNumberExtn) {
        this.telephoneNumberExtn = telephoneNumberExtn;
    }

    @Column(name="mobileno")
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="faxnumberextn")
    public String getFaxNumberExtn() {
        return faxNumberExtn;
    }

    public void setFaxNumberExtn(String faxNumberExtn) {
        this.faxNumberExtn = faxNumberExtn;
    }

    @Column(name="buildingno")
    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    @Column(name="address1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name="address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name="address3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Column(name="address4")
    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    @Column(name="address5")
    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    @Column(name="city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="postcode")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name="region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name="country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name="railwaystation")
    public String getRailwayStation() {
        return railwayStation;
    }

    public void setRailwayStation(String railwayStation) {
        this.railwayStation = railwayStation;
    }

    @Column(name="accountholdername")
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    @Column(name="accountnumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name="bankname")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name="ifsccode")
    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }


    @Column(name="branchname")
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Column(name="branchlocation")
    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    @Column(name="pan")
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Column(name="vatnumber")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Column(name="gstregistrationstatus")
    public String getGstRegistrationStatus() {
        return gstRegistrationStatus;
    }

    public void setGstRegistrationStatus(String gstRegistrationStatus) {
        this.gstRegistrationStatus = gstRegistrationStatus;
    }

    @Column(name="noofgstregistration")
    public String getNoOfGstRegistration() {
        return noOfGstRegistration;
    }

    public void setNoOfGstRegistration(String noOfGstRegistration) {
        this.noOfGstRegistration = noOfGstRegistration;
    }

    @Column(name="state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name="gstnumber")
    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }


    @Column(name="submityn")
    public String getSubmityn() {
        return submityn;
    }

    public void setSubmityn(String submityn) {
        this.submityn = submityn;
    }

    @Transient
    public String getTelephoneNumber() {
        if(telephoneNumberExtn!=null && telephoneNumberExtn.contains("-")){
            telephoneNumber=telephoneNumberExtn.substring(0,telephoneNumberExtn.indexOf("-"));
        }else{
            telephoneNumber=telephoneNumberExtn;
        }
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Transient
    public String getTelephoneExtn() {
        if(telephoneNumberExtn!=null && telephoneNumberExtn.contains("-")){
            telephoneExtn=telephoneNumberExtn.substring(telephoneNumberExtn.indexOf("-")+1,telephoneNumberExtn.length());
        }
        return telephoneExtn;
    }

    public void setTelephoneExtn(String telephoneExtn) {
        this.telephoneExtn = telephoneExtn;
    }

    @Transient
    public String getFaxNumber() {

        if(faxNumberExtn!=null && faxNumberExtn.contains("-")){
            faxNumber=faxNumberExtn.substring(0,faxNumberExtn.indexOf("-"));
        }else{
            faxNumber=faxNumberExtn;
        }
         return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Transient
    public String getFaxExtn() {

        if(faxNumberExtn!=null && faxNumberExtn.contains("-")){
            faxExtn=faxNumberExtn.substring(faxNumberExtn.indexOf("-")+1,faxNumberExtn.length());
        }
        return faxExtn;

    }

    public void setFaxExtn(String faxExtn) {
        this.faxExtn = faxExtn;
    }

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.REMOVE)
    public List<MaterialMaster> getMaterialMaster() {

        return materialMaster;
    }


    public void setMaterialMaster(List<MaterialMaster> materialMaster) {
        this.materialMaster = materialMaster;
    }

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.REMOVE)
    public List<ContactPersonMaster> getContactPersonMaster() {

        return contactPersonMaster;
    }

    public void setContactPersonMaster(List<ContactPersonMaster> contactPersonMasterList) {
        this.contactPersonMaster = contactPersonMasterList;
    }


    @Column(name="vendorstatus")
    public VendorStatusEnum getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(VendorStatusEnum vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    @Column(name="modifiedby")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Column(name="modifiedon")
    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Column(name="submittedby")
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Column(name="lastsubmittedon")
    public Date getLastSubmittedOn() {
        return lastSubmittedOn;
    }

    public void setLastSubmittedOn(Date lastSubmittedOn) {
        this.lastSubmittedOn = lastSubmittedOn;
    }

    @Column(name="approvedby")
    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Column(name="lastapprovedon")
    public Date getLastApprovedOn() {
        return lastApprovedOn;
    }

    public void setLastApprovedOn(Date lastApprovedOn) {
        this.lastApprovedOn = lastApprovedOn;
    }

    @Column(name="rejectedby")
    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    @Column(name="lastrejectedon")
    public Date getLastRejectedOn() {
        return lastRejectedOn;
    }

    public void setLastRejectedOn(Date lastRejectedOn) {
        this.lastRejectedOn = lastRejectedOn;
    }

    @Column(name="lastrevertedby")
    public String getLastRevertedBy() {
        return lastRevertedBy;
    }

    public void setLastRevertedBy(String lastRevertedBy) {
        this.lastRevertedBy = lastRevertedBy;
    }

    @Column(name="lastrevertedon")
    public Date getLastRevertedOevertedOn() {
        return lastRevertedOevertedOn;
    }

    public void setLastRevertedOevertedOn(Date lastRevertedOevertedOn) {
        this.lastRevertedOevertedOn = lastRevertedOevertedOn;
    }

    @Column(name="revertcount")
    public int getRevertCount() {
        return revertCount;
    }

    public void setRevertCount(int revertCount) {
        this.revertCount = revertCount;
    }

    @Column(name="rejectreason")
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Column(name="revertreason")
    public String getRevertReason() {
        return revertReason;
    }

    public void setRevertReason(String revertReason) {
        this.revertReason = revertReason;
    }

    @Column(name="isaccepttnc")
    public String isAcceptTermsAndCondition() {
        return acceptTermsAndCondition;
    }

    public void setAcceptTermsAndCondition(String acceptTermsAndCondition) {
        this.acceptTermsAndCondition = acceptTermsAndCondition;
    }

    @Column(name="tncacceptedby")
    public String getTncAcceptBy() {
        return tncAcceptBy;
    }

    public void setTncAcceptBy(String tncAcceptBy) {
        this.tncAcceptBy = tncAcceptBy;
    }

    @Column(name="tncacceptedon")
    public Date getTncAcceptedOn() {
        return tncAcceptedOn;
    }

    public void setTncAcceptedOn(Date tncAcceptedOn) {
        this.tncAcceptedOn = tncAcceptedOn;
    }

    @Column(name="sapsyncdate")
    public Date getSapSyncDate() {
        return sapSyncDate;
    }

    public void setSapSyncDate(Date sapSyncDate) {
        this.sapSyncDate = sapSyncDate;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName1='" + vendorName1 + '\'' +
                '}';
    }
}
