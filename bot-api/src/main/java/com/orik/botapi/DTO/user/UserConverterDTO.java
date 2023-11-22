package com.orik.botapi.DTO.user;

import com.orik.botapi.entity.User;
import com.orik.botapi.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserConverterDTO {

    private final RoleService roleService;

    @Autowired
    public UserConverterDTO(RoleService roleService) {
        this.roleService = roleService;
    }

    public User convertToEntity(UserRegistrationDTO user){
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setChatId(user.getChatId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setRole(roleService.getUserRole());
        newUser.setMessagesFromUser(new ArrayList<>());
        newUser.setMessagesToUser(new ArrayList<>());
        return newUser;
    }
}
