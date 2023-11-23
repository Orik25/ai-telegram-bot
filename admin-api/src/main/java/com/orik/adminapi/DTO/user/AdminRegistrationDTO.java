package com.orik.adminapi.DTO.user;

import lombok.Data;

@Data
public class AdminRegistrationDTO {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
}
