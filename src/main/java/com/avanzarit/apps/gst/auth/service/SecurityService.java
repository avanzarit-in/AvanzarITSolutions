package com.avanzarit.apps.gst.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService<T> {

    String findLoggedInUsername();

    void changePassword(UserDetails user,String newPassword);

    String validatePasswordResetToken(String id, String token);
}
