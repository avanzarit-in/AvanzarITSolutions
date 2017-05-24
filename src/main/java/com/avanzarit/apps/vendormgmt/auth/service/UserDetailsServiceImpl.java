package com.avanzarit.apps.vendormgmt.auth.service;

import com.avanzarit.apps.vendormgmt.auth.model.Role;
import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.model.UserStatusEnum;
import com.avanzarit.apps.vendormgmt.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User userDetails=null;
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Does not exist.");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        if(user.getUserStatus()== UserStatusEnum.NEW) {

             userDetails = new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), true, true,
                    false, true, grantedAuthorities);


        }else{
            userDetails = new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        return userDetails;

    }
}
