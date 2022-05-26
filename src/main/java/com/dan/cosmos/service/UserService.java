package com.dan.cosmos.service;

import com.dan.cosmos.dto.ChangePasswordDTO;
import com.dan.cosmos.exception.userException.*;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.AppUserRole;
import com.dan.cosmos.repository.UserRepository;
import com.dan.cosmos.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AppUser signin(String username, String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userRepository.findByUsername(username);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernamePasswordException();
        } catch (DisabledException e) {
            throw new UserDisabledException();
        } catch (LockedException e) {
            throw new UserLockedException();
        } catch (AuthenticationException e) {
            throw new UserAuthenticationException(e.getMessage());
        }
    }

    public boolean signup(AppUser appUser) {
        if (userRepository.existsByUsername(appUser.getUsername())) {
            throw new UsernameExistException();
        }
        if (userRepository.existsByEmail(appUser.getEmail())) {
            throw new EmailExistException();
        }

        if (appUser.getAppUserRoles() == null || appUser.getAppUserRoles().size() == 0) {
            appUser.setAppUserRoles(Collections.singletonList(AppUserRole.ROLE_CLIENT));
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setEmailConfirmUUID(UUID.randomUUID().toString());
        userRepository.save(appUser);
        emailService.sendConfirmEmail(appUser);
        return true;
    }

    public AppUser aboutMe(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        return userRepository.findByUsername(username);
    }

    public String createAccessToken(AppUser appUser) {
        return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());

    }

    public Long usersCount() {
        return userRepository.count();
    }

    public boolean confirmEmail(String token) {
        AppUser appUser = userRepository.findByEmailConfirmUUID(token);
        if (appUser == null) {
            throw new UserNotFoundException();
        }
        if (appUser.isEnabled()) {
            throw new EmailAlreadyConfirmedException();
        }
        appUser.setEnabled(true);
        userRepository.save(appUser);
        return true;
    }

    public void recoverPasswordByUsername(String username) {
        AppUser appUser = userRepository.findByUsername(username);
        recoverPassword(appUser);
    }

    public void recoverPasswordByEmail(String email) {
        AppUser appUser = userRepository.findByEmail(email);
        recoverPassword(appUser);
    }

    public void recoverPassword(AppUser appUser) {
        if (appUser == null) {
            throw new UserNotFoundException();
        }
        appUser.setPasswordRecoveryToken(UUID.randomUUID().toString());
        userRepository.save(appUser);
        emailService.sendPasswordRecoveryEmail(appUser);        
    }

    public boolean passwordResetTokenValid(String token) {
        return userRepository.existsByPasswordRecoveryToken(token);
    }

    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        AppUser appUser = userRepository.findByPasswordRecoveryToken(changePasswordDTO.getToken()) ;
        if (appUser == null) {
            throw new UserNotFoundException();
        }
        return changeAppUserPassword(appUser, changePasswordDTO.getPassword());
    }

    public boolean changeAppUserPassword(AppUser appUser, String password) {
        appUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(appUser);
        return true;
    }
}
