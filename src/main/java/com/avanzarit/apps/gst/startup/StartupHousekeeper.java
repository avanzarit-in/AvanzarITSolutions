package com.avanzarit.apps.gst.startup;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        User user = userService.findByUsername(userProperties.getAdminId());
        if (user == null) {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("ADMIN");
            roles.add(role);
            user = new User();
            user.setUsername(userProperties.getAdminId());
            user.setRoles(roles);
            user.setPassword(userProperties.getDefaultPassword());
            user.setEmail(userProperties.getAdminEmailId());
            user.setUserStatus(UserStatusEnum.NEW);
            userService.save(user);
        }else if(userProperties.isAdminPasswordResetOnStartup()){
            user.setUserStatus(UserStatusEnum.NEW);
            userService.saveOnly(user);
        }
    }
}
