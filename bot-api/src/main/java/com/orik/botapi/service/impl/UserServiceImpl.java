package com.orik.botapi.service.impl;

import com.orik.botapi.DAO.UserRepository;
import com.orik.botapi.DTO.user.UserConverterDTO;
import com.orik.botapi.DTO.user.UserRegistrationDTO;
import com.orik.botapi.entity.User;
import com.orik.botapi.exception.UserNotFoundException;
import com.orik.botapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserConverterDTO userConverter;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserConverterDTO userConverter,UserRepository userRepository) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isRegistered(long id) {
        User user = userRepository.findByChatId(id).orElse(null);
        return user != null;
    }

    @Override
    public User registerUser(UserRegistrationDTO user) {
        return userRepository.save(userConverter.convertToEntity(user));
    }

    @Override
    public User findByChatId(long id) throws UserNotFoundException{
        return userRepository.findByChatId(id)
                .orElseThrow(()->new UserNotFoundException("User with chatId: "+id+" is not found!"));
    }
}
