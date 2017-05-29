package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import org.springframework.batch.item.ItemWriter;
import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDataWriter implements ItemWriter<User> {

    private UserService userService;
    private RoleRepository roleRepository;
    public SimpleMailMessage template;
    public EmailServiceImpl emailService;


    public UserDataWriter(UserService userService, RoleRepository roleRepository,SimpleMailMessage template,EmailServiceImpl emailService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.template=template;
        this.emailService=emailService;
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

            String text = String.format(template.getText(), "http://www.cool.com",user.getUsername(),"welcome123");
            emailService.sendSimpleMessage(user.getEmail(), "Welcome to Vendor Management Portal", text);

        }
    }
}
