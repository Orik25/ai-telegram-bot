package com.orik.adminapi.controller;

import com.orik.adminapi.DAO.MessageRepository;
import com.orik.adminapi.DAO.UserRepository;
import com.orik.adminapi.DTO.message.MessageFromAdminDTO;
import com.orik.adminapi.DTO.message.NewMessageDTO;
import com.orik.adminapi.DTO.message.TelegramResponse;
import com.orik.adminapi.config.BotConfig;
import com.orik.adminapi.entity.Message;
import com.orik.adminapi.exception.UserNotFoundException;
import com.orik.adminapi.service.interfaces.MessageService;
import com.orik.adminapi.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/system/users")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final RestTemplate restTemplate;

    private final BotConfig botConfig;

    @Autowired
    public MessageController(MessageService messageService, RestTemplate restTemplate, UserService userService, BotConfig botConfig) {
        this.messageService = messageService;
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.botConfig = botConfig;
    }

    @GetMapping("/history/{userId}")
    public String getHistory(@PathVariable Long userId, Model model) {
        model.addAttribute("messages", messageService.findByUserId(userId));
        model.addAttribute("userId", userId);
        return "admin/history";

    }

    @GetMapping("/delete-history/{userId}")
    public String deleteHistory(@PathVariable Long userId, Model model) {
        log.info("Deleting history of user: " + userService.findById(userId).getChatId());
        messageService.deleteByUserId(userId);
        return "redirect:/system/users/history/" + userId;

    }

    @PostMapping("/send-message/{id}")
    public String sendMessage(@PathVariable Long id, @RequestParam String messageToSend) {
        Long chatId;
        try {
            chatId = userService.findById(id).getChatId();
        } catch (UserNotFoundException ex) {
            log.error("Error occurred: " + ex.getMessage());
            return "redirect:/system/users";
        }

        MessageFromAdminDTO messageFromAdminDTO = new MessageFromAdminDTO(chatId, messageToSend);

        String url = "https://api.telegram.org/bot" + botConfig.getToken() + "/sendMessage";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageFromAdminDTO> requestEntity = new HttpEntity<>(messageFromAdminDTO, headers);
        ResponseEntity<TelegramResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, TelegramResponse.class);
        if (response.getBody().isOk()) {
            log.info("Adding new message to db from: " + botConfig.getId() + " to: " + chatId);
            messageService.addNew(new NewMessageDTO(messageToSend, botConfig.getId(), chatId));
        }

        return "redirect:/system/users/history/" + id;
    }
}
