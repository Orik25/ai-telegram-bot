package com.orik.adminapi;

import com.orik.adminapi.DAO.UserRepository;
import com.orik.adminapi.DTO.user.AdminRegistrationDTO;
import com.orik.adminapi.DTO.user.UserConverterDTO;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverterDTO userConverterDTO;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testRegisterAdmin() {
        AdminRegistrationDTO adminRegistrationDTO = new AdminRegistrationDTO();
        User convertedUser = new User();

        when(userConverterDTO.convertToEntity(adminRegistrationDTO)).thenReturn(convertedUser);
        when(userRepository.save(convertedUser)).thenReturn(convertedUser);

        User result = userService.registerAdmin(adminRegistrationDTO);

        assertNotNull(result);
        assertEquals(convertedUser, result);

        verify(userConverterDTO, times(1)).convertToEntity(adminRegistrationDTO);
        verify(userRepository, times(1)).save(convertedUser);
    }

    @Test
    void testFindByIdUserFound() {
        long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(user, result);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByIdUserNotFound() {
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByChatIdUserFound() {
        long chatId = 12345L;
        User user = new User();

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.of(user));

        User result = userService.findByChatId(chatId);

        assertNotNull(result);
        assertEquals(user, result);

        verify(userRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void testFindByChatIdUserNotFound() {
        long chatId = 12345L;

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByChatId(chatId));

        verify(userRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void testGetAllUsersSorted() {
        int page = 0;
        int size = 10;
        String sortField = "userName";
        String sortOrder = "asc";
        Long roleId = 1L;
        Sort sort = Sort.by(sortField).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> expectedPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        when(userRepository.findUsersByRoleRoleId(roleId, pageRequest)).thenReturn(expectedPage);

        Page<User> result = userService.getAllUsersSorted(page, size, sortField, sortOrder, roleId);

        assertNotNull(result);
        assertEquals(expectedPage, result);

        verify(userRepository, times(1)).findUsersByRoleRoleId(roleId, pageRequest);
    }

    @Test
    void testFindByUserNameUserFound() {
        String userName = "testUser";
        User user = new User();

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        User result = userService.findByUserName(userName);

        assertNotNull(result);
        assertEquals(user, result);

        verify(userRepository, times(1)).findByUserName(userName);
    }

    @Test
    void testFindByUserNameUserNotFound() {
        String userName = "nonexistentUser";

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUserName(userName));

        verify(userRepository, times(1)).findByUserName(userName);
    }

    @Test
    void testFindByFieldContainingIgnoreCase() {
        String fieldName = "userName";
        String searchValue = "test";
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(userRepository.findByFieldContainingIgnoreCase(fieldName, searchValue, pageable)).thenReturn(expectedPage);

        Page<User> result = userService.findByFieldContainingIgnoreCase(fieldName, searchValue, pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);

        verify(userRepository, times(1)).findByFieldContainingIgnoreCase(fieldName, searchValue, pageable);
    }

}

