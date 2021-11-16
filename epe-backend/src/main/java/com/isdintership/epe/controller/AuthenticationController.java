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
    private final String origin = "http://localhost:4200";

    @PostMapping("login")
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserView> login(@RequestBody LoginRequest requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @PostMapping("register")
    @CrossOrigin(origins = origin)
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userService.register(registrationRequest));
    }

    @GetMapping("jobs")
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<JobsDto>> getJobTitles(){
        return ResponseEntity.ok(userService.getJobTitles());
    }

}
