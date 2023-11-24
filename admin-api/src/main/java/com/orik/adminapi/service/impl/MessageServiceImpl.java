package com.orik.adminapi.service.impl;

import com.orik.adminapi.DAO.MessageRepository;
import com.orik.adminapi.DTO.message.MessageConverterDTO;
import com.orik.adminapi.DTO.message.NewMessageDTO;
import com.orik.adminapi.entity.Message;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.interfaces.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return messageRepository.save(newMessage);
        }
    }

    @Override
    public List<Message> findByUserId(Long id) {
        return messageRepository.findByFromUserUserIdOrToUserUserId(id,id);
    }
}
