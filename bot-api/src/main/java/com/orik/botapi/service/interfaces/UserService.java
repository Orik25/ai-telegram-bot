package com.orik.botapi.service.interfaces;

import com.orik.botapi.DTO.user.UserRegistrationDTO;
import com.orik.botapi.entity.User;

public interface UserService {
    boolean isRegistered(long id);
    User registerUser(UserRegistrationDTO user);
}
