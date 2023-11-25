package com.orik.adminapi.service.interfaces;

import com.orik.adminapi.DTO.message.NewMessageDTO;
import com.orik.adminapi.entity.Message;

import java.util.List;

public interface MessageService {
    Message addNew(NewMessageDTO message);
    List<Message> findByUserId(Long id);
    void deleteByUserId(Long id);
}
