package com.example.webprojectgames.api.telegram;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.TelegramService;
import com.example.webprojectgames.services.UserService;
import com.example.webprojectgames.services.UserStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.webprojectgames.model.entities.UserState;
import com.example.webprojectgames.repositories.UserStateRepository;

import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${telegram.bot.username}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String token;

    private final UserStateService userStateService;

    private final UserService userService;

    public TelegramBot(UserStateService userStateService, UserService userService) {
        this.userStateService = userStateService;
        this.userService = userService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        logger.info("On update receive method. ChatID = " + chatId + ", Message = " + inputText);


        Optional<UserState> userStateOptional = userStateService.findUserStateByChatId(chatId);
        String currentState = userStateOptional.map(UserState::getState).orElse("START");

        // Обработка в зависимости от текущего состояния
        switch (currentState) {
            case "START":
                // Обработка начального состояния
                handleStartState(chatId);
                break;
            case "ENTER_USERNAME":
                // Обработка ввода имени пользователя
                handleUsernameInput(chatId, inputText);
                break;
            // Другие обработки для остальных состояний
            default:
                // Если состояние не определено, выполните какое-то действие по умолчанию или выведите сообщение об ошибке
                logger.error("Unknown user state: {}", currentState);
        }


    }

    // Метод для обработки начального состояния
    private void handleStartState(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Welcome to the bot! Please enter your username:");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }
        userStateService.updateUserState(chatId, "ENTER_USERNAME");
    }

    // Метод для обработки ввода имени пользователя
    private void handleUsernameInput(Long chatId, String username) {
        User user = userService.findByUsername(username);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (user != null) {
            String code = "1111";
            message.setText("Thank you! Now enter this code in your user page on web site: " + code);
            userStateService.updateUserState(chatId,"ENTER_CODE");
        } else {
            message.setText("User was not found, please try again");
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }

    }

    private void doRegisterUserInBot(String inputText, Long chatId) {

    }


    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public String getBotName() {
        return botName;
    }


    public String getToken() {
        return token;
    }


}

