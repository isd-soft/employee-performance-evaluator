package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.CountDto;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * {@code TeamServiceImpl} is a service class that implements the {@code TeamService} interface.
 *
 * <p> This class handles all requests to the team table
 * @author Maxim Gribencicov
 * @author Andrei Chetrean
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
class TeamServiceImpl implements TeamService {

    private static final Logger log
            = LoggerFactory.getLogger(TeamServiceImpl.class);
    /**
     * {@code JpaRepository} that handles the access to the user table
     */
    private final UserRepository userRepository;

    /**
     * {@code JpaRepository} that handles the access to the team table
     */
    private final TeamRepository teamRepository;


    /**
     * creates a new team record and updates the users' team id in the data source
     * @param teamDto {@code TeamDto} object that will be inserted in the data source,
     *                if a team with same name doesn't already exist
     * @return a success message if new record created
     * @throws {@code TeamExistException} if a team with same name already exists
     * @throws {@code UserNotFoundException} if a user with the provided id doesn't exist in the data source
     * @since 1.0
     */
    @Override
    @Transactional
    public String createTeam(TeamDto teamDto) {
        Optional<Team> existingTeam = teamRepository.findByName(teamDto.getName());
        if (existingTeam.isPresent()) {
            log.error("Team with " + teamDto.getName() + " already exists");
            throw new TeamExistException("Team with " + teamDto.getName() + " already exists");
        }

        Team team = new Team();

        if (teamDto.getTeamLeader() != null) {
            User teamLeader = userRepository
                    .findById(teamDto.getTeamLeader().getId())
                    .orElseThrow(
                        () -> {
                            log.error("User with id " + teamDto.getTeamLeader().getId() + "was not found");
                            return new UserNotFoundException("User with id " + teamDto.getTeamLeader().getId() + "was not found");
                        });
            team.setTeamLeader(teamLeader);
        }

        if (teamDto.getMembers() != null) {

            List<UserDto> membersViews = teamDto.getMembers();
            List<User> members = new ArrayList<>();

            for (UserDto memberView : membersViews) {
                User user = userRepository.findById(memberView.getId()).orElseThrow(
                        () -> {
                            log.error("User with id " + memberView.getId() + "was not found");
                            return new UserNotFoundException("User with id " + memberView.getId() + "was not found");
                        } );
                members.add(user);
            }

            team.setMembers(members);
        }

        team.setName(teamDto.getName());
        teamRepository.save(team);
        log.info("Team " + teamDto.getName() + " was created successfully");
        return "Team " + teamDto.getName() + " was created successfully";
    }

    /**
     * returns a {@code TeamDto} object containing the details of the team with the provided id
     * @param id the ID of the requested team
     * @return the details of the requested team
     * @throws {@code TeamNotFoundException} if a team with the requested id doesn't exist
     * @since 1.0
     */
    @Override
    @Transactional
    public TeamDto getTeam(String id) {

        Team team = teamRepository.findById(id).orElseThrow(() ->{
            log.error("Team with id " + id + " was not found");
            return new TeamNotFoundException("Team with id " + id + " was not found");
        });
        log.info("Getting team with id " + id);
        return TeamDto.fromTeam(team);
    }

    /**
     * returns all the records from the data source as a list of {@code TeamDto} objects
     * @return a list of teams
     * @since 1.0
     */
    @Override
    @Transactional
    public List<TeamDto> getAllTeams() {

        List<TeamDto> teamDtos = new ArrayList<>();

        for (Team team : teamRepository.findAll()) {
            teamDtos.add(TeamDto.fromTeam(team));
        }

        log.info("Getting all teams");
        return teamDtos;
    }

