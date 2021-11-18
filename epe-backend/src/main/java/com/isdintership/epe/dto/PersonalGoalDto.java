package com.isdintership.epe.dto;

import com.isdintership.epe.entity.PersonalGoal;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalGoalDto {

    @NotNull
    private String id;
    private String context;
    private String assessmentId;

    public static PersonalGoalDto fromPersonalGoal(PersonalGoal goal) {

        PersonalGoalDto goalDto = new PersonalGoalDto();

        goalDto.setId(goal.getId());
        goalDto.setContext(goal.getContext());
        goalDto.setAssessmentId(goal.getAssessment().getId());

        return goalDto;

    }

}
