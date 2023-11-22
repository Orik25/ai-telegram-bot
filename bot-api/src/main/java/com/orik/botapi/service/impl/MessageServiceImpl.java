package com.orik.botapi.service.impl;

import com.orik.botapi.DAO.MessageRepository;
import com.orik.botapi.DTO.message.MessageConverterDTO;
import com.orik.botapi.DTO.message.NewMessageDTO;
import com.orik.botapi.entity.Message;
import com.orik.botapi.exception.UserNotFoundException;
import com.orik.botapi.service.interfaces.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageConverterDTO messageConverterDTO;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageConverterDTO messageConverterDTO) {
        this.messageRepository = messageRepository;
        this.messageConverterDTO = messageConverterDTO;
    }

    @Override
    public Message addNew(NewMessageDTO message) {
        log.info("Adding new message to history form: " + message.getFromId() + "to: " + message.getToId());
        Message newMessage = null;
        try{
            newMessage = messageConverterDTO.convertToEntity(message);
        }catch (UserNotFoundException ex){
            log.error("Error occurred: " + ex.getMessage());
        }
        if(newMessage == null){
            return null;
        }
        else {
            return newMessage;
        }
    }
}
