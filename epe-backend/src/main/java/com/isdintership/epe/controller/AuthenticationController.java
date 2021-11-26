package com.isdintership.epe.controller;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userService.register(registrationRequest));
    }

    @GetMapping("jobs")
    public ResponseEntity<List<JobsDto>> getJobTitles(){
        return ResponseEntity.ok(userService.getJobTitles());
    }

}
