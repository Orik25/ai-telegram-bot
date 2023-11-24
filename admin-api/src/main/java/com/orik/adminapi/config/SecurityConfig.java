package com.orik.adminapi.config;

import com.orik.adminapi.constant.RoleData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private static final String ADMIN = RoleData.ADMIN.getRoleName();
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/registration").permitAll()
                        .requestMatchers("/system/**").hasRole(ADMIN)
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(successHandler())
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/system/users");

        };
    }
}
