package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GamesController.class)
@AutoConfigureMockMvc
@Import({TestDataSourceConfig.class})
class GamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private SteamApiService steamApiService;

    @MockBean
    private PlatformService platformService;


    @Test
    @WithMockUser(username="user", roles={"USER"})
    void showGamesPage() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(view().name("games"))
                .andExpect(model().attributeExists("games"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void rateGame() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        mockMvc.perform(post("/games/1/rate")
                        .param("rating", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void showGameDetails() throws Exception {

        Game game = new Game("Test Game", "Test description", new Date(), null, "Test developer", "https://example.com/test.jpg");
        game.setGameId(1);
        when(gameService.findById(1)).thenReturn(game);

        mockMvc.perform(get("/games/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game-details"))
                .andExpect(model().attributeExists("game"))
                .andExpect(model().attributeExists("averageRating"))
                .andExpect(model().attributeExists("reviews"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void getUserCollectionPage() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        List<Game> userGamesCollection = new ArrayList<>(); // Пустая коллекция игр
        when(gameService.getUserGamesCollection(anyInt())).thenReturn(userGamesCollection);


        mockMvc.perform(get("/games/collection"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-collection"))
                .andExpect(model().attributeExists("userCollection"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void showAddGamePage() throws Exception {
        mockMvc.perform(get("/games/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-game"))
                .andExpect(model().attributeExists("game"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void addGameWithSteamId() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        mockMvc.perform(post("/games/add")
                        .param("title", "Test Game")
                        .param("steamId", "12345")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

@Test
    @WithMockUser(username="user", roles={"USER"})
    void addGameWithoutSteamId() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        mockMvc.perform(post("/games/add")
                        .param("title", "Test Game")

                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void searchSteamGame() throws Exception {
        SteamGame steamGameMock = new SteamGame("Test Game", "Test description", new Date(2024, 3, 1), Collections.singletonList("Windows"), "Test developer", "https://example.com/test.jpg");
        //steamGameMock.set
        when(steamApiService.getSteamGame(anyLong())).thenReturn(steamGameMock);

        mockMvc.perform(post("/games/load")
                        .param("steamId", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-game"))
                .andExpect(model().attributeExists("game"))
                .andExpect(model().attribute("steamId", 12345L));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void searchSteamGameForEditPage() throws Exception {
        when(gameService.findById(1)).thenReturn(new Game("Test Game", "Test description",  new Date(2024,03,01), null, "Test developer", "https://example.com/test.jpg"));

        when(steamApiService.getSteamGame(anyLong())).thenReturn(new SteamGame("Test Game Updated", "Updated description",  new Date(2024,03,01), Collections.singletonList("Windows"), "Test developer", "https://example.com/test.jpg"));

        mockMvc.perform(post("/games/1/edit/load")
                        .param("steamId", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-game"))
                .andExpect(model().attributeExists("game"))
                .andExpect(model().attribute("steamId", 12345L));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void loadSteamReviews() throws Exception {
        Game mockGame = new Game("Test Game", "Test description", new Date(2024, 03, 01), null, "Test developer", "https://example.com/test.jpg");
        mockGame.setSteamId(12345L);
        when(gameService.findById(1)).thenReturn(mockGame);
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);
        when(steamApiService.getSteamReviews(anyInt(), anyInt())).thenReturn(new ArrayList<SteamReview>());

        mockMvc.perform(post("/games/1/load-steam-reviews")
                        .param("steamId", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void showEditGameForm() throws Exception {
        when(gameService.findById(1)).thenReturn(new Game("Test Game", "Test description",  new Date(2024,03,01), null, "Test developer", "https://example.com/test.jpg"));

        mockMvc.perform(get("/games/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-game"))
                .andExpect(model().attributeExists("game"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void editGame() throws Exception {
        when(gameService.findById(1)).thenReturn(new Game("Test Game", "Test description",  new Date(2024,03,01), null, "Test developer", "https://example.com/test.jpg"));
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        mockMvc.perform(post("/games/1/edit")
                        .param("title", "Test Game Updated")
                        .param("steamId", "12345")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void saveReview() throws Exception {
        mockMvc.perform(post("/games/1/add-review")
                        .param("comment", "Test review")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void saveGameToCollectionGame() throws Exception {
        when(gameService.findById(1)).thenReturn(new Game("Test Game", "Test description",  new Date(2024,03,01), null, "Test developer", "https://example.com/test.jpg"));
        User mockUser = new User();
        mockUser.setUserId(1);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        mockMvc.perform(get("/games/1/save-to-collection"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/collection"));
    }


}