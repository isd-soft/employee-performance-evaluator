package com.isdintership.epe.controller;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.exception.StatusNotFoundException;
import com.isdintership.epe.service.TeamService;
import com.isdintership.epe.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;

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
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN, ROLE_USER})
    public ResponseEntity<TeamDto> getTeam(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(teamService.getTeam(id), HttpStatus.OK);
    }

    @GetMapping(value = "")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN, ROLE_USER})
    public ResponseEntity<?> getAllTeams(@RequestParam(name = "count", required = false) String count) {

        if (count != null) {
            if (count.equals("leaders")) {
                return ResponseEntity.ok(teamService.countTeamLeaders());
            }
            throw new StatusNotFoundException("Count " + count + " was not found");
        }

        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/members")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<List<UserDto>> getTeamMembers(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(teamService.getTeamMembers(id));
    }

    @GetMapping(value = "/{id}/leader")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<UserDto> getTeamLeader(@PathVariable(name = "id")String id) {
        return ResponseEntity.ok(teamService.getTeamLeader(id));
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
