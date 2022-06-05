package com.dan.cosmos.controller;

import com.dan.cosmos.dto.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> login(@RequestBody UserSignInDTO userSignInDTO, HttpServletRequest request) {
        AppUser appUser = userService.signin(userSignInDTO.getUsername(), userSignInDTO.getPassword());
        String accessToken = userService.createAccessToken(appUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUser, request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", String.format("refresh-token=%s; HttpOnly; Path=/", refreshToken.getUUID()));
        return new ResponseEntity<>(new TokenDTO(accessToken), headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void signup(@RequestBody UserSignUpDTO user) {
        AppUser appUser = modelMapper.map(user, AppUser.class);
        userService.signup(appUser);
    }

    @GetMapping("/confirmemail")
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@Valid @NotBlank @RequestParam(name = "token") String token) {
        userService.confirmEmail(token);
    }

    @GetMapping("/signout")
    public ResponseEntity signout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        refreshTokenService.removeRefreshToken(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "refresh-token=%s; HttpOnly; Path=/; Max-Age=0");
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(HttpServletRequest request) {
        RefreshToken newRefreshToken = refreshTokenService.generateNewRefreshToken(request);
        String accessToken = userService.createAccessToken(newRefreshToken.getAppUser());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", String.format("refresh-token=%s; HttpOnly; Path=/", newRefreshToken.getUUID()));
        return new ResponseEntity<>(new TokenDTO(accessToken), headers, HttpStatus.OK);
    }

    @GetMapping("/passwordrecovery/username")
    @ResponseStatus(HttpStatus.OK)
    public void passwordRecoveryByUsername(@RequestParam String username) {
        userService.recoverPasswordByUsername(username);
    }

    @GetMapping("/passwordrecovery/email")
    @ResponseStatus(HttpStatus.OK)
    public void passwordRecoveryByEmail(@RequestParam String email) {
        userService.recoverPasswordByEmail(email);
    }

    @GetMapping("/passwordreset/{token}")
    @ResponseStatus(HttpStatus.OK)
    public void passwordResetTokenValid(@PathVariable(name = "token") String token) {
        userService.passwordResetTokenValid(token);
    }

    @PostMapping("/changepassword")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO);
    }

    @GetMapping("/me")
    public UserResponseDTO aboutMe(HttpServletRequest request) {
        return modelMapper.map(userService.aboutMe(request), UserResponseDTO.class);
    }

}
