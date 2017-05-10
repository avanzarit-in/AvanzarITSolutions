package com.avanzarit.apps.vendormgmt.auth.service;

import com.avanzarit.apps.vendormgmt.auth.model.User;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
