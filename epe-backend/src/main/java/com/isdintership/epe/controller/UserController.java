package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssignedUserDto;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final String origin = "http://localhost:4200";

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable(name = "id") String id) {
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/{id}/assignedUsers")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<AssignedUserDto>> getAllAssignedUsers(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getAssignedUsers(id));
    }

}