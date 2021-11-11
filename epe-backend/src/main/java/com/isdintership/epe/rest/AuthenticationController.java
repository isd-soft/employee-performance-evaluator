package com.isdintership.epe.rest;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody LoginRequest requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @PostMapping("register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userService.register(registrationRequest);
        return ResponseEntity.ok(new SuccessResponse("User registered successfully"));
    }

}
