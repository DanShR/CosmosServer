package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.dto.UserSignInDTO;
import com.dan.cosmos.exception.userException.InvalidUsernamePasswordException;
import com.dan.cosmos.exception.userException.UserDisabledException;
import com.dan.cosmos.exception.userException.UserLockedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class SignInTests {

    private final String URL = "/users/signin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldStatusOk() throws Exception {

        UserSignInDTO userSignInDTO = new UserSignInDTO();
        userSignInDTO.setUsername("admin");
        userSignInDTO.setPassword("123456");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userSignInDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(cookie().exists("refresh-token"));
    }

    @Test
    public void shouldInvalidUsernamePasswordException() throws Exception {

        HashMap<String, String> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "wrong password");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUsernamePasswordException));
    }

    @Test
    public void shouldUserDisabledException() throws Exception {

        HashMap<String, String> body = new HashMap<>();
        body.put("username", "disabled");
        body.put("password", "123456");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserDisabledException));

    }

    @Test
    public void shouldUserLockedException() throws Exception {

        HashMap<String, String> body = new HashMap<>();
        body.put("username", "locked");
        body.put("password", "123456");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserLockedException));

    }
}