    /**
     * updates the record of the team with the provided id
     * @param id the ID of team that shall be updated
     * @param teamDto the {@code TeamDto} containing the details of the new team
     * @return the details of the updated team
     * @throws {@code InvalidTeamNameException} if the new team name is null or empty
     * @throws {@code TeamNotFoundException} if a team with the provided ID doesn't exist
     * @throws {@code TeamExistException} if the new team name matches another team name from the database
     * @throws {@code UserNotFoundException} if a user from the team user list with the provided id
     * doesn't exist in the database
     * @since 1.0
     */
    @Override
    @Transactional
    public TeamDto updateTeam(TeamDto teamDto, String id) {

        if (teamDto.getName() == null || teamDto.getName().equals("")) {
            log.error("Team name is empty");
            throw new InvalidTeamNameException("Team name cannot be null or empty");
        }
        Team team = teamRepository
                .findById(id)
                .orElseThrow(() ->{
                    log.error("Team with id " + id + " was not found");
                    return new TeamNotFoundException("Team with id " + id + " was not found");
        });

        if (!team.getName().equals(teamDto.getName())) {
            Optional<Team> existingTeam = teamRepository.findByName(teamDto.getName());
            if (existingTeam.isPresent()) {
                log.error("Team with " + teamDto.getName() + " already exists");
                throw new TeamExistException("Team with " + teamDto.getName() + " already exists");
            }
        }
        
        team.setName(teamDto.getName());

        if (teamDto.getTeamLeader() != null) {
            User teamLeader = userRepository
                    .findById(teamDto.getTeamLeader().getId())
                    .orElseThrow(
                        () -> {
                            log.error("User with id " + teamDto.getTeamLeader().getId() + "was not found");
                            return new UserNotFoundException("User with id " + teamDto.getTeamLeader().getId() + "was not found");
                        });
            team.setTeamLeader(teamLeader);
        } else {
            team.setTeamLeader(null);
        }

        if (teamDto.getMembers() != null) {
            team.getMembers().clear();
            List<UserDto> membersViews = teamDto.getMembers();

            for (UserDto memberView : membersViews) {
                User user = userRepository
                        .findById(memberView.getId())
                        .orElseThrow(
                            () -> {
                                log.error("User with id " + memberView.getId() + "was not found");
                                return new UserNotFoundException("User with id " + memberView.getId() + "was not found");
                            });
                team.getMembers().add(user);
            }
        } else {
            teamDto.setMembers(new ArrayList<>());
        }

        log.info("Updated team with id "+ id);
        return TeamDto.fromTeam(team);

    }

    /**
     * removes the team record from the data source
     * @param id the ID of team that shall be deleted
     * @return a success message, if team record was removed
     * @throws {@code TeamNotFoundException} if a team with the provided ID doesn't exist
     * @since 1.0
     */
    @Override
    @Transactional
    public String deleteTeam(String id) {

        teamRepository.findById(id).orElseThrow(() ->{
            log.error("Team with id " + id + " was not found");
            return new TeamNotFoundException("Team with id " + id + " was not found");
        });

        teamRepository.deleteById(id);
        log.info("Team with id "+  id + " was deleted");
        return "Team with id " + id + " was deleted successfully";
    }

    /**
     * returns a list of {@code UserDto} objects, that belong to a team where the team leader has
     * the provided ID
     * @param id the team leader ID
     * @return a list of users that have a team leader with the provided ID
     * @since 1.0
     */
    @Override
    @Transactional
    public List<UserDto> getTeamMembers(String id) {
        List<Team> leaderTeams = new ArrayList<>();
        leaderTeams.addAll(teamRepository.findAllByTeamLeaderId(id));
        List<User> listOfMemmbers = new ArrayList<>();
        for (Team team : leaderTeams) {
            listOfMemmbers.addAll(team.getMembers());
        }
        List<UserDto> listToReturn = new ArrayList<>();
        for (User user : listOfMemmbers) {
            listToReturn.add(UserDto.fromUser(user));
        }
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            List<User> users = team.getMembers();
            for (User user : users) {
                List <User> auxList = users;
                boolean flag = false;
                if (user.getId().equals(id)) {
                    flag = true;
                }
                if (flag == true) {
                    for (User auxUser : auxList) {
                        if (!auxUser.getId().equals(id)) {
                            listToReturn.add(UserDto.fromUser(auxUser));
                        }
                    }
                }
            }
        }
        log.info("Getting all the team members of team with id "+ id);
        return listToReturn;
    }

    /**
     * returns a {@code UserDto} with the details of a team leader of the user with the provided ID
     * @param id the user ID
     * @return the details of the team leader, if the user has one
     * @since 1.0
     */
    @Override
    @Transactional
    public UserDto getTeamLeader(String id) {
        List<Team> allTeams = teamRepository.findAll();
        UserDto teamLeader = null;
        boolean flag = false;
        for (Team team : allTeams) {
            List<User> teamMembers = team.getMembers();
            for (User user : teamMembers) {
                if (user.getId().equals(id)) {
                    teamLeader = UserDto.fromUser(team.getTeamLeader());
                    flag = true;
                }
            }
        }
        log.info("Getting the team leader of the user "+ id);
        if (flag) {
            return teamLeader;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public CountDto countTeamLeaders() {
        List<Team> allTeams = teamRepository.findAll();
        Set<User> teamLeaders = new HashSet<>();
        for (Team team : allTeams) {
            teamLeaders.add(team.getTeamLeader());
        }
        log.info("Getting the total number of team leaders");
        return new CountDto((long) teamLeaders.size());
    }
}
