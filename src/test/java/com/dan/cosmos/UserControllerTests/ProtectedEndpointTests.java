package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ProtectedEndpointTests {

    private final String URL = "/users/me";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldStatusOk() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc,"admin", "123456");
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .header("Authorization", "Bearer " + tokens.get("accessToken"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));
    }

    @Test
    public void shouldIsUnauthorizedCase1() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldIsUnauthorizedCase2() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .header("Authorization", "Bearer sdfsdfsdf.kjsdfhjksdfh.ksdjhfjksdfh")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
