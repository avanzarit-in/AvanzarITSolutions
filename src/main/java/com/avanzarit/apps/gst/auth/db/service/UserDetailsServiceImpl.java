package com.avanzarit.apps.gst.auth.db.service;

import com.avanzarit.apps.gst.auth.db.model.Role;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.db.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.db.repository.UserRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.configcondition.DatabaseAuthEnabledCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User userDetails=null;
        DbUser dbUser = userRepository.findByUsername(username);
        if (dbUser == null) {
            throw new UsernameNotFoundException("User Does not exist.");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if(dbUser.getUserStatus()== UserStatusEnum.NEW) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PASSWORD_CHANGE"));
             userDetails = new org.springframework.security.core.userdetails
                     .User(dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);

        }else{
            for (Role role : dbUser.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            userDetails = new org.springframework.security.core.userdetails
                    .User(dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);

        }
        return userDetails;
    }
}