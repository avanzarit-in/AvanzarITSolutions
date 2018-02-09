package com.avanzarit.apps.gst.model.mdm;

import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.avanzarit.apps.gst.auth.SYSTEM_ROLES.PO_ADMIN;

public enum VENDOR_MGMT_ROLE {
    PO_ROLES{
        @Override
        public Set<String> getSystemRoles() {
            String[] SET_VALUES = new String[]{SYSTEM_ROLES.ADMIN.name(), SYSTEM_ROLES.PO_ADMIN.name(),
                    SYSTEM_ROLES.PO_ADMIN_MANAGER.name(),
                    SYSTEM_ROLES.PO_FINANCE.name(), SYSTEM_ROLES.PO_FINANCE_MANAGER.name(), SYSTEM_ROLES.PO_ORG.name(),
                    SYSTEM_ROLES.PO_ORG_MANAGER.name(), SYSTEM_ROLES.PO_TAX.name(), SYSTEM_ROLES.PO_TAX_MANAGER.name()};
            return new HashSet<>(Arrays.asList(SET_VALUES));
        }
    },
    PO_ADMIN_ROLES{
        @Override
        public Set<String> getSystemRoles() {
            String[] SET_VALUES = new String[]{SYSTEM_ROLES.ADMIN.name(), SYSTEM_ROLES.PO_ADMIN.name(),
                    SYSTEM_ROLES.PO_ADMIN_MANAGER.name()};
            return new HashSet<>(Arrays.asList(SET_VALUES));
        }
    },
    PO_FINANCE_ROLES{
        @Override
        public Set<String> getSystemRoles() {
            String[] SET_VALUES = new String[]{SYSTEM_ROLES.ADMIN.name(),
                    SYSTEM_ROLES.PO_FINANCE.name(), SYSTEM_ROLES.PO_FINANCE_MANAGER.name()};
            return new HashSet<>(Arrays.asList(SET_VALUES));
        }
    },
    PO_TAX_ROLES{
        @Override
        public Set<String> getSystemRoles() {
            String[] SET_VALUES = new String[]{SYSTEM_ROLES.ADMIN.name(), SYSTEM_ROLES.PO_TAX.name(),
                    SYSTEM_ROLES.PO_TAX_MANAGER.name()};
            return new HashSet<>(Arrays.asList(SET_VALUES));
        }
    },
    PO_ORG_ROLES{
        @Override
        public Set<String> getSystemRoles() {
            String[] SET_VALUES = new String[]{SYSTEM_ROLES.ADMIN.name(),
                    SYSTEM_ROLES.PO_ORG.name(), SYSTEM_ROLES.PO_ORG_MANAGER.name()};
            return new HashSet<>(Arrays.asList(SET_VALUES));
        }
    };

    public abstract Set<String> getSystemRoles();


}
