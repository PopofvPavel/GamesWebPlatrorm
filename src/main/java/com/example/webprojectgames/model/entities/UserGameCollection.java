package com.example.webprojectgames.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_game_collections")
@IdClass(UserGameCollectionKey.class)
public class UserGameCollection implements Serializable {
    //private int collectionId;
    @Id
    @Column(name = "user_id")
    private int userId;
    @Id
    @Column(name = "game_id")
    private int gameId;
    @Column(name = "date_added")
    private Date date_added;


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

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }
}
