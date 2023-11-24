package com.orik.adminapi.security;

import com.orik.adminapi.exception.CustomAuthenticationException;
import com.orik.adminapi.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService,BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws CustomAuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            checkPassword(password,userDetails);
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } catch (UserNotFoundException e) {
            log.info("User with username: "+userName+" - not found");
            throw new CustomAuthenticationException("Authentication failed: " + e.getMessage());
        }
    }
    private void checkPassword(String password,UserDetails userDetails){
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.info("User "+userDetails.getUsername()+" - invalid password");
            throw new CustomAuthenticationException("Authentication failed: Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}