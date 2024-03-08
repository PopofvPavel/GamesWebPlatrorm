package com.example.webprojectgames.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;
    @Column(name = "game_id")
    private int gameId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private Date date;
    @Column(name = "is_blocked")
    private boolean isBlocked;

    public Review(int gameId, int userId, String comment, Date date) {
        this.gameId = gameId;
        this.userId = userId;
        this.comment = comment;
        this.date = date;
    }

    public Review() {
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

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



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
