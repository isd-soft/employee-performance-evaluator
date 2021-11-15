package com.isdintership.epe.controller;

import com.isdintership.epe.dto.PasswordView;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.RoleView;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.dao.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class    UserController {

    private final UserService userService;

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<UserView>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<UserView> createUser(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<Optional<UserView>> getUserById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<UserView> updateUser(@Valid @RequestBody UserView userView,
                                               @PathVariable(name = "id") String id) {
        return ResponseEntity.ok(userService.updateUser(userView, id));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PatchMapping("users/updatepassword/{id}")
    @RolesAllowed({ROLE_SYSADMIN,ROLE_USER})
    public ResponseEntity<PasswordView> changePassword(@RequestBody PasswordView passwordView,
                                                       @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.changePassword(passwordView,id),HttpStatus.OK);
    }

    @PatchMapping("users/updategroup/{id}")
    @RolesAllowed(ROLE_SYSADMIN)
    public ResponseEntity<RoleView> changeGroup(@RequestBody RoleView roleView,
                                                @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.changeGroup(roleView,id),HttpStatus.OK);
    }
}
