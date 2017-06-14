package com.avanzarit.apps.gst.storage;

/**
 * Created by SPADHI on 5/16/2017.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    /**
     * Folder uploadLocation for storing files
     */
    private String uploadLocation;
    private String exportLocation;
    private String downloadLocation;
    private String vendorDownloadFileName;
    private String customerDownloadFileName;
    private String batchjobLogLocation;
    private String attachmentLocation;

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public String getExportLocation() {
        return exportLocation;
    }

    public void setExportLocation(String exportLocation) {
        this.exportLocation = exportLocation;
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
}
