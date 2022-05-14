package com.dan.cosmos.service;

import com.dan.cosmos.exception.CustomException;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.RefreshToken;
import com.dan.cosmos.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Long refreshTokenValidity;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               @Value("${security.jwt.refresh-token.expire-length}") Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken generateNewRefreshToken(HttpServletRequest request) {

        String refreshTokenValue = resolveRefreshToken(request);
        if (refreshTokenValue == null) {
            throw new CustomException("Refresh token not present", HttpStatus.BAD_REQUEST);
        }

        RefreshToken oldRefreshToken = findByToken(refreshTokenValue);
        if (oldRefreshToken == null) {
            throw  new CustomException("Refresh token not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        refreshTokenRepository.delete(oldRefreshToken);
        validateRefreshtoken(oldRefreshToken);

        RefreshToken newRefreshToken = createRefreshToken(oldRefreshToken.getAppUser(), request);

        return newRefreshToken;
    }

    public boolean validateRefreshtoken(RefreshToken refreshToken) {
        Date now = new Date();
        if (refreshToken.getExpiresIn() < now.getTime()) {
            throw new CustomException("Expired refresh token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(c -> "refresh-token".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (refreshTokenCookie != null) {
            return refreshTokenCookie.getValue();
        }

        return null;
    }

    public RefreshToken createRefreshToken(AppUser appUser, HttpServletRequest request) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAppUser(appUser);
        refreshToken.setUUID(UUID.randomUUID().toString());

        Date now = new Date();
        refreshToken.setCreated(now);
        refreshToken.setExpiresIn(now.getTime() + refreshTokenValidity);

        refreshToken.setUserAgent(request.getHeader("User-Agent"));
        refreshToken.setIp(request.getRemoteAddr());

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByUUID(token);
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }


    public void removeRefreshToken(HttpServletRequest request) {
        String refreshTokenValue = resolveRefreshToken(request);
        if (refreshTokenValue == null) {
            return;
        }
        RefreshToken refreshToken = findByToken(refreshTokenValue);
        deleteRefreshToken(refreshToken);
    }
}
