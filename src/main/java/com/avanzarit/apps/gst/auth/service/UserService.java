package com.avanzarit.apps.gst.auth.service;

import com.avanzarit.apps.gst.auth.model.User;

public interface UserService {
    void save(User user);

    void saveOnly(User user);

    User findByUsername(String username);

    public User findByEmail(String email);

    public void createPasswordResetTokenForUser(User user, String token);
}
