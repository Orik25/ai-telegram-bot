package com.orik.botapi.service.impl;

import com.orik.botapi.DAO.MessageRepository;
import com.orik.botapi.DTO.message.MessageConverterDTO;
import com.orik.botapi.DTO.message.NewMessageDTO;
import com.orik.botapi.entity.Message;
import com.orik.botapi.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageConverterDTO messageConverterDTO;
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,MessageConverterDTO messageConverterDTO) {
        this.messageRepository = messageRepository;
        this.messageConverterDTO = messageConverterDTO;
    }

    @Override
    public Message addNew(NewMessageDTO message) {
        return messageRepository.save(messageConverterDTO.convertToEntity(message));
    }
}
