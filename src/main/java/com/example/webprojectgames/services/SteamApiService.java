package com.example.webprojectgames.services;

import com.example.webprojectgames.api.steam.model.SteamGame;
import org.springframework.stereotype.Service;

@Service
public interface SteamApiService {

     SteamGame getSteamGame(long id);



}
