package com.avanzarit.apps.gst.auth.db.repository;

import com.avanzarit.apps.gst.auth.db.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}