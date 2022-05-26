package com.dan.cosmos.service;

import com.dan.cosmos.exception.CustomException;
import com.dan.cosmos.exception.userException.ExpiredRefreshTokenException;
import com.dan.cosmos.exception.userException.RefreshTokenNotFoundException;
import com.dan.cosmos.exception.userException.RefreshTokenNotPresentException;
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
            throw new RefreshTokenNotPresentException();
        }

        RefreshToken oldRefreshToken = findByToken(refreshTokenValue);
        if (oldRefreshToken == null) {
            throw new RefreshTokenNotFoundException();
        }

        refreshTokenRepository.delete(oldRefreshToken);
        validateRefreshtoken(oldRefreshToken);

        return createRefreshToken(oldRefreshToken.getAppUser(), request);

    }

    public boolean validateRefreshtoken(RefreshToken refreshToken) {
        Date now = new Date();
        if (refreshToken.getExpiresIn() < now.getTime()) {
            throw new ExpiredRefreshTokenException();
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
