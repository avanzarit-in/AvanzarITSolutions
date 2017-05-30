package com.avanzarit.apps.gst.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by SPADHI on 5/30/2017.
 */

@ConfigurationProperties("user")
@Component
public class UserProperties {
    /**
     * Folder location for storing files
     */
    private String defaultPassword;
    private String adminId;
    private String adminEmailId;
    private boolean adminPasswordResetOnStartup;

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminEmailId() {
        return adminEmailId;
    }

    public void setAdminEmailId(String adminEmailId) {
        this.adminEmailId = adminEmailId;
    }

    public boolean isAdminPasswordResetOnStartup() {
        return adminPasswordResetOnStartup;
    }

    public void setAdminPasswordResetOnStartup(boolean adminPasswordResetOnStartup) {
        this.adminPasswordResetOnStartup = adminPasswordResetOnStartup;
    }
}
