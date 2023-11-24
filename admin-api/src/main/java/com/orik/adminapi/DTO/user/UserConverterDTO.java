package com.orik.adminapi.DTO.user;


import com.orik.adminapi.entity.User;
import com.orik.adminapi.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverterDTO {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConverterDTO(RoleService roleService,PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User convertToEntity(AdminRegistrationDTO user){
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleService.getAdminRole());
        return newUser;
    }
}
