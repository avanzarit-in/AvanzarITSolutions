package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.batch.job.annotations.Export;
import com.avanzarit.apps.gst.batch.job.annotations.SapInfo;

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

import static com.avanzarit.apps.gst.model.Attachment.PAN;

@Entity
@Table(name = "vendor")
public class VendorMaster implements Model {
    private String baCompanyCode;
    private String baAccountGroup;
    private String baAddressName;
    private String baSearchTerm;
    private String baPostalCode;
    private String baCity;
    private String baCountry;
    private String baRegion;
    private String baFirstName;
    private String baName;
    private String baState;
    private String baCustomer;
    private String baTaxNumber1;
    private String baTaxNumber2;
    private String baTaxnumber3;

    private String faReconAccount;
    private String faSortKey;
    private String faPaymentTerm;
    private String faCheckDoubleInvoice;
    private String faPaymentMethod;
    private String faWithTaxType;
    private String faWithTaxCode;
    private String faLiable;
    private String faRecType;

    private String txpanNo;
    private String txPanRefNo;
    private String txPanValidFrom;
    private String txGstVendorClass;


    private String poOrganization
    private String poOrderCurrency
    private String poSchemaGroup
    private String poOrderingAddress
    private String poVendor
    private String poInvoicingParty


}
