package com.orik.adminapi.DTO.user;

import com.orik.adminapi.validation.UniqueUserName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdminRegistrationDTO {
    @UniqueUserName
    @NotBlank(message = "Username is mandatory")
    private String userName;
    @Pattern(message = "Must contain at least 1 capital letter, at least 1 number, at least 8 characters",
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$")
    private String password;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    private String lastName;
}
