package com.isdintership.epe.rest;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping(value = "/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<UserView>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("admin/users")
    @RolesAllowed({ROLE_SYSADMIN})
    public ResponseEntity<UserView> createUser(@Valid @RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserView> getUserById(@PathVariable(name = "id") String id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("users/{id}")
    @RolesAllowed({ROLE_SYSADMIN, ROLE_USER})
    public ResponseEntity<UserView> updateUser(@Valid @RequestBody UserView userView,
                                               @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.updateUser(userView, id), HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    @RolesAllowed(ROLE_SYSADMIN)
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
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
