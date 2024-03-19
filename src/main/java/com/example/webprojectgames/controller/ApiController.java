package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.GameService;
import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class ApiController {

    final
    GameService gameService;
    final
    UserService userService;

    public ApiController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/load")
    public String getLoadGamesPage() {
        return "load-games-page";
    }

    @PostMapping("/load-games-wiki")
    public String loadGamesFromSteamUsingWiki(@RequestParam("year") String year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String editorUsername = authentication.getName();
        User editor = userService.findByUsername(editorUsername);

        gameService.loadGamesFromSteamUsingWiki(year,editor.getUserId());

        System.out.println("Loaded all games from wiki");
        return "redirect:/";
    }
}
