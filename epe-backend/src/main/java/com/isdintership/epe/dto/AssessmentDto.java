package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.EvaluationGroup;
import com.isdintership.epe.entity.StatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentDto {
    private String id;
    private String title;
    private String description;
    private String jobPosition;
    private Float overallScore;
    private StatusEnum status;
    private Boolean isTemplate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String userId;
    private List<EvaluationGroupDto> evaluationGroupDto ;

    //list of evaluation fields
    private List<PersonalGoalDto> personalGoalList;
    private List<DepartmentGoalDto> departmentGoalList;
    private List<FeedbackDto> feedback;

    public AssessmentDto() {
    }

    public AssessmentDto(String id, String title, String description, String jobPosition,
                         Float overallScore, StatusEnum status, Boolean isTemplate,
                         LocalDate startDate, LocalDate finishDate, String userId, List<EvaluationGroup> evaluationGroup) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.jobPosition = jobPosition;
        this.overallScore = overallScore;
        this.status = status;
        this.isTemplate = isTemplate;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.userId = userId;

        this.evaluationGroupDto = new ArrayList<>();
        for(EvaluationGroup group : evaluationGroup) {
            this.evaluationGroupDto.add(EvaluationGroupDto.fromEvaluationGroup(group));
        }
    }
}
