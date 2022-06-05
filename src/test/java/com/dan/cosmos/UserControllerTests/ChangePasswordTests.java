package com.dan.cosmos.UserControllerTests;


import com.dan.cosmos.Utils;
import com.dan.cosmos.dto.ChangePasswordDTO;
import com.dan.cosmos.exception.userException.UserNotFoundException;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ChangePasswordTests {

    private final String URL = "/users/changepassword";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/passwordrecovery/username")
                .param("username", "changepassword")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        AppUser appUser = userRepository.findByUsername("changepassword");

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setToken(appUser.getPasswordRecoveryToken());
        changePasswordDTO.setPassword("newpassword");
        changePasswordDTO.setPasswordConfirm("newpassword");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Utils.signIn(mockMvc, "changepassword", "newpassword");

    }

    @Test
    public void shouldUserNotFoundException() throws Exception {

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setToken("wrongtoken");
        changePasswordDTO.setPassword("newpassword");
        changePasswordDTO.setPasswordConfirm("newpassword");


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

}
