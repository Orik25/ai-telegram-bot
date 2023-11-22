package com.orik.botapi.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDTO {
    private Long chatId;
    private String userName;
    private String firstName;
    private String lastName;
}
