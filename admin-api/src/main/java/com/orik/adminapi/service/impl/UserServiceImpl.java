package com.orik.adminapi.service.impl;

import com.orik.adminapi.DAO.UserRepository;
import com.orik.adminapi.DTO.user.AdminRegistrationDTO;
import com.orik.adminapi.DTO.user.UserConverterDTO;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverterDTO userConverterDTO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverterDTO userConverterDTO) {
        this.userRepository = userRepository;
        this.userConverterDTO = userConverterDTO;
    }

    @Override
    public User registerAdmin(AdminRegistrationDTO user) {
        return userRepository.save(userConverterDTO.convertToEntity(user));
    }

    @Override
    public User findByUserName(String userName) throws UserNotFoundException {
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("User not found with username: " + userName));
    }
}
