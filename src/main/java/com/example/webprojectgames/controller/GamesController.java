package com.example.webprojectgames.controller;


import com.example.webprojectgames.api.steam.model.SteamGame;
import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Rating;
import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    private GameService gameService;

    private UserService userService;

    private RatingService ratingService;

    private ReviewService reviewService;

    @Autowired
    private SteamApiService steamApiService;

    public void setSteamApiService(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

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

    @GetMapping("/{id}")
    public String showGameDetails(@PathVariable(value = "id") int id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            return "game-not-found";
        }
        List<Rating> ratings = ratingService.findByGameId(id);
        List<Review> reviews = reviewService.findByGameId(id);

        Double averageRating = ratings.stream()
                .mapToDouble(Rating::getRatingValue)
                .average()
                .orElse(0.0);

        model.addAttribute("game", game);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviews", reviews);

        return "game-details"; //
    }


    @GetMapping("/collection")
    public String getUserCollectionPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();


        User currentUser = userService.findByUsername(userName);
        List<Game> userCollection = gameService.getUserGamesCollection(currentUser.getUserId());
        model.addAttribute("userCollection", userCollection);
        return "user-collection";

    }

    @GetMapping("/add")
    public String showAddGamePage(Model model) {
        Game game = new Game();
        model.addAttribute("game", game);
        return "add-game";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute("game") Game game, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Some fields are invalid");

            //sout the error message
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }

            return "add-game";
        }

        if (game.getTitle() == null || game.getTitle().isEmpty()) {
            model.addAttribute("error", "Title is required");
            return "add-game";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String editorUsername = authentication.getName();

        // Получение идентификатора пользователя (editorId) по его имени
        User editor = userService.findByUsername(editorUsername);
        //System.out.println("editor: " + editor);
        // Установка editorId в объекте game
        game.setEditorId(editor.getUserId());
        //System.out.println("User id : " + editor.getUserId());

        gameService.saveGame(game);
        return "redirect:/games";
    }

    @PostMapping("/load")
    public String searchSteamGame(@RequestParam("steamId") long steamId, Model model) {
        SteamGame steamGame = steamApiService.getSteamGame(steamId);

        if (steamGame == null) {
            model.addAttribute("error", "Game not found on Steam");
            return "add-game";
        }

        Game game = new Game(steamGame.getTitle(), steamGame.getDescription(), steamGame.getReleaseDate(),
                steamGame.getPlatform().get(0), steamGame.getDeveloper());
        model.addAttribute("game", game);
        return "add-game";
    }

    @GetMapping("/{id}/edit")
    public String showEditGameForm(@PathVariable("id") int id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            return "not-found";
        }
        model.addAttribute("game", game);
        return "edit-game";
    }

    @PostMapping("/{id}/edit")
    public String editGame(@ModelAttribute("game") Game game, @PathVariable("id") int id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Some fields are invalid");
            return "edit-game";
        }

        if (game.getTitle() == null || game.getTitle().isEmpty()) {
            model.addAttribute("error", "Title is required");
            return "edit-game";
        }

        Game existingGame = gameService.findById(id);
        if (existingGame == null) {
            return "not-found";
        }

        existingGame.setTitle(game.getTitle());
        existingGame.setDescription(game.getDescription());
        existingGame.setReleaseDate(game.getReleaseDate());
        existingGame.setPlatform(game.getPlatform());
        existingGame.setDeveloper(game.getDeveloper());
        existingGame.setImageUrl(game.getImageUrl());

        gameService.saveGame(existingGame);
        return "redirect:/games/" + id;
    }

}
