package com.isdintership.epe.service_implement;


import com.isdintership.epe.dao.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public String createTeam(TeamDto teamView) {
        Optional<Team> existingTeam = teamRepository.findByName(teamView.getName());
        if (existingTeam.isPresent()) {
                throw new TeamExistException("Team with " + teamView.getName() + " already exists");
        }

        User teamLeader = userRepository.findById(teamView.getTeamLeaderId()).orElseThrow(
                () -> new UserNotFoundException("User with id " + teamView.getTeamLeaderId() + "was not found"));


        List<String> membersIds = teamView.getMembersIds();
        if (membersIds.isEmpty()) {
            throw new UserNotFoundException("No ids provided for team members");
        }

        List<User> members = new ArrayList<>();
        for (String id : membersIds) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("User with id " + id + "was not found"));
            members.add(user);
        }

        Team team = new Team();
        team.setName(teamView.getName());
        team.setTeamLeader(teamLeader);
        team.setMembers(members);

        teamRepository.save(team);

        return "Team " + teamView.getName() + " was created successfully";
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
    public TeamDto updateTeam(TeamDto teamView, String id) {

        Team team = teamRepository.findById(id).orElseThrow(() ->
                new TeamNotFoundException("Team with id " + id + " was not found"));

        team.setName(teamView.getName());
        User teamLeader = userRepository.findById(teamView.getTeamLeaderId()).orElseThrow(() ->
                new UserNotFoundException("Team leader with id " + id + " was not found"));
        team.setTeamLeader(teamLeader);

        List<String> membersIds = teamView.getMembersIds();
        if (membersIds.isEmpty()) {
            throw new UserNotFoundException("No ids provided for team members");
        }

        List<User> members = new ArrayList<>();
        for (String memberId : membersIds) {
            User user = userRepository.findById(memberId).orElseThrow(
                    () -> new UserNotFoundException("User with id " + memberId + "was not found"));
            members.add(user);
        }

        team.setMembers(members);

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
