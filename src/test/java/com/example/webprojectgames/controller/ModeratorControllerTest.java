package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(ModeratorController.class)
@AutoConfigureMockMvc
@Import({TestDataSourceConfig.class})
class ModeratorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username="user", roles={"MODERATOR"})
    void showUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/moderator/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.view().name("moderator"));
    }

    @Test
    @WithMockUser(username = "moderator", roles = { "MODERATOR" })
    void changeUserStatus() throws Exception {
        User user = new User();
        user.setUserId(1);

        when(userService.findByUsername(anyString())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/moderator/change-status").param("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/moderator/users"));

    }


    @Test
    @WithMockUser(username="user", roles={"MODERATOR"})
    void updateRoles() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/moderator/update-roles").param("userId", "1").param("roleId", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/moderator/users"));
    }

}