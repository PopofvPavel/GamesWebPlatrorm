package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.Role;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc
@Import({TestDataSourceConfig.class})
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void showHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void registerNewUser_UsernameExists() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        when(userService.existsByUsername("existingUser")).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "existingUser")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Username is already in already in use"));
    }

    @Test
    void registerNewUser_Success() throws Exception {
        User user = new User();
        when(userService.existsByUsername(anyString())).thenReturn(false);
        Role mockRole = mock(Role.class);
        when(userService.getRoleByRoleName("ROLE_USER")).thenReturn(mockRole);

        doNothing().when(userService).saveUser(any(User.class));

        mockMvc.perform(post("/register")
                        .param("username", "newUser")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void showLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
