package com.example.webprojectgames.api.telegram;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.GameService;
import com.example.webprojectgames.services.NotificationService;
import com.example.webprojectgames.services.UserService;
import com.example.webprojectgames.services.UserStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.webprojectgames.model.entities.UserState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * Класс для обработки обновлений и отправки сообщений через Telegram бота.
 * Также имеет ScheduledExecutorService для отправки уведомлений по таймеру
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${telegram.bot.username}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String token;

    private final UserStateService userStateService;

    private final UserService userService;

    private final NotificationService notificationService;

    private final GameService gameService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TelegramBot(UserStateService userStateService, UserService userService, NotificationService notificationService, GameService gameService) {
        this.userStateService = userStateService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.gameService = gameService;
    }

    /**
     * Обрабатывает полученное сообщение.
     * Реагирует в зависимости от UserState,
     * которое показывает на каком этапе находится пользователь
     * в чате тг бота
     * @param update Обновление из Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        logger.info("On update receive method. ChatID = " + chatId + ", Message = " + inputText);


        Optional<UserState> userStateOptional = userStateService.findUserStateByChatId(chatId);
        String currentState = userStateOptional.map(UserState::getState).orElse("START");

        switch (currentState) {
            case "START":
                handleStartState(chatId);
                break;
            case "ENTER_USERNAME":
                handleUsernameInput(chatId, inputText);
                break;
            case "ENTER_CODE":
                handleEnterCodeInput(chatId);
                break;
            case "AUTHORIZED":
                handleAuthorizedState(chatId, inputText);
                break;
            default:
                logger.error("Unknown user state: {}", currentState);
        }


    }
    /**
     * Обрабатывает состояние авторизованного пользователя.
     *
     * @param chatId     Идентификатор чата пользователя.
     * @param inputText  Введенный текст пользователем.
     */
    private void handleAuthorizedState(Long chatId, String inputText) {
        Optional<User> userOptional = userService.findUserByChatId(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (inputText.startsWith("/notifications")) {
                handleNotificationsCommand(user);
            } else if (inputText.startsWith("/mycollection")) {
                handleMyCollectionCommand(user);
            } else {
                sendTextMessage(user.getTelegramChatId(), "Команда не распознана. Пожалуйста, используйте:\n" +
                        " /notifications для получения новых уведомлениий\n" +
                        "/mycollection для получения ващей коллекции игр.");
            }
        }
    }
    /**
     * Обрабатывает команду для просмотра коллекции игр пользователя.
     *
     * @param user  Пользователь.
     */
    private void handleMyCollectionCommand(User user) {
        List<Game> userCollection = gameService.getUserGamesCollection(user.getUserId());
        StringBuilder response = new StringBuilder("Ваша коллекция игр:\n");
        for (Game game : userCollection) {
            response.append(game.getTitle()).append("\n");
        }
        sendTextMessage(user.getTelegramChatId(), response.toString());
    }
    /**
     * Обрабатывает команду для просмотра уведомлений пользователя.
     *
     * @param user  Пользователь.
     */
    private void handleNotificationsCommand(User user) {
        List<Notification> notifications = notificationService.getUserUnsendNotifications(user);
        for (Notification notification : notifications) {
            sendTextMessage(user.getTelegramChatId(), "Новое уведомление: " + notification.getMessage());
            notification.setNotified(true);
            notificationService.save(notification);
        }
    }
    /**
     * Обрабатывает ввод кода пользователем.
     *
     * @param chatId  Идентификатор чата пользователя.
     */
    private void handleEnterCodeInput(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Чтобы продолжить работу с ботом вы должны ввести код в вашем профиле");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }
    }
    /**
     * Обрабатывает начальное состояние пользователя.
     *
     * @param chatId  Идентификатор чата пользователя.
     */
    private void handleStartState(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать в бота! Пожалуйста, введите ваш username:");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }
        userStateService.updateUserState(chatId, "ENTER_USERNAME");
    }

    /**
     * Обрабатывает ввод имени пользователя.
     *
     * @param chatId    Идентификатор чата пользователя.
     * @param username  Имя пользователя.
     */
    private void handleUsernameInput(Long chatId, String username) {
        User user = userService.findByUsername(username);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (user != null) {
            String code = generateCode();
            userStateService.updateUserState(chatId, "ENTER_CODE");
            userStateService.saveCode(chatId, code);
            userStateService.setUserId(chatId, user.getUserId());
            user.setTelegramChatId(chatId);
            userService.saveUser(user);
            message.setText("Хорошо, теперь введите данный код на сайте (страница профиля): " + code);
        } else {
            message.setText("Пользователь не найден, попробуйте еще раз");
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage());
        }

    }

    /**
     * Генерирует код для подтверждения аккаунта.
     *
     * @return Сгенерированный код.
     */
    private String generateCode() {
        // Генерируем случайное четырехзначное число
        Random random = new Random();
        int code = random.nextInt(10_000);
        return String.valueOf(code);
    }
    /**
     * Отправляет текстовое сообщение пользователю.
     *
     * @param chatId  Идентификатор чата пользователя.
     * @param text    Текст сообщения.
     */
    public void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    /**
     * Планирует отправку уведомления в указанное время.
     *
     * @param notification       Уведомление для отправки.
     * @param notificationTime   Время отправки уведомления.
     */
    public void scheduleNotification(Notification notification, LocalDateTime notificationTime) {
        Optional<User> userOptional = userService.findById(notification.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getTelegramChatId() != null) {
                LocalDateTime now = LocalDateTime.now();
                Duration delay = Duration.between(now, notificationTime);

                scheduler.schedule(() -> sendNotification(user, notification), delay.getSeconds(), TimeUnit.SECONDS);
            } else {
                logger.error("This user has got no telegram chat id");
            }
        } else {
            logger.error("User not found for userId: " + notification.getUserId());
        }

    }
    /**
     * Отправляет уведомление пользователю.
     *
     * @param user          Пользователь.
     * @param notification  Уведомление для отправки.
     */
    private void sendNotification(User user, Notification notification) {
        sendTextMessage(user.getTelegramChatId(), notification.getMessage());
        notification.setNotified(true);
        notificationService.save(notification);

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

