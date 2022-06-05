package com.dan.cosmos;

import com.dan.cosmos.dto.UserSignInDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

public class Utils {

    public static HashMap<String, String> signIn(MockMvc mockMvc, String username, String password) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        UserSignInDTO userSignInDTO = new UserSignInDTO();
        userSignInDTO.setUsername(username);
        userSignInDTO.setPassword(password);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userSignInDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        HashMap<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", mapper.readTree(result.getResponse().getContentAsString()).get("accessToken").asText());
        tokens.put("refreshToken", result.getResponse().getCookie("refresh-token").getValue());
        return tokens;
    }

}
