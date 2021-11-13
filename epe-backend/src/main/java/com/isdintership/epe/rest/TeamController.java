package com.isdintership.epe.rest;

import com.isdintership.epe.dto.Response;
import com.isdintership.epe.dto.TeamView;
import com.isdintership.epe.service.TeamService;
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
@RequestMapping(value = "/api/")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping(value = "teams")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<Response> createTeam (@RequestBody TeamView teamView) {

        return new ResponseEntity<>(teamService.createTeam(teamView), HttpStatus.CREATED);

    }

}
