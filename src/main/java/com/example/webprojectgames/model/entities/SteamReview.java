package com.example.webprojectgames.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "steam_reviews")
public class SteamReview implements ReviewInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "steam_review_id")
    private Long steamReviewId;

    @Column(name = "game_id")
    private long gameId;
    @Column(name = "username")
    private String username;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private Date date;

    public SteamReview() {

    }


    @Override
    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
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



    public SteamReview(long gameId, String username, String comment, Date date) {
        this.gameId = gameId;
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    // Конструктор, геттеры и сеттеры
}
