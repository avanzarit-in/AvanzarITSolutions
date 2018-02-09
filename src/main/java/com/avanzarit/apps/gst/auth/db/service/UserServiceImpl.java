package com.avanzarit.apps.gst.auth.db.service;

import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;
import com.avanzarit.apps.gst.auth.model.PasswordResetToken;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.repository.PasswordResetTokenRepository;
import com.avanzarit.apps.gst.auth.db.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.db.repository.UserRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.configcondition.DatabaseAuthEnabledCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "dbUserService")
public class UserServiceImpl implements UserService<DbUser> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void save(DbUser dbUser) {
        userRepository.save(dbUser);
    }

    @Override
    public DbUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public DbUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(String userId, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, userId, AUTH_SYSTEM.DB);
        passwordResetTokenRepository.save(myToken);
    }
}
