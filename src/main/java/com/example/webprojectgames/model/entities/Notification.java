package com.example.webprojectgames.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private long notificationId;
    @Column(name = "user_id")
    private int userId;

    public Notification() {
    }

    @Column(name = "game_id")
    private int gameId;

    public Notification(int userId, int gameId, String message) {
        this.userId = userId;
        this.gameId = gameId;
        this.message = message;
        this.isNotified = false;
    }

    @Column(name = "message")
    private String message;
    @Column(name = "is_notified")
    private boolean isNotified;

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String notificationType) {
        this.message = notificationType;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }
}
