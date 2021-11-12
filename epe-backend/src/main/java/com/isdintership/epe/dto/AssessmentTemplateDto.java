package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.PersonalGoal;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssessmentTemplateDto {
    private String title;
    private String description;
    private String jobPosition;
    private Float overallScore;
    private boolean isTemplate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private List<PersonalGoalDto> personalGoalList;
    private List<DepartmentGoalDto> departmentGoalList;
    private List<FeedbackDto> feedback;


    public Assessment toTable(){
        Assessment assessment = new Assessment();
        assessment.setTitle(title);
        assessment.setDescription(description);
        assessment.setJob(new Job(jobPosition));
        assessment.setOverallScore(overallScore);
        assessment.setIsTemplate(isTemplate);
        assessment.setStartDate(startDate);
        assessment.setEndDate(finishDate);
        return assessment;
    }
}
