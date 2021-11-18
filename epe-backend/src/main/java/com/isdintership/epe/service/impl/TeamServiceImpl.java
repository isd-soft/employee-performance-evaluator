package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.exception.InvalidTeamNameException;
import com.isdintership.epe.service.TeamService;
import com.isdintership.epe.dto.TeamDto;
import com.isdintership.epe.entity.Team;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.TeamExistException;
import com.isdintership.epe.exception.TeamNotFoundException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.TeamRepository;
import com.isdintership.epe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class TeamServiceImpl implements TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public String createTeam(TeamDto teamDto) {
        Optional<Team> existingTeam = teamRepository.findByName(teamDto.getName());
        if (existingTeam.isPresent()) {
                throw new TeamExistException("Team with " + teamDto.getName() + " already exists");
        }

        Team team = new Team();

        if (teamDto.getTeamLeader() != null) {
            User teamLeader = userRepository.findById(teamDto.getTeamLeader().getId()).orElseThrow(
                    () -> new UserNotFoundException("User with id " + teamDto.getTeamLeader().getId() + "was not found"));
            team.setTeamLeader(teamLeader);
        }

        if (teamDto.getMembers() != null) {

            List<UserDto> membersViews = teamDto.getMembers();
            List<User> members = new ArrayList<>();

            for (UserDto memberView : membersViews) {
                User user = userRepository.findById(memberView.getId()).orElseThrow(
                        () -> new UserNotFoundException("User with id " + memberView.getId() + "was not found"));
                members.add(user);
            }

            team.setMembers(members);
        }

        team.setName(teamDto.getName());
        teamRepository.save(team);

        return "Team " + teamDto.getName() + " was created successfully";
    }

    @Override
    @Transactional
    public TeamDto getTeam(String id) {

        Team team = teamRepository.findById(id).orElseThrow(() ->
                new TeamNotFoundException("Team with id " + id + " was not found"));

        return TeamDto.fromTeam(team);

    }

    @Override
    @Transactional
    public List<TeamDto> getAllTeams() {

        List<TeamDto> teamDtos = new ArrayList<>();

        for (Team team : teamRepository.findAll()) {
            teamDtos.add(TeamDto.fromTeam(team));
        }

        return teamDtos;
    }

    @Override
    @Transactional
    public TeamDto updateTeam(TeamDto teamDto, String id) {

        if (teamDto.getName() == null || teamDto.getName().equals("")) {
            throw new InvalidTeamNameException("Team name cannot be null or empty");
        }
        Team team = teamRepository.findById(id).orElseThrow(() ->
                new TeamNotFoundException("Team with id " + id + " was not found"));

        team.setName(teamDto.getName());

        if (teamDto.getTeamLeader() != null) {
            User teamLeader = userRepository.findById(teamDto.getTeamLeader().getId()).orElseThrow(
                    () -> new UserNotFoundException("User with id " + teamDto.getTeamLeader().getId() + "was not found"));
            team.setTeamLeader(teamLeader);
        } else {
            team.setTeamLeader(null);
        }

        if (teamDto.getMembers() != null) {
            teamDto.getMembers().clear();
            List<UserDto> membersViews = teamDto.getMembers();

            for (UserDto memberView : membersViews) {
                User user = userRepository.findById(memberView.getId()).orElseThrow(
                        () -> new UserNotFoundException("User with id " + memberView.getId() + "was not found"));
                team.getMembers().add(user);
            }
        } else {
            teamDto.setMembers(new ArrayList<>());
        }


        return TeamDto.fromTeam(team);

    }

    @Override
    @Transactional
    public String deleteTeam(String id) {

        teamRepository.findById(id).orElseThrow(() ->
                new TeamNotFoundException("Team with id " + id + " was not found"));

        teamRepository.deleteById(id);

        return "Team with id " + id + " was deleted successfully";

    }

}
