package com.dan.cosmos.security;

import com.dan.cosmos.exception.userException.ExpiredInvalidTokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/api/users/refresh".equals(request.getRequestURI())) {
            String token = jwtTokenProvider.resolveToken(request);
            try {
                if (token != null && jwtTokenProvider.validateToken(token)) {
                    Authentication auth = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (ExpiredInvalidTokenException e) {
                SecurityContextHolder.clearContext();
                response.sendError(401, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
