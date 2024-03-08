package com.example.webprojectgames.controller;


import com.example.webprojectgames.api.steam.model.SteamGame;
import com.example.webprojectgames.model.entities.*;
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


    private SteamApiService steamApiService;

    private PlatformService platformService;

    @Autowired
    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    @Autowired
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


        System.out.println("game url : " + game.getImageUrl());
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

        List<Platform> platforms = platformService.getPlatformsByNames(steamGame.getPlatform());
        Game game = new Game(steamGame.getTitle(), steamGame.getDescription(), steamGame.getReleaseDate(),
                platforms, steamGame.getDeveloper(),steamGame.getImage_url());
                //steamGame.getPlatform().get(0), steamGame.getDeveloper());
        model.addAttribute("game", game);
        System.out.println("game url : " + game.getImageUrl());
        return "add-game";
    }

    @PostMapping("/{id}/edit/load")
    public String searchSteamGameForEditPage(@PathVariable("id") int id, @RequestParam("steamId") long steamId,
                                             @RequestParam(value = "useTitle", required = false) boolean useTitle,
                                             @RequestParam(value = "useDescription", required = false) boolean useDescription,
                                             @RequestParam(value = "useReleaseDate", required = false) boolean useReleaseDate,
                                             @RequestParam(value = "usePlatform", required = false) boolean usePlatform,
                                             @RequestParam(value = "useDeveloper", required = false) boolean useDeveloper,
                                             @RequestParam(value = "useImageUrl", required = false) boolean useImageUrl,
                                             Model model) {


        //Game game = new Game();
        Game game = gameService.findById(id);
        if (game == null) {
            return "not-found";
        }
        //game.setGameId(id);

        SteamGame steamGame = steamApiService.getSteamGame(steamId);
        if (steamGame == null) {

            model.addAttribute("error", "Game not found on Steam");
            //model.addAttribute("game", ne);
            return "edit-game";
        }


        if (useTitle) {
            game.setTitle(steamGame.getTitle());
        }
        if (useDescription) {
            game.setDescription(steamGame.getDescription());
        }
        if (useReleaseDate) {
            game.setReleaseDate(steamGame.getReleaseDate());
        }
        if (usePlatform) {
            //game.setPlatform(String.join(",", steamGame.getPlatform()));///fix
            /*List<Platform> platforms = steamGame.getPlatform().stream().map(new)*/
            List<Platform> platforms = platformService.getPlatformsByNames(steamGame.getPlatform());
            System.out.println("platforms: " + platforms);
            game.setPlatform(platforms);
        }
        if (useDeveloper) {
            game.setDeveloper(steamGame.getDeveloper());
        }
        if (useImageUrl) {
            game.setImageUrl(steamGame.getImage_url());
            System.out.println(game.getImageUrl());
        }

        model.addAttribute("steamId", steamId);
        model.addAttribute("game", game);
        return "edit-game";
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
