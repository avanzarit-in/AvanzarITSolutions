package com.avanzarit.apps.gst.batch.job.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("batch")
@Component
public class BatchProperties {

    private String userImportFileName;
    private String vendorImportFileName;
    private String customerImportFileName;
    private String vendorExportFileName;
    private String customerExportFileName;
    private String vendorMaterialExportFileName;
    private String vendorContactPersonExportFileName;
    private String customerContactPersonExportFileName;
    private String vendorMaterialImportFileName;
    private String serviceSacExportFileNmae;


    public String getUserImportFileName() {
        return userImportFileName;
    }

    public void setUserImportFileName(String userImportFileName) {
        this.userImportFileName = userImportFileName;
    }

    public String getVendorImportFileName() {
        return vendorImportFileName;
    }

    public void setVendorImportFileName(String vendorImportFileName) {
        this.vendorImportFileName = vendorImportFileName;
    }

    public String getVendorExportFileName() {
        return vendorExportFileName;
    }

    public void setVendorExportFileName(String vendorExportFileName) {
        this.vendorExportFileName = vendorExportFileName;
    }

    public String getVendorMaterialExportFileName() {
        return vendorMaterialExportFileName;
    }

    public void setVendorMaterialExportFileName(String vendorMaterialExportFileName) {
        this.vendorMaterialExportFileName = vendorMaterialExportFileName;
    }

    public String getVendorContactPersonExportFileName() {
        return vendorContactPersonExportFileName;
    }

    public void setVendorContactPersonExportFileName(String vendorContactPersonExportFileName) {
        this.vendorContactPersonExportFileName = vendorContactPersonExportFileName;
    }

    public String getVendorMaterialImportFileName() {
        return vendorMaterialImportFileName;
    }

    public void setVendorMaterialImportFileName(String vendorMaterialImportFileName) {
        this.vendorMaterialImportFileName = vendorMaterialImportFileName;
    }

    public String getServiceSacExportFileNmae() {
        return serviceSacExportFileNmae;
    }

    public void setServiceSacExportFileNmae(String serviceSacExportFileNmae) {
        this.serviceSacExportFileNmae = serviceSacExportFileNmae;
    }

    public String getCustomerImportFileName() {
        return customerImportFileName;
    }

    public void setCustomerImportFileName(String customerImportFileName) {
        this.customerImportFileName = customerImportFileName;
    }

    public String getCustomerExportFileName() {
        return customerExportFileName;
    }

    public void setCustomerExportFileName(String customerExportFileName) {
        this.customerExportFileName = customerExportFileName;
    }

    public String getCustomerContactPersonExportFileName() {
        return customerContactPersonExportFileName;
    }

    public void setCustomerContactPersonExportFileName(String customerContactPersonExportFileName) {
        this.customerContactPersonExportFileName = customerContactPersonExportFileName;
    }
}
