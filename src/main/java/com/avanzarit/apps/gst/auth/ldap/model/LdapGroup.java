package com.avanzarit.apps.gst.auth.ldap.model;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.List;

@Entry(objectClasses = {"groupOfUniqueNames", "top"}, base = "ou=groups")
public final class LdapGroup {
    @Id
    private Name cn;
    @Attribute(name = "description", type = Attribute.Type.STRING)
    private String description;
    @Attribute(name = "uniquemember", type = Attribute.Type.STRING)
    private List<String> members;

    public Name getCn() {
        return cn;
    }

    public void setCn(Name cn) {
        this.cn = cn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
