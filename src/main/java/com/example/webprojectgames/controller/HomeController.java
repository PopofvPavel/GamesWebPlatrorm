package com.example.webprojectgames.controller;

import com.example.webprojectgames.api.wiki.WikipediaParserService;
import com.example.webprojectgames.model.entities.Role;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.GameComparisonService;
import com.example.webprojectgames.services.SteamApiService;
import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(Model model) {

        return "redirect:/games";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username is already in use");
            return "register";
        }

        Role role = userService.getRoleByRoleName("ROLE_USER");
        user.setRoleId(role.getRoleId());

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @Autowired
    private SteamApiService steamApiService;

    @Autowired
    private WikipediaParserService wikipediaParserService;


    @Autowired
    private GameComparisonService gameComparisonService;

    @GetMapping("/2023")
    @ResponseBody
    public List<SteamGame> get2007Games() {
        return gameComparisonService.getMatchingGames("2011");
        //return wikipediaParserService.extractGameTitles("2023");
    }

   /* @GetMapping("/api/{appId}")
    @ResponseBody
    public List<SteamReview> getGameDescription(@PathVariable long appId) {


        return steamApiService.getSteamReviews(appId);
    }*/

}
