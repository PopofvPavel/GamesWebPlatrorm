package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserState;
import com.example.webprojectgames.services.UserService;
import com.example.webprojectgames.services.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 *  Контроллер для операций с пользовательской страницей(профилем)
 */
@Controller
@RequestMapping("/users")
public class UserController
{
    private  UserStateService userStateService;
    private  UserService userService;
    @Autowired
    public void setUserStateService(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String showUserPage(Model model) {
        User user = getCurrentUser();
        String telegramCode = userStateService.getTelegramCode(user.getUserId());
        System.out.println("Telegram code: " + telegramCode);
        if (telegramCode != null) {
            model.addAttribute("hasCode", true);
        }
        UserState userState = userStateService.findUserStateByUserId((long) user.getUserId()).orElse(null);
        System.out.println("userState" + userState);
        if (userState != null) {
            String state = userState.getState();
            model.addAttribute("state", state);
        }
        return "user-page";

    }

    @PostMapping("/code")
    public String checkCodeOnUserUpdate(@RequestParam("code") String code) {
        User user = getCurrentUser();
        String telegramCode = userStateService.getTelegramCode(user.getUserId());
        if (telegramCode != null && telegramCode.equals(code)) {
            userStateService.updateUserState(user.getTelegramChatId(), "AUTHORIZED");

            return "redirect:/users";
        } else {
            // Код неверный, возвращаемся на страницу пользователя с сообщением об ошибке
            return "redirect:/users?error";
        }
    }

    @PostMapping("/unlink")
    public String unlinkBotAccount() {
        User user = getCurrentUser();
        userStateService.deleteState(user.getTelegramChatId());
        user.setTelegramChatId(null);
        userService.saveUser(user);
        return "redirect:/users";
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();
        return userService.findByUsername(userName);
    }
}
