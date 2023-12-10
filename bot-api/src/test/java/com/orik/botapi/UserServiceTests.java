package com.orik.botapi;

import com.orik.botapi.DAO.UserRepository;
import com.orik.botapi.DTO.user.UserConverterDTO;
import com.orik.botapi.DTO.user.UserRegistrationDTO;
import com.orik.botapi.entity.User;
import com.orik.botapi.exception.UserNotFoundException;
import com.orik.botapi.service.impl.UserServiceImpl;
import com.orik.botapi.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverterDTO userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testIsRegisteredTrue() {
        long chatId = 123456;
        when(userRepository.findByChatId(chatId)).thenReturn(Optional.of(new User()));

        assertTrue(userService.isRegistered(chatId));

        verify(userRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void testIsRegisteredFalse() {
        long chatId = 123456;
        when(userRepository.findByChatId(chatId)).thenReturn(Optional.empty());

        assertFalse(userService.isRegistered(chatId));

        verify(userRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO(1L,"test","test","test");
        User user = new User();

        when(userConverter.convertToEntity(registrationDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(registrationDTO);

        assertNotNull(result);
        verify(userConverter, times(1)).convertToEntity(registrationDTO);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByChatId() {
        long chatId = 123456;
        User user = new User();

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.of(user));

        User result = userService.findByChatId(chatId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByChatId(chatId);
    }

    @Test
    void testFindByChatIdNotFound() {
        long chatId = 123456;

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByChatId(chatId));

        verify(userRepository, times(1)).findByChatId(chatId);
    }
}
