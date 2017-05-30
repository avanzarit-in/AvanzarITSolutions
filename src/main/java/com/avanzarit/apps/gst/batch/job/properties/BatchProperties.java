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
}
