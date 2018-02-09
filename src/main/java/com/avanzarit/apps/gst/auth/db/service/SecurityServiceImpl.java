package com.avanzarit.apps.gst.auth.db.service;

import com.avanzarit.apps.gst.auth.db.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.db.repository.UserRepository;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.service.AbstractSecurityService;
import com.avanzarit.apps.gst.auth.service.SecurityService;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.configcondition.DatabaseAuthEnabledCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "dbSecurityService")
public class SecurityServiceImpl extends AbstractSecurityService<DbUser> {
     @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService<DbUser> userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

   @Override
    public void changePassword(UserDetails userDetails,String newPassword) {
        String userName = userDetails.getUsername();
        DbUser repDbUser = userRepository.findByUsername(userName);
        repDbUser.setLastLoginDate(new Date());
        repDbUser.setUserStatus(UserStatusEnum.ACTIVE);
        repDbUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userService.save(repDbUser);
      //  autologin(userDetails.getUsername(), userDetails.getPassword());
    }

 /*   public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    } */
}
