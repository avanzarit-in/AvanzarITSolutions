package com.avanzarit.apps.vendormgmt.auth.service;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.repository.RoleRepository;
import com.avanzarit.apps.vendormgmt.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

      //  user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
