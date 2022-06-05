package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.exception.userException.EmailAlreadyConfirmedException;
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
public class ConfirmEmailTests {

    private final String URL = "/users/confirmemail";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldStatusOk() throws Exception {

        AppUser confirmemailUser = userRepository.findByUsername("confirmemail");

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param("token", confirmemailUser.getEmailConfirmUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(userRepository.findByUsername("confirmemail").isEnabled());
    }


    @Test
    public void shouldUserNotFoundException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param("token", "wrongtoken")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    public void shouldBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param("token", "")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldEmailAlreadyConfirmedException() throws Exception {

        AppUser adminUser = userRepository.findByUsername("admin");
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param("token", adminUser.getEmailConfirmUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailAlreadyConfirmedException));
    }

}
