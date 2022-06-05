package com.dan.cosmos.UserControllerTests;

import com.dan.cosmos.Utils;
import com.dan.cosmos.exception.userException.ExpiredRefreshTokenException;
import com.dan.cosmos.exception.userException.RefreshTokenNotFoundException;
import com.dan.cosmos.exception.userException.RefreshTokenNotPresentException;
import com.dan.cosmos.model.RefreshToken;
import com.dan.cosmos.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class RefreshTokenTests {

    private final String URL = "/users/refresh";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Test
    public void shouldStatusOk() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");
        Cookie cookie = new Cookie("refresh-token", tokens.get("refreshToken"));
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldOldRefreshtokenIsNull() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");
        Cookie cookie = new Cookie("refresh-token", tokens.get("refreshToken"));
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        RefreshToken oldRefreshToken = refreshTokenRepository.findByUUID(tokens.get("refreshToken"));
        assertTrue(oldRefreshToken == null);
    }

    @Test
    public void shouldExpiredRefreshTokenException() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");
        Cookie cookie = new Cookie("refresh-token", tokens.get("refreshToken"));

        RefreshToken refreshToken = refreshTokenRepository.findByUUID(tokens.get("refreshToken"));
        refreshToken.setExpiresIn((long) 0);
        refreshTokenRepository.save(refreshToken);

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ExpiredRefreshTokenException));
    }

    @Test
    public void shouldRefreshTokenNotPresentExceptionCase1() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .header("Authorization", "Bearer " + tokens.get("accessToken"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RefreshTokenNotPresentException));
    }

    @Test
    public void shouldRefreshTokenNotPresentExceptionCase2() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");

        Cookie cookie = new Cookie("wrongname-refresh-token", tokens.get("refreshToken"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .header("Authorization", "Bearer " + tokens.get("accessToken"))
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RefreshTokenNotPresentException));
    }

    @Test
    public void shouldRefreshTokenNotFoundException() throws Exception {

        HashMap<String, String> tokens = Utils.signIn(mockMvc, "admin", "123456");

        Cookie cookie = new Cookie("refresh-token", "token-wrong-value");

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .header("Authorization", "Bearer " + tokens.get("accessToken"))
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RefreshTokenNotFoundException));
    }

}
