package com.example.webprojectgames.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_bot_states")
public class UserState {


    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "state")
    private String state;

    @Column(name = "code")
    private String code;

    public UserState(Long chatId, Long userId, String state) {
        this.chatId = chatId;
        this.userId = userId;
        this.state = state;
    }

    public UserState() {

    }

    public void setChatId(Long chatId) {

        this.chatId = chatId;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
