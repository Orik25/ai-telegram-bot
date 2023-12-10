package com.orik.botapi;

import com.orik.botapi.DAO.MessageRepository;
import com.orik.botapi.DTO.message.MessageConverterDTO;
import com.orik.botapi.DTO.message.NewMessageDTO;
import com.orik.botapi.entity.Message;
import com.orik.botapi.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {
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
}
