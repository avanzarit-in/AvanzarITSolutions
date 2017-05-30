package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet
     *         the {@link FieldSet} to map
     * @throws BindException
     *         if there is a problem with the binding
     */
    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        User user=new User();
        user.setUsername(fieldSet.readString("USERNAME"));
        user.setEmail(fieldSet.readString("EMAIL"));
        List<String> applicableRoles = Arrays.asList(fieldSet.readString("ROLES").split(":"));
        Set<Role> roles = new HashSet<>();
        for (String roleName : applicableRoles) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        return user;
    }
}
