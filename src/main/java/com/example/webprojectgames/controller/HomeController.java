package com.example.webprojectgames.controller;

import com.example.webprojectgames.api.steam.client.SteamApiClient;
import com.example.webprojectgames.api.steam.model.SteamGame;
import com.example.webprojectgames.services.SteamApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String showHomePage(Model model) {

        return "redirect:/games";
    }

    @Autowired
    private  SteamApiService steamApiService;


    @GetMapping("/api/{appId}")
    @ResponseBody
    public SteamGame getGameDescription(@PathVariable long appId) {

        //service
        return steamApiService.getSteamGame(appId);
    }

}
