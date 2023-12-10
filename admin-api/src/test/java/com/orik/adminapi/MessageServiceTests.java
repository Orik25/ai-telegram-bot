package com.orik.adminapi;

import com.orik.adminapi.DAO.MessageRepository;
import com.orik.adminapi.DTO.message.MessageConverterDTO;
import com.orik.adminapi.DTO.message.NewMessageDTO;
import com.orik.adminapi.entity.Message;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTests {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageConverterDTO messageConverterDTO;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void testAddNew() {
        NewMessageDTO newMessageDTO = new NewMessageDTO("testText",1L,2L);
        Message convertedMessage = new Message();
        when(messageConverterDTO.convertToEntity(newMessageDTO)).thenReturn(convertedMessage);
        when(messageRepository.save(convertedMessage)).thenReturn(convertedMessage);

        Message result = messageService.addNew(newMessageDTO);

        assertNotNull(result);
        assertEquals(convertedMessage, result);

        verify(messageConverterDTO, times(1)).convertToEntity(newMessageDTO);
        verify(messageRepository, times(1)).save(convertedMessage);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        Sort sort = Sort.by(Sort.Order.desc("messageId"));
        List<Message> expectedMessages = Arrays.asList(new Message(), new Message());
        when(messageRepository.findByFromUserUserIdOrToUserUserId(userId, userId, sort)).thenReturn(expectedMessages);

        List<Message> result = messageService.findByUserId(userId);

        assertNotNull(result);
        assertEquals(expectedMessages, result);

        verify(messageRepository, times(1)).findByFromUserUserIdOrToUserUserId(userId, userId, sort);
    }


    @Test
    void testDeleteByUserId() {
        Long userId = 1L;

        messageService.deleteByUserId(userId);

        verify(messageRepository, times(1)).deleteByFromUserUserIdOrToUserUserId(userId, userId);
    }

}
