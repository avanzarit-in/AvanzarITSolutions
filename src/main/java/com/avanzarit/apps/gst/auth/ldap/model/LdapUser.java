package com.avanzarit.apps.gst.auth.ldap.model;

import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;
import com.avanzarit.apps.gst.auth.model.AppUser;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entry(objectClasses = {"inetOrgPerson", "inetOrgPersonExt", "organizationalPerson", "person"}, base = "ou=users")
public final class LdapUser implements LdapUserDetails, AppUser {
    private static final long serialVersionUID = 1L;
    @Transient
    private LdapUserDetails details;
    @Transient
    private Attributes attributes;
    @Transient
    private List<String> ldapGroups;
    @Id
    private Name cn;
    @Attribute(name = "sn", type = Attribute.Type.STRING)
    private String sn;
    @Attribute(name = "description", type = Attribute.Type.STRING)
    private String description;
    @Attribute(name = "givenname", type = Attribute.Type.STRING)
    private String givenname;
    @Attribute(name = "mail", type = Attribute.Type.STRING)
    private String mail;
    @Attribute(name = "uid", type = Attribute.Type.STRING)
    private String uid;
    @Attribute(name = "userpassword", type = Attribute.Type.STRING)
    private String userpassword;
    @Attribute(name = "resetPasswordOnLogin", type = Attribute.Type.STRING)
    private boolean resetPasswordOnLogin;

    /**
     * This constructor is required if we use LDAP Object-Dictionary Mapping to read LDAP Entity using the ldapTemplate.
     */
    public LdapUser() {

    }

    public void setLdapGroups(List<LdapGroup> ldapGroups) {
        this.ldapGroups = ldapGroups.stream().map(this::getGroupName).collect(Collectors.toList());
    }

    private <R> String getGroupName(LdapGroup item) {
        String groupCn=item.getCn().toString().split(",")[0];
        return groupCn.substring(groupCn.indexOf("=")+1);
    }

    public boolean isEnabled() {
        return true;
    }

    public String getDn() {
        return details.getDn();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (attributes != null) {
            javax.naming.directory.Attribute resetPasswordAttribute = attributes.get("resetpasswordonlogin");

            try {
                if (resetPasswordAttribute != null && Boolean.valueOf(resetPasswordAttribute.get().toString())) {
                    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PASSWORD_CHANGE"));
                    return grantedAuthorities;
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }

            return details.getAuthorities();
        }

        return null;
    }

    public LdapUserDetails getDetails() {
        return details;
    }

    public void setDetails(LdapUserDetails details) {
        this.details = details;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return details.getPassword();
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public String getUserId() {
        String userCn=getCn().toString().split(",")[0];
        return userCn.substring(userCn.indexOf("=")+1);
     }

    public String getUsername() {
        return details.getUsername();
    }

    @Override
    public String getEmail() {
        return getMail();
    }

    @Override
    public List<String> getRolesAsString() {
        if (getAuthorities() != null) {
            return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } else if (ldapGroups != null && !ldapGroups.isEmpty()) {
            return ldapGroups;
        }

        return null;
    }

    @Override
    public AUTH_SYSTEM getAuthSystem() {
        return AUTH_SYSTEM.LDAP;
    }

    public boolean isAccountNonExpired() {
        return details.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return details.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return details.isCredentialsNonExpired();
    }

    @Override
    public void eraseCredentials() {

    }

    public Name getCn() {
        return cn;
    }

    public void setCn(Name cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public boolean isResetPasswordOnLogin() {
        return resetPasswordOnLogin;
    }

    public void setResetPasswordOnLogin(boolean resetPasswordOnLogin) {
        this.resetPasswordOnLogin = resetPasswordOnLogin;
    }
}
