package com.avanzarit.apps.gst.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    /**
     * Folder uploadLocation for storing files
     */
    private String uploadLocation;
    private String vendorExportLocation;
    private String customerExportLocation;
    private String downloadLocation;
    private String vendorDownloadFileName;
    private String customerDownloadFileName;
    private String batchjobLogLocation;
    private String attachmentLocation;

    private String vendorUploadLogFileName;
    private String customerUploadLogFileName;
    private String vendorContactPersonUploadLogFileName;
    private String customerContactPersonUploadLogFileName;
    private String materialUploadLogFileName;
    private String userUploadLogFileName;
    private String appLogFileName;

    private String vendorUploadLogFileId;
    private String customerUploadLogFileId;
    private String vendorContactPersonUploadLogFileId;
    private String customerContactPersonUploadLogFileId;
    private String materialUploadLogFileId;
    private String userUploadLogFileId;
    private String appLogFileId;

    private String appLogLocation;

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public String getVendorExportLocation() {
        return vendorExportLocation;
    }

    public void setVendorExportLocation(String vendorExportLocation) {
        this.vendorExportLocation = vendorExportLocation;
    }

    public String getCustomerExportLocation() {
        return customerExportLocation;
    }

    public void setCustomerExportLocation(String customerExportLocation) {
        this.customerExportLocation = customerExportLocation;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public String getVendorDownloadFileName() {
        return vendorDownloadFileName;
    }

    public void setVendorDownloadFileName(String vendorDownloadFileName) {
        this.vendorDownloadFileName = vendorDownloadFileName;
    }

    public String getCustomerDownloadFileName() {
        return customerDownloadFileName;
    }

    public void setCustomerDownloadFileName(String customerDownloadFileName) {
        this.customerDownloadFileName = customerDownloadFileName;
    }

    public String getCustomerDownloadZipFilePath() {
        return downloadLocation + "\\" + customerDownloadFileName;
    }

    public String getVendorDownloadZipFilePath() {
        return downloadLocation + "\\" + vendorDownloadFileName;
    }

    public String getBatchjobLogLocation() {
        return batchjobLogLocation;
    }

    public void setBatchjobLogLocation(String batchjobLogLocation) {
        this.batchjobLogLocation = batchjobLogLocation;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    public String getVendorUploadLogFileName() {
        return vendorUploadLogFileName;
    }

    public void setVendorUploadLogFileName(String vendorUploadLogFileName) {
        this.vendorUploadLogFileName = vendorUploadLogFileName;
    }

    public String getCustomerUploadLogFileName() {
        return customerUploadLogFileName;
    }

    public void setCustomerUploadLogFileName(String customerUploadLogFileName) {
        this.customerUploadLogFileName = customerUploadLogFileName;
    }

    public String getVendorContactPersonUploadLogFileName() {
        return vendorContactPersonUploadLogFileName;
    }

    public void setVendorContactPersonUploadLogFileName(String vendorContactPersonUploadLogFileName) {
        this.vendorContactPersonUploadLogFileName = vendorContactPersonUploadLogFileName;
    }

    public String getCustomerContactPersonUploadLogFileName() {
        return customerContactPersonUploadLogFileName;
    }

    public void setCustomerContactPersonUploadLogFileName(String customerContactPersonUploadLogFileName) {
        this.customerContactPersonUploadLogFileName = customerContactPersonUploadLogFileName;
    }

    public String getMaterialUploadLogFileName() {
        return materialUploadLogFileName;
    }

    public void setMaterialUploadLogFileName(String materialUploadLogFileName) {
        this.materialUploadLogFileName = materialUploadLogFileName;
    }

    public String getUserUploadLogFileName() {
        return userUploadLogFileName;
    }

    public void setUserUploadLogFileName(String userUploadLogFileName) {
        this.userUploadLogFileName = userUploadLogFileName;
    }

    public String getAppLogFileName() {
        return appLogFileName;
    }

    public void setAppLogFileName(String appLogFileName) {
        this.appLogFileName = appLogFileName;
    }

    public String getVendorUploadLogFileId() {
        return vendorUploadLogFileId;
    }

    public void setVendorUploadLogFileId(String vendorUploadLogFileId) {
        this.vendorUploadLogFileId = vendorUploadLogFileId;
    }

    public String getCustomerUploadLogFileId() {
        return customerUploadLogFileId;
    }

    public void setCustomerUploadLogFileId(String customerUploadLogFileId) {
        this.customerUploadLogFileId = customerUploadLogFileId;
    }

    public String getVendorContactPersonUploadLogFileId() {
        return vendorContactPersonUploadLogFileId;
    }

    public void setVendorContactPersonUploadLogFileId(String vendorContactPersonUploadLogFileId) {
        this.vendorContactPersonUploadLogFileId = vendorContactPersonUploadLogFileId;
    }

    public String getCustomerContactPersonUploadLogFileId() {
        return customerContactPersonUploadLogFileId;
    }

    public void setCustomerContactPersonUploadLogFileId(String customerContactPersonUploadLogFileId) {
        this.customerContactPersonUploadLogFileId = customerContactPersonUploadLogFileId;
    }

    public String getMaterialUploadLogFileId() {
        return materialUploadLogFileId;
    }

    public void setMaterialUploadLogFileId(String materialUploadLogFileId) {
        this.materialUploadLogFileId = materialUploadLogFileId;
    }

    public String getUserUploadLogFileId() {
        return userUploadLogFileId;
    }

    public void setUserUploadLogFileId(String userUploadLogFileId) {
        this.userUploadLogFileId = userUploadLogFileId;
    }

    public String getAppLogFileId() {
        return appLogFileId;
    }

    public void setAppLogFileId(String appLogFileId) {
        this.appLogFileId = appLogFileId;
    }

    public String getLogFileName(String logType) {
        switch (logType) {
            case "vendor":
                return vendorUploadLogFileName;
            case "customer":
                return customerUploadLogFileName;
            case "vendorcontact":
                return vendorContactPersonUploadLogFileId;
            case "customercontact":
                return customerContactPersonUploadLogFileId;
            case "material":
                return materialUploadLogFileName;
            case "user":
                return userUploadLogFileName;
            case "app":
                return appLogFileName;
        }
        return null;
    }

    public String getAppLogLocation() {
        return appLogLocation;
    }

    public void setAppLogLocation(String appLogLocation) {
        this.appLogLocation = appLogLocation;
    }
}
