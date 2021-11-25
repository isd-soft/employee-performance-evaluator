package com.isdintership.epe.dto;

import com.isdintership.epe.entity.PersonalGoal;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalGoalDto {

    @NotNull
    private String id;
    private String goalSPart;
    private String goalMPart;
    private String goalAPart;
    private String goalRPart;
    private String goalTPart;
    private String assessmentId;

    public static PersonalGoalDto fromPersonalGoal(PersonalGoal goal) {

        PersonalGoalDto goalDto = new PersonalGoalDto();

        goalDto.setId(goal.getId());
        goalDto.setGoalSPart(goal.getGoalSPart());
        goalDto.setGoalMPart(goal.getGoalMPart());
        goalDto.setGoalAPart(goal.getGoalAPart());
        goalDto.setGoalRPart(goal.getGoalRPart());
        goalDto.setGoalTPart(goal.getGoalTPart());
        goalDto.setAssessmentId(goal.getAssessment().getId());

        return goalDto;

    }

}
