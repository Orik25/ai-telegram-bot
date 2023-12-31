package com.orik.botapi.service.impl;


import com.orik.botapi.DTO.message.NewMessageDTO;
import com.orik.botapi.DTO.user.UserRegistrationDTO;
import com.orik.botapi.config.BotConfig;
import com.orik.botapi.config.ChatGPTConfig;
import com.orik.botapi.exception.UserNotFoundException;
import com.orik.botapi.service.interfaces.MessageService;
import com.orik.botapi.service.interfaces.UserService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final ChatGPTConfig chatGPTConfig;
    private final MessageService messageService;
    private final UserService userService;
    private static final String HELP_TEXT = "This bot is created to communicate with Chat GPT from Telegram\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /help to see this message again";

    @Autowired
    public TelegramBot(BotConfig botConfig, ChatGPTConfig chatGPTConfig, UserService userService, MessageService messageService) {
        this.botConfig = botConfig;
        this.chatGPTConfig = chatGPTConfig;
        this.userService = userService;
        this.messageService = messageService;

        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "get greeting message"));
        commands.add(new BotCommand("/help", "get info how to use"));
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot`d command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();

            try {
                messageService.addNew(new NewMessageDTO(messageText, chatId, botConfig.getId()));
            } catch (UserNotFoundException ex) {
                log.error("Error occurred: " + ex.getMessage());
            }

            switch (messageText) {
                case "/start" -> {
                    if (userService.isRegistered(chatId)) {
                        greeting(chatId, firstName);
                    } else {
                        userService.registerUser(new UserRegistrationDTO(chatId, update.getMessage().getChat().getUserName(), update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName()));
                        messageService.addNew(new NewMessageDTO(messageText, chatId, botConfig.getId()));
                        greeting(chatId, firstName);
                    }
                }
                case "/help" -> sendMessage(chatId, HELP_TEXT);
                default -> sendMessage(chatId, getChatResponse(List.of(messageText)));
            }

        }
    }

    private void greeting(long chatId, String name) {
        sendMessage(chatId, "Hi, " + name + ", nice to meet you");

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            log.info("Replying to " + chatId);
            execute(sendMessage);
            try {
                messageService.addNew(new NewMessageDTO(textToSend, botConfig.getId(), chatId));
            } catch (UserNotFoundException ex) {
                log.error("Error occurred: " + ex.getMessage());
            }
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private String getChatResponse(List<String> messages) {
        List<ChatMessage> prompt = new ArrayList<>();
        for (String message : messages) {
            prompt.add(new ChatMessage("user", message));
        }
        OpenAiService service = new OpenAiService(chatGPTConfig.getToken(), Duration.ofSeconds(20));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(prompt)
                .model(chatGPTConfig.getModel())
                .build();

        try {
            return service.createChatCompletion(completionRequest).getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("Timeout exception occurred: " + e.getMessage());
            log.info("Sending the message again!");
            try {
                return service.createChatCompletion(completionRequest).getChoices().get(0).getMessage().getContent();
            }catch (Exception ex){
                log.error("Timeout exception occurred: " + ex.getMessage());
                return "Sorry, the response time has expired!";
            }
        }


    }
}
