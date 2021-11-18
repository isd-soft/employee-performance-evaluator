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

    private UserDto teamLeader;
    private List<UserDto> members;

    public static TeamDto fromTeam(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setId(team.getId());
        teamDto.setName(team.getName());

        User teamLeader = team.getTeamLeader();
        List<User> members = team.getMembers();

        if (teamLeader != null) {
            UserDto teamLeaderView = new UserDto();

            teamLeaderView.setId(teamLeader.getId());
            teamLeaderView.setFirstname(teamLeader.getFirstname());
            teamLeaderView.setLastname(teamLeader.getLastname());

            teamDto.setTeamLeader(teamLeaderView);
        }

        if (members != null) {

            List<UserDto> membersViews = new ArrayList<>();

            for (User user : members) {

                UserDto memberView = new UserDto();

                memberView.setId(user.getId());
                memberView.setFirstname(user.getFirstname());
                memberView.setLastname(user.getLastname());

                membersViews.add(memberView);

            }

            teamDto.setMembers(membersViews);
        }

        return teamDto;

    }

}
