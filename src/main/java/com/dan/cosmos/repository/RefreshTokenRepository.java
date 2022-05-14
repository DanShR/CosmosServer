package com.dan.cosmos.repository;

import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByAppUser(AppUser appUser);
    RefreshToken findByUUID(String UUID);
}
