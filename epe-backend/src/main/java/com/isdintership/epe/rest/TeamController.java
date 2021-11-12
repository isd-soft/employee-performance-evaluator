package com.isdintership.epe.rest;

import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.RoleEnum;
import com.isdintership.epe.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_ADMIN;
import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_SYSADMIN;

@RestController
@RequestMapping(value = "/api/")
@RequiredArgsConstructor
public class TeamController {

    private TeamService teamService;

    @PostMapping(value = "admin/teams")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<SuccessResponse> createTeam
            (String name, String teamLeaderId, String[] membersIds) {
        System.out.println(name);
        System.out.println(teamLeaderId);
        System.out.println(membersIds);

        return new ResponseEntity<>(teamService.createTeam(name, teamLeaderId, Arrays.asList(membersIds)), HttpStatus.CREATED);
    }

}
