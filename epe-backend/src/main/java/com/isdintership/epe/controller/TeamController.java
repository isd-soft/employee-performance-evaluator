package com.isdintership.epe.controller;

import com.isdintership.epe.dao.TeamService;
import com.isdintership.epe.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_ADMIN;
import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_SYSADMIN;

@RestController
@RequestMapping(value = "/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping(value = "")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> createTeam (@RequestBody TeamDto teamView) {

        return new ResponseEntity<>(teamService.createTeam(teamView), HttpStatus.CREATED);

    }

}
