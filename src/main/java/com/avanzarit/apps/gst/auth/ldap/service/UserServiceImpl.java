package com.avanzarit.apps.gst.auth.ldap.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;
import com.avanzarit.apps.gst.auth.ldap.model.LdapGroup;
import com.avanzarit.apps.gst.auth.ldap.model.LdapUser;
import com.avanzarit.apps.gst.auth.model.PasswordResetToken;
import com.avanzarit.apps.gst.auth.repository.PasswordResetTokenRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service(value = "ldapUserService")
public class UserServiceImpl implements UserService<LdapUser> {
    @Autowired
    LdapTemplate ldapTemplate;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void save(LdapUser user) {
        ldapTemplate.update(user);
    }

    @Override
    public LdapUser findByUsername(String username) {
        try {
            LdapQuery ldapUserQuery = query().base(LdapUtils.newLdapName("ou=users")).where("objectclass").is("person")
                    .and("cn").is(username);
            List<LdapUser> ldapUserDetails = ldapTemplate.find(ldapUserQuery, LdapUser.class);
            if (ldapUserDetails != null && !ldapUserDetails.isEmpty()) {
                if (!ldapUserDetails.isEmpty()) {
                    LdapUser ldapUser = ldapUserDetails.get(0);
                    LdapQuery ldapGroupQuery = query().base(LdapUtils.newLdapName("ou=groups")).where("objectclass").is("groupOfUniqueNames")
                            .and("uniquemember").is(LdapUtils.newLdapName(ldapUser.getCn().toString() + ",o=avanzarit").toString());

                    List<LdapGroup> ldapUserGroups = ldapTemplate.find(ldapGroupQuery, LdapGroup.class);
                    ldapUser.setLdapGroups(ldapUserGroups);
                    return ldapUser;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public LdapUser findByEmail(String email) {

        try {
            LdapQuery ldapUserQuery = query().base(LdapUtils.newLdapName("ou=users")).where("objectclass").is("person")
                    .and("mail").is(email);

            List<LdapUser> ldapUserDetails = ldapTemplate.find(ldapUserQuery, LdapUser.class);
            if (ldapUserDetails != null && !ldapUserDetails.isEmpty()) {
                LdapUser ldapUser = ldapUserDetails.get(0);
                LdapQuery ldapGroupQuery = query().base(LdapUtils.newLdapName("ou=groups")).where("objectclass").is("groupOfUniqueNames")
                        .and("uniquemember").is(LdapUtils.newLdapName(ldapUser.getCn().toString() + ",o=avanzarit").toString());

                List<LdapGroup> ldapUserGroups = ldapTemplate.find(ldapGroupQuery, LdapGroup.class);
                ldapUser.setLdapGroups(ldapUserGroups);
                return ldapUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPasswordResetTokenForUser(String userId, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, userId, AUTH_SYSTEM.LDAP);
        passwordResetTokenRepository.save(myToken);
    }
}
