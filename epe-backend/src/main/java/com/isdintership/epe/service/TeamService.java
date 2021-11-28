package com.isdintership.epe.service;

import com.isdintership.epe.dto.TeamDto;
import com.isdintership.epe.dto.UserDto;

import java.util.List;

public interface TeamService {

    String createTeam(TeamDto teamView);

    TeamDto getTeam(String id);

    List<TeamDto> getAllTeams();

    TeamDto updateTeam(TeamDto teamView, String id);

    String deleteTeam(String id);

    List<UserDto> getTeamMembers(String id);
}
