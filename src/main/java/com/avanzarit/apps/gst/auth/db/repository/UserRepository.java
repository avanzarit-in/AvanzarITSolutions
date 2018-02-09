package com.avanzarit.apps.gst.auth.db.repository;

import com.avanzarit.apps.gst.auth.db.model.Role;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface UserRepository extends JpaRepository<DbUser, Long> {
    DbUser findByUsername(String username);

    DbUser findByEmail(String email);

    List<DbUser> findByRoles(List<Role> roles);
}

