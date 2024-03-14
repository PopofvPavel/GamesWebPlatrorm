package com.example.webprojectgames.controller;


import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.*;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import com.example.webprojectgames.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

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
    @PostMapping("/search")
    public String searchGames(Model model, @RequestParam String query) {
        List<Game> searchedGames = gameService.searchGames(query);
        model.addAttribute("games", searchedGames);
        return "games";
    }

    @PostMapping("/{id}/rate")
    public String rateGame(@PathVariable("id") int id, @RequestParam("rating") int ratingValue, Model model) {
        User currentUser = getCurrentUser();
        Rating rating = new Rating(currentUser.getUserId(), id, ratingValue);
        if (ratingService.hasUserRatedGame(currentUser.getUserId(), id)) {
            ratingService.rewriteRating(rating);
            return "redirect:/games/" + id;
        }

        ratingService.saveRating(rating);
        return "redirect:/games/" + id;
    }


    @GetMapping("/{id}")
    public String showGameDetails(@PathVariable(value = "id") long id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            throw new GameNotFoundException("This game is not found in games list: " + id);
            //return "not-found";
        }
        List<Rating> ratings = ratingService.findByGameId(id);
        List<ReviewInterface> reviews = reviewService.findReviewByGameId(id);
        List<ReviewInterface> steamReviews = reviewService.findSteamReviewByGameId(id);
        reviews.addAll(steamReviews);


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
        User currentUser = getCurrentUser();
        List<Game> userCollection = gameService.getUserGamesCollection(currentUser.getUserId());
        model.addAttribute("userCollection", userCollection);
        return "user-collection";

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();
        return userService.findByUsername(userName);
    }

    @GetMapping("/add")
    public String showAddGamePage(Model model) {
        Game game = new Game();
        model.addAttribute("game", game);

        List<Genre> allGenres = genreService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        List<Long> selectedGenres = new ArrayList<>();
        model.addAttribute("selectedGenres", selectedGenres);

        return "add-game";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute("game") Game game,
                          @RequestParam(value = "steamId", required = false) Long steamId,
                          BindingResult result, Model model) {

        model.addAttribute("steamId", steamId);
        if (result.hasErrors()) {
            model.addAttribute("error", "Some fields are invalid");

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

        User editor = userService.findByUsername(editorUsername);
        game.setEditorId(editor.getUserId());

        try {
            gameService.saveGame(game);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Could not save game, maybe this game already exists");
        }
        return "redirect:/games";
    }

    @PostMapping("/load")
    public String searchSteamGame(@RequestParam("steamId") long steamId, Model model) {
        SteamGame steamGame = steamApiService.getSteamGame(steamId);
        model.addAttribute("steamId", steamId);
        if (steamGame == null) {

            model.addAttribute("error", "Game not found on Steam");
            return "add-game";
        }

        List<Platform> platforms = platformService.getPlatformsByNames(steamGame.getPlatform());
        Game game = new Game(steamGame.getTitle(), steamGame.getDescription(), steamGame.getReleaseDate(),
                platforms, steamGame.getDeveloper(), steamGame.getImage_url());
        //steamGame.getPlatform().get(0), steamGame.getDeveloper());
        game.setSteamId(steamId);

        List<Genre> genres = genreService.getGenresByDescription(steamGame.getGenres());
        game.setGenres(genres);
        List<Genre> allGenres = genreService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("selectedGenres", new ArrayList<Long>());



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
        model.addAttribute("steamId", steamId);
        Game game = gameService.findById(id);
        if (game == null) {
            throw new GameNotFoundException("This game is not found in games list: " + id);
            //return "not-found";
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

        game.setSteamId(steamId);

        List<Genre> genres = genreService.getGenresByDescription(steamGame.getGenres());
        game.setGenres(genres);
        List<Genre> allGenres = genreService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("selectedGenres", new ArrayList<Long>());


        model.addAttribute("game", game);
        return "edit-game";
    }


    @PostMapping("/{id}/load-steam-reviews")
    public String loadSteamReviews(@PathVariable("id") int id, Model model) {
        Game game = gameService.findById(id);
        if (game == null || game.getSteamId() == null) {
            throw new GameNotFoundException("This game is not found in games list: " + id);
            //return "not-found";
        }

        long steamId = game.getSteamId();
        List<SteamReview> steamReviews = steamApiService.getSteamReviews(steamId, game.getGameId());
        System.out.println("Steam reviews before save" + steamReviews);
        reviewService.saveSteamReviews(steamReviews);

        return "redirect:/games/" + id;
    }


    @GetMapping("/{id}/edit")
    public String showEditGameForm(@PathVariable("id") int id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            throw new GameNotFoundException("This game is not found in games list: " + id);
            //return "not-found";
        }
        model.addAttribute("game", game);
        List<Genre> allGenres = genreService.getAllGenres();
        model.addAttribute("allGenres", allGenres);
        model.addAttribute("selectedGenres", new ArrayList<Long>());

        return "edit-game";
    }

    @PostMapping("/{id}/edit")
    public String editGame(@ModelAttribute("game") Game game, @PathVariable("id") int id,
                           BindingResult result,
                           @RequestParam(value = "steamId", required = false) Long steamId,
                           Model model) {


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

        System.out.println("Steam id: " + steamId);
        existingGame.setSteamId(steamId);
        existingGame.setTitle(game.getTitle());
        existingGame.setDescription(game.getDescription());
        existingGame.setReleaseDate(game.getReleaseDate());
        existingGame.setPlatform(game.getPlatform());
        existingGame.setDeveloper(game.getDeveloper());
        existingGame.setImageUrl(game.getImageUrl());
        existingGame.setGenres(game.getGenres());

        try {
            gameService.saveGame(existingGame);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Could not save game, maybe this game already exists");
        }
        return "redirect:/games/" + id;
    }

    @PostMapping("/{id}//add-review")
    public String saveReview(@ModelAttribute("comment") String comment, @ModelAttribute("game") Game game, @PathVariable("id") int id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Some fields are invalid");
            return "redirect:/games/" + id;
        }

        if (comment == null || comment.isEmpty()) {
            model.addAttribute("error", "Comment should not be empty");
            return "redirect:/games/" + id;
        }

        // Получение текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Создание нового объекта Review
        Review review = new Review();
        review.setGameId(id);
        review.setUser(user);
        review.setComment(comment);
        // Установка текущей даты
        review.setDate(new Date());

        // Сохранение отзыва в базе данных
        reviewService.saveReview(review);
        return "redirect:/games/" + id;
    }

    @GetMapping("/{id}/save-to-collection")
    public String saveGameToCollectionGame(@PathVariable("id") long id, Model model) {
        Game existingGame = gameService.findById(id);
        if (existingGame == null) {
            return "not-found";
        }

    /*    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        int userId = user.getUserId();*/

        User user = getCurrentUser();
        int userId = user.getUserId();
        userService.saveGameToUserCollection(new UserGameCollection(userId, id, new Date()));
        //System.out.println("Saving game usr id = " + userId + " game id = " + id);
        return "redirect:/games/collection";
    }


}
