package com.example.webprojectgames.controller;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserState;
import com.example.webprojectgames.services.UserService;
import com.example.webprojectgames.services.UserStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Import({TestDataSourceConfig.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  UserStateService userStateService;
    @MockBean
    private  UserService userService;


    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void showUserPageValidUser() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setTelegramChatId(111L);

        UserState mockUserState = new UserState();
        mockUserState.setState("AUTHORIZED");

        when(userService.findByUsername(any())).thenReturn(mockUser);
        when(userStateService.getTelegramCode(1)).thenReturn("telegramCode");
        when(userStateService.findUserStateByUserId(1L)).thenReturn(Optional.of(mockUserState));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("hasCode"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("state"))
                .andExpect(MockMvcResultMatchers.model().attribute("hasCode", true))
                .andExpect(MockMvcResultMatchers.model().attribute("state", "AUTHORIZED"));

        verify(userService).findByUsername(any());
        verify(userStateService).getTelegramCode(1);
        verify(userStateService).findUserStateByUserId(1L);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void showUserPageNoTelegramLink() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1);

        when(userService.findByUsername(any())).thenReturn(mockUser);
        when(userStateService.getTelegramCode(1L)).thenReturn(null);
        when(userStateService.findUserStateByUserId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-page"))
                .andExpect(model().attributeDoesNotExist("hasCode"))
                .andExpect(model().attributeDoesNotExist("state"));

        verify(userService).findByUsername(any());
        verify(userStateService).getTelegramCode(1L);
        verify(userStateService).findUserStateByUserId(1L);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void checkCodeOnUserUpdateValidCode() throws Exception {
        User mockUser = new User();
        mockUser.setTelegramChatId(111L);
        when(userService.findByUsername(any())).thenReturn(mockUser);
        when(userStateService.getTelegramCode(anyLong())).thenReturn("1111");
        doNothing().when(userStateService).updateUserState(anyLong(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/code").param("code", "1111"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).findByUsername(any());
        verify(userStateService).getTelegramCode(anyLong());
        verify(userStateService).updateUserState(anyLong(), anyString());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void checkCodeOnUserUpdateInvalidCode() throws Exception {
        when(userService.findByUsername(any())).thenReturn(new User());
        when(userStateService.getTelegramCode(anyLong())).thenReturn("gg");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/code").param("code", "badCode"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users?error"));

        verify(userService).findByUsername(any());
        verify(userStateService).getTelegramCode(anyLong());
        verify(userStateService, never()).updateUserState(anyLong(), anyString());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void unlinkBotAccountValidLinkUser() throws Exception {
        User mockUser = new User();
        mockUser.setTelegramChatId(111L);
        when(userService.findByUsername(any())).thenReturn(mockUser);
        doNothing().when(userStateService).deleteState(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/unlink"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).findByUsername(any());
        verify(userStateService).deleteState(anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void unlinkBotAccountNotValidLinkUser() throws Exception {
        User mockUser = new User();
        when(userService.findByUsername(any())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/unlink"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).findByUsername(any());

    }
}