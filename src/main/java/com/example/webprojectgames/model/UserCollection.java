package com.example.webprojectgames.model;

public class UserCollection {
    private int collectionId;
    private int userId;
    private int[] gameIds;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int[] getGameIds() {
        return gameIds;
    }

    public void setGameIds(int[] gameIds) {
        this.gameIds = gameIds;
    }
}
