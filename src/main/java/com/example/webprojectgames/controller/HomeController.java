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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Контроллер для логина и регистрации
 */
@Controller
@RequestMapping
public class HomeController {
    private  UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(Model model, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            return "redirect:/moderator/users";
        } else {
            return "redirect:/games";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username is already in already in use");
            System.out.println("in error register block");
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


}
