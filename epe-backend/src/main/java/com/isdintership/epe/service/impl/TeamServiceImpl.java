package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.Team;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.entity.exception.UserNotFoundException;
import com.isdintership.epe.entity.exception.UsersNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public SuccessResponse createTeam(String name, String teamLeaderId, List<String> membersIds) {
        User teamLeader = userRepository.findById(teamLeaderId).orElseThrow(
                () -> new UserNotFoundException("User with id " + teamLeaderId + "was not found"));

        if (membersIds.isEmpty()) {
            throw new UsersNotFoundException("Users with given id's were not found");
        }

        List<User> members = new ArrayList<>();
        for (String id : membersIds) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("User with id " + id + "was not found"));
            members.add(user);
        }

        Team team = new Team();
        team.setName(name);
        team.setTeamLeader(teamLeader);
        team.setMembers(members);

        return new SuccessResponse("Team " + name + " was created successfully");
    }

}
