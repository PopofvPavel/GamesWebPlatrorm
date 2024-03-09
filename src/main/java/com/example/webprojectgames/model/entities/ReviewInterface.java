package com.example.webprojectgames.model.entities;


import java.util.Date;

public interface ReviewInterface {

    long getGameId();
    String getUsername();
    String getComment();
    Date getDate();
}
