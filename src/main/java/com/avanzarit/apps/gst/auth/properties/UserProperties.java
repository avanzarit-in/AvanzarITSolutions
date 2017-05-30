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

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
}
