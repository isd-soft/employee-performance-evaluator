package com.isdintership.epe.controller;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<UserDto> updateUserAsAdmin(@Valid @RequestBody UserDto userDto,
                                              @PathVariable(name = "id") String id) throws IOException {
        return ResponseEntity.ok(userService.updateUserAsAdmin(userDto, id));
    }

    @PatchMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable(name = "id") String id) throws IOException {
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("/password/{id}")
    @RolesAllowed({ROLE_SYSADMIN,ROLE_USER})
    @CrossOrigin(origins = origin)
    public ResponseEntity<PasswordView> changePassword(@RequestBody PasswordView passwordView,
                                                       @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.changePassword(passwordView,id),HttpStatus.OK);
    }

    @PutMapping("/group/{id}")
    @RolesAllowed(ROLE_SYSADMIN)
    @CrossOrigin(origins = origin)
    public ResponseEntity<RoleView> changeGroup(@RequestBody RoleView roleView,
                                                @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.changeGroup(roleView,id),HttpStatus.OK);
    }
//    @GetMapping("/subordinates/{id}")
//    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
//    @CrossOrigin(origins = origin)
//    public ResponseEntity<List<SubordinatesDto>> getSubordinates(@PathVariable(name = "id") String id) {
//        return ResponseEntity.ok(userService.getSubordinates(id));
//    }

    @GetMapping("/{id}/assignedUsers")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<AssignedUserDto>> getAllAssignedUsers(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getAssignedUsers(id));
    }

}