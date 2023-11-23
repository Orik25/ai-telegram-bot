package com.orik.adminapi.service.interfaces;

import com.orik.adminapi.DTO.user.AdminRegistrationDTO;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;

public interface UserService {
    User registerAdmin(AdminRegistrationDTO user);
    User findByUserName(String userName) throws UserNotFoundException;
}
