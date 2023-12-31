package com.orik.adminapi.security;

import com.orik.adminapi.constant.RoleData;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.CustomAuthenticationException;
import com.orik.adminapi.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private static final String ADMIN = RoleData.ADMIN.getDBRoleName();

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws CustomAuthenticationException {
        User user = userService.findByUserName(userName);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        if (user.getPassword() == null || !user.getRole().getName().equals(ADMIN)) {
            log.error("Error occurred " + userName + " has not access for this service");
            throw new CustomAuthenticationException("Authentication failed");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }
}