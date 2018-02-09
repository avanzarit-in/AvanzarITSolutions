package com.avanzarit.apps.gst.configcondition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This class is not used for now, but is available in the project as a sample to
 * demonstrate how condition can be used to conditionally load a configuration
 */
public class LDAPAuthEnabledCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("app.security.ldap.enabled").contains("true");  }
}

