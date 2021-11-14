package com.isdintership.epe.rest;

import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.entity.RoleEnum;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_ADMIN;
import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_USER;


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
    @RolesAllowed(ROLE_USER)
    public ResponseEntity<UserView> createUser(@Valid @RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserView> getUserById(@PathVariable(name = "id") String id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("users/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<UserView> updateUser(@Valid @RequestBody UserView userView,
                                               @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.updateUser(userView, id), HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
