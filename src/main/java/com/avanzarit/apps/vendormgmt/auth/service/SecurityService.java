package com.avanzarit.apps.vendormgmt.auth.service;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

    String validatePasswordResetToken(String id, String token);
}
