package com.avanzarit.apps.gst.auth.model;

import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;

import java.util.List;

public interface AppUser {

     String getUserId();

     String getUsername();

     String getEmail();

    List<String> getRolesAsString();

    AUTH_SYSTEM getAuthSystem();

}
