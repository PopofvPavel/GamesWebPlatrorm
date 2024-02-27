package com.example.webprojectgames.model.entities;

import java.io.Serializable;

public class UserGameCollectionKey implements Serializable {
    private int userId;
    private int gameId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
