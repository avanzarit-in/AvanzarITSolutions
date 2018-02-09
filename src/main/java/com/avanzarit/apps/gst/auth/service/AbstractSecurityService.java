package com.avanzarit.apps.gst.auth.service;

import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.model.PasswordResetToken;
import com.avanzarit.apps.gst.auth.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Calendar;

public abstract class AbstractSecurityService<T> implements SecurityService<T> {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }


    @Override
    public String validatePasswordResetToken(String id, String token) {
        PasswordResetToken passToken =
                passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (!passToken.getUserId().equals(id))) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        return null;
    }
}
