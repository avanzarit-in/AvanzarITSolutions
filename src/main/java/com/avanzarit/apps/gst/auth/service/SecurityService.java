package com.avanzarit.apps.gst.auth.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

    String validatePasswordResetToken(String id, String token);
}
