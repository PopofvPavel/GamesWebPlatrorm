package com.example.webprojectgames.model.entities;


import java.util.Date;

public interface ReviewInterface {

    int getGameId();
    String getUsername();
    String getComment();
    Date getDate();
}
