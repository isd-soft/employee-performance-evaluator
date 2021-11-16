package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.Team;
import com.isdintership.epe.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String teamLeaderId;

    @NotNull
    private List<String> membersIds;

    public static TeamDto fromTeam(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setId(team.getId());
        teamDto.setName(team.getName());


        if (team.getTeamLeader() != null) {
            teamDto.setTeamLeaderId(team.getTeamLeader().getId());
        }

        if (team.getMembers() != null) {
            List<String> membersIds = new ArrayList<>();
            for (User user : team.getMembers()) {
                membersIds.add(user.getId());
            }
            teamDto.setMembersIds(membersIds);
        }

        return teamDto;

    }

}
