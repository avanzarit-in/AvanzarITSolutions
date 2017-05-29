package com.avanzarit.apps.gst.auth.service;

import com.avanzarit.apps.gst.auth.model.User;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);

    public User findByEmail(String email);

    public void createPasswordResetTokenForUser(User user, String token);
}
