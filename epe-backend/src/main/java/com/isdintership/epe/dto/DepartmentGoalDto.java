package com.isdintership.epe.dto;

import com.isdintership.epe.entity.DepartmentGoal;
import lombok.Data;

@Data
public class DepartmentGoalDto {
    public String id;
    public String goalSPart;
    public String goalMPart;
    public String goalAPart;
    public String goalRPart;
    public String goalTPart;
    public String assessmentId;

    public static DepartmentGoalDto fromDepartmentGoal(DepartmentGoal goal) {

        DepartmentGoalDto goalDto = new DepartmentGoalDto();

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
