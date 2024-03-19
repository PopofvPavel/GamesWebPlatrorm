package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.repositories.NotificationRepository;
import com.example.webprojectgames.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NotificationRepository notificationRepository;


    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getUserUnsendNotifications(User user) {
        return notificationRepository.findByUserIdAndIsNotified(user.getUserId(),false);


    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }


}
