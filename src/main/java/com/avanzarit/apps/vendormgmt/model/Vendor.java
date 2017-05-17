package com.avanzarit.apps.vendormgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Entity
@Table(name = "vendor")
public class Vendor {


    private String vendorId;
    private String vendorName1;
    private String vendorName2;
    private String vendorName3;
    private String contactPerson;
    private String telephoneNumberExtn;
    private String telephoneNumber;
    private String telephoneExtn;
    private String mobileNo;
    private String email;
    private String faxNumber;
    private String faxExtn;
    private String faxNumberExtn;
    private String buildingNo;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String city;
    private String postCode;
    private String region;
    private String country;
    private String railwayStation;
    private String accountHolderName;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String branchLocation;
    private String pan;
    private String vatNumber;
    private String gstRegistrationStatus;
    private String noOfGstRegistration;
    private String state;
    private String gstNumber;
    private String materialCode1;
    private String materialDescription1;
    private String hsn1;
    private String materialCode2;
    private String materialDescription2;
    private String hsn2;
    private String materialCode3;
    private String materialDescription3;
    private String hsn3;
    private String materialCode4;
    private String materialDescription4;
    private String hsn4;
    private String materialCode5;
    private String materialDescription5;
    private String hsn5;
    private String submityn="N";



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

    @Column(name="materialcode1")
    public String getMaterialCode1() {
        return materialCode1;
    }

    public void setMaterialCode1(String materialCode1) {
        this.materialCode1 = materialCode1;
    }

    @Column(name="materialdescription1")
    public String getMaterialDescription1() {
        return materialDescription1;
    }

    public void setMaterialDescription1(String materialDescription1) {
        this.materialDescription1 = materialDescription1;
    }

    @Column(name="hsn1")
    public String getHsn1() {
        return hsn1;
    }

    public void setHsn1(String hsn1) {
        this.hsn1 = hsn1;
    }

    @Column(name="materialcode2")
    public String getMaterialCode2() {
        return materialCode2;
    }

    public void setMaterialCode2(String materialCode2) {
        this.materialCode2 = materialCode2;
    }

    @Column(name="materialdescription2")
    public String getMaterialDescription2() {
        return materialDescription2;
    }

    public void setMaterialDescription2(String materialDescription2) {
        this.materialDescription2 = materialDescription2;
    }

    @Column(name="hsn2")
    public String getHsn2() {
        return hsn2;
    }

    public void setHsn2(String hsn2) {
        this.hsn2 = hsn2;
    }

    @Column(name="materialcode3")
    public String getMaterialCode3() {
        return materialCode3;
    }

    public void setMaterialCode3(String materialCode3) {
        this.materialCode3 = materialCode3;
    }

    @Column(name="materialdescription3")
    public String getMaterialDescription3() {
        return materialDescription3;
    }

    public void setMaterialDescription3(String materialDescription3) {
        this.materialDescription3 = materialDescription3;
    }

    @Column(name="hsn3")
    public String getHsn3() {
        return hsn3;
    }

    public void setHsn3(String hsn3) {
        this.hsn3 = hsn3;
    }

    @Column(name="materialcode4")
    public String getMaterialCode4() {
        return materialCode4;
    }

    public void setMaterialCode4(String materialCode4) {
        this.materialCode4 = materialCode4;
    }

    @Column(name="materialdescription4")
    public String getMaterialDescription4() {
        return materialDescription4;
    }

    public void setMaterialDescription4(String materialDescription4) {
        this.materialDescription4 = materialDescription4;
    }

    @Column(name="hsn4")
    public String getHsn4() {
        return hsn4;
    }

    public void setHsn4(String hsn4) {
        this.hsn4 = hsn4;
    }

    @Column(name="materialcode5")
    public String getMaterialCode5() {
        return materialCode5;
    }

    public void setMaterialCode5(String materialCode5) {
        this.materialCode5 = materialCode5;
    }

    @Column(name="materialdescription5")
    public String getMaterialDescription5() {
        return materialDescription5;
    }

    public void setMaterialDescription5(String materialDescription5) {
        this.materialDescription5 = materialDescription5;
    }

    @Column(name="hsn5")
    public String getHsn5() {
        return hsn5;
    }

    public void setHsn5(String hsn5) {
        this.hsn5 = hsn5;
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
}
