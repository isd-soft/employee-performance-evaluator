package com.isdintership.epe.controller;

import com.isdintership.epe.dao.TeamService;
import com.isdintership.epe.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import java.util.List;

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

    @GetMapping(value = "/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<TeamDto> getTeam(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(teamService.getTeam(id), HttpStatus.OK);
    }

    @GetMapping(value = "")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<TeamDto> updateTeam(
            @PathVariable(name = "id") String id,
            @RequestBody TeamDto teamDto) {

        return new ResponseEntity<>(teamService.updateTeam(teamDto, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> deleteTeam(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(teamService.deleteTeam(id), HttpStatus.OK);
    }

}
