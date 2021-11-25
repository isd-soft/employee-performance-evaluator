package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private String userId;
    private String startedById;

    private List<EvaluationGroupDto> evaluationGroupDtos;
    private List<PersonalGoalDto> personalGoalDtos;
    private List<DepartmentGoalDto> departmentGoalDtos;
    private List<FeedbackDto> feedbackDtos;

    public AssessmentDto() {
    }


    public static AssessmentDto fromAssessment(Assessment assessment) {

        AssessmentDto assessmentDto = new AssessmentDto();

        assessmentDto.setId(assessment.getId());
        assessmentDto.setTitle(assessment.getTitle());
        assessmentDto.setDescription(assessment.getDescription());
        assessmentDto.setJobPosition(assessment.getJob().getJobTitle());
        assessmentDto.setOverallScore(assessment.getOverallScore());
        assessmentDto.setStatus(assessment.getStatus());
        assessmentDto.setIsTemplate(assessment.getIsTemplate());
        assessmentDto.setStartDate(assessment.getStartDate());
        assessmentDto.setEndDate(assessment.getEndDate());
        assessmentDto.setUserId(assessment.getUser().getId());

        List<EvaluationGroupDto> evaluationGroupDtos = new ArrayList<>();
        assessment.getEvaluationGroups()
                  .forEach(group -> evaluationGroupDtos.add(EvaluationGroupDto.fromEvaluationGroup(group)));
        assessmentDto.setEvaluationGroupDtos(evaluationGroupDtos);

        List<PersonalGoalDto> personalGoalDtos = new ArrayList<>();
        assessment.getPersonalGoals()
                .forEach(goal -> personalGoalDtos.add(PersonalGoalDto.fromPersonalGoal(goal)));
        assessmentDto.setPersonalGoalDtos(personalGoalDtos);

        List<DepartmentGoalDto> departmentGoalDtos = new ArrayList<>();
        assessment.getDepartmentGoals()
                .forEach(goal -> departmentGoalDtos.add(DepartmentGoalDto.fromDepartmentGoal(goal)));
        assessmentDto.setDepartmentGoalDtos(departmentGoalDtos);

        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        assessment.getFeedbacks()
                .forEach(feedback -> feedbackDtos.add(FeedbackDto.fromFeedback(feedback)));
        assessmentDto.setFeedbackDtos(feedbackDtos);

        return assessmentDto;
    }
}
