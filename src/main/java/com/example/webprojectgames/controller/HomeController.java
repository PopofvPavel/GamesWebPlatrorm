package com.example.webprojectgames.controller;

import com.example.webprojectgames.api.wiki.WikipediaParserService;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.services.SteamApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String showHomePage(Model model) {

        return "redirect:/games";
    }

    @Autowired
    private  SteamApiService steamApiService;

    @Autowired
    private WikipediaParserService wikipediaParserService;

    @GetMapping("/2023")
    @ResponseBody
    public List<String> get2007Games() {
        return wikipediaParserService.extractGameTitles("2023");
    }

   /* @GetMapping("/api/{appId}")
    @ResponseBody
    public List<SteamReview> getGameDescription(@PathVariable long appId) {


        return steamApiService.getSteamReviews(appId);
    }*/

}
