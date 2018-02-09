package com.avanzarit.apps.gst.startup;

import com.avanzarit.apps.gst.auth.db.model.Role;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.db.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.db.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class StartupHousekeeper {

    @Autowired(required = false)
    private UserService<DbUser> userService;

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
    /*    if (userService != null) {
            DbUser dbUser = userService.findByUsername(userProperties.getAdminId());
            if (dbUser == null) {
                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName("ADMIN");
                roles.add(role);
                dbUser = new DbUser();
                dbUser.setUsername(userProperties.getAdminId());
                dbUser.setRoles(roles);
                dbUser.setPassword(userProperties.getDefaultPassword());
                dbUser.setEmail(userProperties.getAdminEmailId());
                dbUser.setUserStatus(UserStatusEnum.NEW);
                userService.save(dbUser);
            } else if (userProperties.isAdminPasswordResetOnStartup()) {
                dbUser.setUserStatus(UserStatusEnum.NEW);
                userService.saveOnly(dbUser);
            }
        }*/
    }
}
