package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataProcessor.class);

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet the {@link FieldSet} to map
     * @throws BindException if there is a problem with the binding
     */
    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {

        String email = fieldSet.readString("EMAIL");
        String userName = fieldSet.readString("USERNAME");
        String rolesString = fieldSet.readString("ROLES");

        if (StringUtils.isEmpty(email)) {
            throw new SkippableReadException("Email can not be empty skip processing");
        } else if (userRepository.findByEmail(email) != null) {
            throw new SkippableReadException("Email already in use skip processing");
        }
        if (StringUtils.isEmpty(userName)) {
            throw new SkippableReadException("Username can not be empty skip processing");
        }
        if (StringUtils.isEmpty(rolesString)) {
            throw new SkippableReadException("Roles not defined for the user skip processing");
        }
        List<String> applicableRoles = Arrays.asList(rolesString.split(":"));
        Set<Role> roles = new HashSet<>();
        for (String roleName : applicableRoles) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            } else {
                LOGGER.info("Invalid Role : {}");
            }
        }
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            user = new User();
            user.setUsername(userName);
            user.setEmail(email);
            if (roles.size() > 0) {
                user.setRoles(roles);
            } else {
                throw new SkippableReadException("Unable to assign any role to the user skip processing the record");
            }
        } else {
            LOGGER.info("User already exist updating....");
            user.setEmail(email);
            if (roles.size() > 0) {
                user.setRoles(roles);
            }
        }
        return user;
    }
}
