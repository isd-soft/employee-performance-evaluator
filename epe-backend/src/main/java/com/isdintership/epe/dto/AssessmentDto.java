package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentDto {
    private String id;
    private String evaluatedUserFullName;
    private String evaluatorFullName;
    private String title;
    private String description;
    private String jobTitle;
    private Float overallScore;
    private Status status;
    private Boolean isTemplate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private String userId;
    private String startedById;

    private List<EvaluationGroupDto> evaluationGroups;
    private List<PersonalGoalDto> personalGoals;
    private List<DepartmentGoalDto> departmentGoals;
    private List<FeedbackDto> feedbacks;

    public AssessmentDto() {
    }


    public static AssessmentDto fromAssessment(Assessment assessment) {

        AssessmentDto assessmentDto = new AssessmentDto();

        assessmentDto.setId(assessment.getId());
        assessmentDto.setTitle(assessment.getTitle());
        assessmentDto.setEvaluatedUserFullName(assessment.getEvaluatedUserFullName());
        assessmentDto.setEvaluatorFullName(assessment.getEvaluatorFullName());
        assessmentDto.setDescription(assessment.getDescription());
        assessmentDto.setJobTitle(assessment.getJob().getJobTitle());
        assessmentDto.setOverallScore(assessment.getOverallScore());
        assessmentDto.setStatus(assessment.getStatus());
        assessmentDto.setIsTemplate(assessment.getIsTemplate());
        assessmentDto.setStartDate(assessment.getStartDate());
        assessmentDto.setEndDate(assessment.getEndDate());
        assessmentDto.setUserId(assessment.getUser().getId());

        List<EvaluationGroupDto> evaluationGroupDtos = new ArrayList<>();
        assessment.getEvaluationGroups()
                  .forEach(group -> evaluationGroupDtos.add(EvaluationGroupDto.fromEvaluationGroup(group)));
        assessmentDto.setEvaluationGroups(evaluationGroupDtos);

        List<PersonalGoalDto> personalGoalDtos = new ArrayList<>();
        assessment.getPersonalGoals()
                .forEach(goal -> personalGoalDtos.add(PersonalGoalDto.fromPersonalGoal(goal)));
        assessmentDto.setPersonalGoals(personalGoalDtos);

        List<DepartmentGoalDto> departmentGoalDtos = new ArrayList<>();
        assessment.getDepartmentGoals()
                .forEach(goal -> departmentGoalDtos.add(DepartmentGoalDto.fromDepartmentGoal(goal)));
        assessmentDto.setDepartmentGoals(departmentGoalDtos);

        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        assessment.getFeedbacks()
                .forEach(feedback -> feedbackDtos.add(FeedbackDto.fromFeedback(feedback)));
        assessmentDto.setFeedbacks(feedbackDtos);

        return assessmentDto;
    }

    public boolean isFirstPhase() {
        return this.getStatus() == Status.FIRST_PHASE;
    }

    public boolean isSecondPhase() {
        return this.getStatus() == Status.SECOND_PHASE;
    }
}
