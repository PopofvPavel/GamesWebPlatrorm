package com.example.webprojectgames.controller;


import com.example.webprojectgames.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GamesController {

    private GamesRepository gamesRepository;

    @Autowired
    public void setGamesRepository(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }


    @GetMapping()
    public String showGamesPage(Model model) {
        model.addAttribute("games", gamesRepository.findAll());
        return "games";
    }

    @GetMapping("/add")
    public String showAddGamePage() {

        return "add-game";
    }

}
