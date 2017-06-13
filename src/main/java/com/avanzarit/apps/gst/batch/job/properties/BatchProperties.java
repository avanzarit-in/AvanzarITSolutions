package com.avanzarit.apps.gst.batch.job.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by SPADHI on 5/30/2017.
 */

@ConfigurationProperties("batch")
@Component
public class BatchProperties {

    private String userImportFileName;
    private String vendorImportFileName;
    private String vendorExportFileName;
    private String vendorMaterialExportFileName;
    private String vendorContactPersonExportFileName;
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
}
