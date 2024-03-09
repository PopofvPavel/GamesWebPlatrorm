package com.example.webprojectgames.model.entities;

import java.io.Serializable;

public class UserGameCollectionKey implements Serializable {
    private long userId;
    private long gameId;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
