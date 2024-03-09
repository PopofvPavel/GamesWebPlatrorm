package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.ReviewInterface;
import com.example.webprojectgames.model.entities.SteamGame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SteamApiService {

     SteamGame getSteamGame(long id);

     public List<ReviewInterface> getSteamReview(long id);

}
