package com.example.webprojectgames.api.steam.client;


import com.example.webprojectgames.api.steam.model.SteamGame;
import com.example.webprojectgames.api.steam.model.SteamGameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SteamApiClient {

    @Value("${steam.api.key}") //described in properties
    private String apiKey;

    final SteamGameMapper steamGameMapper;

    private final RestTemplate restTemplate;


    public SteamApiClient(SteamGameMapper steamGameMapper) {
        this.restTemplate = new RestTemplate();
        this.steamGameMapper = steamGameMapper;
    }

    public SteamGame getGameDescription(long appId) {
        System.out.println("In get desctiption method");
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        //return  response.getBody();
        return steamGameMapper.mapSteamGame(response.getBody());
    }
}