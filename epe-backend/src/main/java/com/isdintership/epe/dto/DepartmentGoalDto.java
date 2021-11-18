package com.isdintership.epe.dto;

import com.isdintership.epe.entity.DepartmentGoal;
import lombok.Data;

@Data
public class DepartmentGoalDto {
    public String id;
    public String context;
    public String assessmentId;

    public static DepartmentGoalDto fromDepartmentGoal(DepartmentGoal goal) {

        DepartmentGoalDto goalDto = new DepartmentGoalDto();

        goalDto.setId(goal.getId());
        goalDto.setContext(goal.getContext());
        goalDto.setAssessmentId(goal.getAssessment().getId());

        return goalDto;

    }
}
