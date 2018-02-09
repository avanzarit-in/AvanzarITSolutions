package com.avanzarit.apps.gst.auth.ldap.service;

import com.avanzarit.apps.gst.auth.ldap.model.LdapUser;
import com.avanzarit.apps.gst.auth.service.AbstractSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.Name;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service(value = "ldapSecurityService")
public class SecurityServiceImpl extends AbstractSecurityService<LdapUser> {
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    @Autowired
    LdapShaPasswordEncoder ldapShaPasswordEncoder;
    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    public void changePassword(UserDetails userDetails,String newPassword) {
        String userName = userDetails.getUsername();
        LdapQuery ldapUserQuery = query().base(LdapUtils.newLdapName("ou=users")).where("objectclass").is("person")
                .and("cn").is(userName);

        List<LdapUser> ldapUserDetails = ldapTemplate.find(ldapUserQuery, LdapUser.class);
        if(ldapUserDetails!=null && !ldapUserDetails.isEmpty()) {

            LdapUser ldapUser=ldapUserDetails.get(0);
            ldapUser.setResetPasswordOnLogin(false);
            ldapUser.setUserpassword(ldapShaPasswordEncoder.encodePassword(newPassword, null));
            ldapTemplate.update(ldapUser);
        }
    }

    @Override
    public String validatePasswordResetToken(String id, String token) {
        return null;
    }
}
