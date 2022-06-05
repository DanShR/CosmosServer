package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.dto.UserSignUpDTO;
import com.dan.cosmos.exception.userException.EmailExistException;
import com.dan.cosmos.exception.userException.UsernameExistException;
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
public class SignUpTests {

    private final String URL = "/users/signup";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldStatusOk() throws Exception {
        UserSignUpDTO signUpDTO = new UserSignUpDTO();
        signUpDTO.setEmail("newuser1@newuser1.com");
        signUpDTO.setPassword("123456");
        signUpDTO.setFirstName("newuser1");
        signUpDTO.setUsername("newuser1");
        signUpDTO.setLastName("newuser1");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUsernameExistException() throws Exception {
        UserSignUpDTO signUpDTO = new UserSignUpDTO();
        signUpDTO.setEmail("newemail@admin.com");
        signUpDTO.setPassword("123456");
        signUpDTO.setFirstName("admin");
        signUpDTO.setUsername("admin");
        signUpDTO.setLastName("admin");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameExistException));
    }

    @Test
    public void shouldEmailExistException() throws Exception {
        UserSignUpDTO signUpDTO = new UserSignUpDTO();
        signUpDTO.setEmail("admin@admin.com");
        signUpDTO.setPassword("123456");
        signUpDTO.setFirstName("admin");
        signUpDTO.setUsername("newuseradmin");
        signUpDTO.setLastName("admin");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailExistException));
    }
}
