package com.orik.adminapi;

import com.orik.adminapi.DAO.RoleRepository;
import com.orik.adminapi.entity.Role;
import com.orik.adminapi.exception.RoleNotFoundException;
import com.orik.adminapi.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testGetAdminRole() {
        Role adminRole = new Role();
        adminRole.setRoleId(2L);
        adminRole.setName("ROLE_ADMIN");

        when(roleRepository.findById(2L)).thenReturn(Optional.of(adminRole));

        Role result = roleService.getAdminRole();

        assertEquals(adminRole, result);
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void testGetAdminRoleNotFound() {
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getAdminRole());
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void testGetUserRole() {
        Role userRole = new Role();
        userRole.setRoleId(3L);
        userRole.setName("ROLE_USER");

        when(roleRepository.findById(3L)).thenReturn(Optional.of(userRole));

        Role result = roleService.getUserRole();

        assertEquals(userRole, result);
        verify(roleRepository, times(1)).findById(3L);
    }

    @Test
    void testGetUserRoleNotFound() {
        when(roleRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getUserRole());
        verify(roleRepository, times(1)).findById(3L);
    }
}
