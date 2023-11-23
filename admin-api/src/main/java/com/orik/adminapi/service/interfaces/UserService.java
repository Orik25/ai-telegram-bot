package com.orik.adminapi.service.interfaces;

import com.orik.adminapi.DTO.user.AdminRegistrationDTO;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User registerAdmin(AdminRegistrationDTO user);

    User findById(Long id) throws UserNotFoundException;
    User findByChatId(Long id) throws UserNotFoundException;


    Page<User> getAllUsersSorted(int page, int size, String sortField, String sortOrder, Long userId);

    User findByUserName(String userName) throws UserNotFoundException;

    Page<User> findByFieldContainingIgnoreCase(String fieldName, String searchValue, Pageable pageable);

}
