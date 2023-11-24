package com.orik.adminapi.service.impl;

import com.orik.adminapi.DAO.UserRepository;
import com.orik.adminapi.DTO.user.AdminRegistrationDTO;
import com.orik.adminapi.DTO.user.UserConverterDTO;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public User findById(Long id) throws UserNotFoundException{
        return  userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }
    @Override
    public User findByChatId(Long id) throws UserNotFoundException {
        return userRepository.findByChatId(id)
                .orElseThrow(() -> new UserNotFoundException("User with chatId: " + id + " is not found!"));
    }

    @Override
    public Page<User> getAllUsersSorted(int page, int size, String sortField, String sortOrder, Long roleId) {
        Sort sort = org.springframework.data.domain.Sort.by(sortField);

        if ("desc".equals(sortOrder)) {
            sort = sort.descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userRepository.findUsersByRoleRoleId(roleId,pageRequest);
    }

    @Override
    public User findByUserName(String userName) throws UserNotFoundException {
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFoundException("User not found with username: " + userName));
    }

    @Override
    public Page<User> findByFieldContainingIgnoreCase(String fieldName, String searchValue, Pageable pageable) {
        return userRepository.findByFieldContainingIgnoreCase(fieldName,searchValue,pageable);
    }
}
