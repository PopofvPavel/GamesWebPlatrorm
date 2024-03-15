package com.example.webprojectgames.controller;

import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/moderator")
@PreAuthorize("hasRole('MODERATOR')")
public class ModeratorController {
    private final UserService userService;

    @Autowired
    public ModeratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getAllRoles());
        return "moderator";
    }

    @PostMapping("/change-status")
    public String changeUserStatus(@RequestParam Integer userId) {
        userService.changeUserStatus(userId);
        return "redirect:/moderator/users";
    }

    @PostMapping("/update-roles")
    public String updateRoles(@RequestParam Integer userId, @RequestParam Integer roleId) {
        userService.updateUserRole(userId, roleId);
        return "redirect:/moderator/users";
    }

}
