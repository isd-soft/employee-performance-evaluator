package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.StatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssessmentDto {
    private String title;
    private String description;
    private String jobPosition;
    private Float overallScore;
    private StatusEnum status;
    private Boolean isTemplate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String userId;
    //list of evaluation fields
    private List<PersonalGoalDto> personalGoalList;
    private List<DepartmentGoalDto> departmentGoalList;
    private List<FeedbackDto> feedback;

    public AssessmentDto(String title, String description, String jobPosition, Float overallScore, StatusEnum status, Boolean isTemplate, LocalDate startDate, LocalDate finishDate, String userId) {
        this.title = title;
        this.description = description;
        this.jobPosition = jobPosition;
        this.overallScore = overallScore;
        this.status = status;
        this.isTemplate = isTemplate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.userId = userId;
    }
}
