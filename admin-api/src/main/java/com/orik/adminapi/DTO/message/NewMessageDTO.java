package com.orik.adminapi.DTO.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewMessageDTO {
    private String text;
    private Long fromId;
    private Long toId;
}
