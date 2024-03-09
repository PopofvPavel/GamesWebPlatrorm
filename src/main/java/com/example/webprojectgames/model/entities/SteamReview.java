package com.example.webprojectgames.model.entities;

import java.util.Date;

public class SteamReview implements ReviewInterface {
    @Override
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private int gameId;
    private String username;
    private String comment;
    private Date date;

    public SteamReview(int gameId, String username, String comment, Date date) {
        this.gameId = gameId;
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    // Конструктор, геттеры и сеттеры
}
