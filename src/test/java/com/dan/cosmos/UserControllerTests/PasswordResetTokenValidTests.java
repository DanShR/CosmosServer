package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.exception.userException.UserNotFoundException;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.repository.UserRepository;
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
public class PasswordResetTokenValidTests {

    private final String URL = "/users/passwordreset/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/passwordrecovery/username")
                .param("username", "admin")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        AppUser admin = userRepository.findByUsername("admin");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + admin.getPasswordRecoveryToken())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUserNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "wrongtoken")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }


}
