package com.dan.cosmos.repository;

import com.dan.cosmos.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    AppUser findByEmailConfirmUUID(String token);
    boolean existsByPasswordRecoveryToken(String token);
    AppUser findByPasswordRecoveryToken(String token);
}
