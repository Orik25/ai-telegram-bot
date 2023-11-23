package com.orik.adminapi.security;

import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.CustomAuthenticationException;
import com.orik.adminapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName)  throws CustomAuthenticationException{
        User user = userService.findByUserName(userName);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        if(user.getPassword()==null){
            throw new CustomAuthenticationException("Authentication failed");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }
}