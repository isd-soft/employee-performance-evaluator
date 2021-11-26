package com.isdintership.epe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/roles")
public class RolesController {


    @GetMapping
    public List<String> getAllRoles() {
        return Arrays.asList("User","Administrator");
    }

}
