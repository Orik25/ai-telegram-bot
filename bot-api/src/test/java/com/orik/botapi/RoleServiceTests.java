package com.orik.botapi;

import com.orik.botapi.DAO.RoleRepository;
import com.orik.botapi.entity.Role;
import com.orik.botapi.exception.RoleNotFoundException;
import com.orik.botapi.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testGetAdminRole() {
        Role adminRole = new Role();
        adminRole.setRoleId(3L);
        adminRole.setName("ROLE_USER");

        when(roleRepository.findById(3L)).thenReturn(Optional.of(adminRole));

        Role result = roleService.getUserRole();

        assertEquals(adminRole, result);
        verify(roleRepository, times(1)).findById(3L);
    }

    @Test
    void testGetAdminRoleNotFound() {
        when(roleRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getUserRole());
        verify(roleRepository, times(1)).findById(3L);
    }
}
