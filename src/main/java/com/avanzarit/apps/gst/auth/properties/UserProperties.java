package com.avanzarit.apps.gst.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
    private boolean sendEmailOnCreate;

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

    public boolean isSendEmailOnCreate() {
        return sendEmailOnCreate;
    }

    public void setSendEmailOnCreate(boolean sendEmailOnCreate) {
        this.sendEmailOnCreate = sendEmailOnCreate;
    }
}
