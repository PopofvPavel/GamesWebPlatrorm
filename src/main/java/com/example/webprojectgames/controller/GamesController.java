package com.example.webprojectgames.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GamesController {

    @GetMapping()
    public String showGamesPage() {

        return "games";
    }

    @GetMapping("/add")
    public String showAddGamePage() {

        return "add-game";
    }

}
