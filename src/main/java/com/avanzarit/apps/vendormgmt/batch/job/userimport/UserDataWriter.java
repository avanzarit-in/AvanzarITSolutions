package com.avanzarit.apps.vendormgmt.batch.job.userimport;

import com.avanzarit.apps.vendormgmt.auth.model.Role;
import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.model.UserStatusEnum;
import com.avanzarit.apps.vendormgmt.auth.repository.RoleRepository;
import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import org.springframework.batch.item.ItemWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDataWriter implements ItemWriter<User> {

    private UserService userService;
    private RoleRepository roleRepository;


    public UserDataWriter(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void write(List<? extends User> users) throws Exception {

        for (User user : users) {
            List<String> applicableRoles = Arrays.asList(user.getRolesString().split(":"));
            Set<Role> roles = new HashSet<>();
            for (String roleName : applicableRoles) {
                Role role = roleRepository.findByName(roleName);
                if (role != null) {
                    role.setName(roleName);
                }

                roles.add(role);
            }
            user.setRoles(roles);
            user.setPassword("welcome123");
            user.setPasswordConfirm("welcome123");
            user.setUserStatus(UserStatusEnum.NEW);
            userService.save(user);
        }
    }
}
