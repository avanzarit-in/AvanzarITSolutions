package com.avanzarit.apps.gst.auth.repository;

import com.avanzarit.apps.gst.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}

