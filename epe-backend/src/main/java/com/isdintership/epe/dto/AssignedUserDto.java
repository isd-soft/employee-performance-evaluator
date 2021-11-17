package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Team;
import com.isdintership.epe.entity.User;
import lombok.Data;


@Data

public class AssignedUserDto {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String job;
    private String team;

    public static AssignedUserDto fromUser(User user) {
        AssignedUserDto assignedUser = new AssignedUserDto();

        assignedUser.setId(user.getId());
        assignedUser.setEmail(user.getEmail());
        assignedUser.setFirstname(user.getFirstname());
        assignedUser.setLastname(user.getLastname());
        assignedUser.setJob(user.getJob().getJobTitle());

        Team assignedUserTeam = user.getTeam();
        if (assignedUserTeam != null) {
            assignedUser.setTeam(assignedUserTeam.getName());
        }

        return assignedUser;
    }


}
