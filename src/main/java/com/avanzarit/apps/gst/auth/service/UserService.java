package com.avanzarit.apps.gst.auth.service;

public interface UserService<T> {

    void save(T user);

    T findByUsername(String username);

    public T findByEmail(String email);

    public void createPasswordResetTokenForUser(String userId, String token);
}
