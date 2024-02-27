package com.example.webprojectgames.controller;


import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.repositories.GamesRepository;
import com.example.webprojectgames.services.GameService;
import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    private GameService gameService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
   public void setGameService(GameService gameService) {
       this.gameService = gameService;
   }

    @GetMapping()
    public String showGamesPage(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "games";
    }

    @GetMapping("/collection")
    public String getUserCollectionPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();


        User currentUser = userService.getCurrentUser(userName);
        List<Game> userCollection = gameService.getUserGamesCollection(currentUser.getUserId());
        model.addAttribute("userCollection", userCollection);
        return "user-collection";

    }

    @GetMapping("/add")
    public String showAddGamePage() {

        return "add-game";
    }

}
