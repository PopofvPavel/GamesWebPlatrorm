package com.example.webprojectgames.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating
{
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private int ratingId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "game_id")
    private int gameId;

    @Column(name = "rating_value")
    private int ratingValue;

    public Rating() {
    }

    public Rating(int userId, int gameId, int ratingValue) {
        this.userId = userId;
        this.gameId = gameId;
        this.ratingValue = ratingValue;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}
