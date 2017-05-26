package com.avanzarit.apps.vendormgmt.auth.repository;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}

