package com.dan.cosmos.controller;

import com.dan.cosmos.dto.ChangePasswordDTO;
import com.dan.cosmos.dto.UserResponseDTO;
import com.dan.cosmos.dto.UserSignUpDTO;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.RefreshToken;
import com.dan.cosmos.service.RefreshTokenService;
import com.dan.cosmos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody HashMap<String, String> body, HttpServletRequest request) {
        AppUser appUser = userService.signin(body.get("username"), body.get("password"));
        String accessToken = userService.createAccessToken(appUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUser, request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", String.format("refresh-token=%s; HttpOnly; Path=/", refreshToken.getUUID()));
        return new ResponseEntity<>(accessToken, headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignUpDTO user) {
        AppUser appUser = modelMapper.map(user, AppUser.class);
        if (userService.signup(appUser)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirmemail")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        if (userService.confirmEmail(token)) {
            return new ResponseEntity<>("Email confirmed!",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/signout")
    public ResponseEntity<?> signout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        refreshTokenService.removeRefreshToken(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "refresh-token=%s; HttpOnly; Path=/; Max-Age=0");
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        RefreshToken newRefreshToken = refreshTokenService.generateNewRefreshToken(request);
        String accessToken = userService.createAccessToken(newRefreshToken.getAppUser());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", String.format("refresh-token=%s; HttpOnly; Path=/", newRefreshToken.getUUID()));
        return new ResponseEntity<>(accessToken, headers, HttpStatus.OK);
    }

    @GetMapping("/passwordrecovery/username")
    @ResponseBody
    public ResponseEntity<?> passwordRecoveryByUsername(@RequestParam String username) {
        userService.recoverPasswordByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/passwordrecovery/email")
    public ResponseEntity<?> passwordRecoveryByEmail(@RequestParam String email) {
        userService.recoverPasswordByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/passwordreset/{token}")
    public ResponseEntity<?> passwordResetTokenValid(@PathVariable(name = "token") String token) {
        if (userService.passwordResetTokenValid(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (userService.changePassword(changePasswordDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/me")
    public UserResponseDTO aboutMe(HttpServletRequest request) {
        return modelMapper.map(userService.aboutMe(request), UserResponseDTO.class);
    }

}
