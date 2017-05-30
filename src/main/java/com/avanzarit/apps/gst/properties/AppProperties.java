package com.avanzarit.apps.gst.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by SPADHI on 5/30/2017.
 */

@ConfigurationProperties("app")
@Component
public class AppProperties {

    private String contextScheme;
    private String contextDomain;
    private String contextPort;
    private String contextURL;

    public String getContextScheme() {
        return contextScheme;
    }

    public void setContextScheme(String contextScheme) {
        this.contextScheme = contextScheme;
    }

    public String getContextDomain() {
        return contextDomain;
    }

    public void setContextDomain(String contextDomain) {
        this.contextDomain = contextDomain;
    }

    public String getContextPort() {
        return contextPort;
    }

    public void setContextPort(String contextPort) {
        this.contextPort = contextPort;
    }

    public String getContextURL() {
        return contextURL;
    }

    public void setContextURL(String contextURL) {
        this.contextURL = contextURL;
    }
}
