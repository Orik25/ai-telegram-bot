package com.orik.adminapi.DTO.message;

import com.orik.adminapi.DAO.MessageRepository;
import com.orik.adminapi.entity.Message;
import com.orik.adminapi.entity.User;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class MessageConverterDTO {
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageConverterDTO(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message convertToEntity(NewMessageDTO messageDTO) throws UserNotFoundException {
        Message newMessage = new Message();
        newMessage.setText(messageDTO.getText());
        newMessage.setDateAndTime(LocalDateTime.now());
        User userFrom;
        User userTo;
        userFrom = userService.findByChatId(messageDTO.getFromId());
        newMessage.setFromUser(userFrom);
        userTo = userService.findByChatId(messageDTO.getToId());
        newMessage.setToUser(userTo);

        return newMessage;
    }
}
