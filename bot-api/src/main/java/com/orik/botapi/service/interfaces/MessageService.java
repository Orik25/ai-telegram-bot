package com.orik.botapi.service.interfaces;

import com.orik.botapi.DTO.message.NewMessageDTO;
import com.orik.botapi.entity.Message;

public interface MessageService {
    Message addNew(NewMessageDTO message);
}
