package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.util.AssessmentUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data

public class AssignedUserDto {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private List<AssessmentDto> personalGoalList;

    public static AssignedUserDto fromUserTo(User user, List<Assessment> assessments) {
        AssignedUserDto assignedUser = new AssignedUserDto();

        assignedUser.setId(user.getId());
        assignedUser.setEmail(user.getEmail());
        assignedUser.setFirstname(user.getFirstname());
        assignedUser.setLastname(user.getLastname());

        List<AssessmentDto> collect = assessments.stream()
                .map(AssessmentUtil::toAssessmentDto)
                .collect(Collectors.toList());

        assignedUser.setPersonalGoalList(collect);

        return assignedUser;
    }


}
