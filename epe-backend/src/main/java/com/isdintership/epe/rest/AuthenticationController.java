package com.isdintership.epe.rest;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final String origin = "http://localhost:4200";

    private final UserService userService;

    @PostMapping("login")
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserView> login(@RequestBody LoginRequest requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @PostMapping("register")
    @CrossOrigin(origins = origin)
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userService.register(registrationRequest);
        return ResponseEntity.ok(new SuccessResponse("User registered successfully"));
    }

}
