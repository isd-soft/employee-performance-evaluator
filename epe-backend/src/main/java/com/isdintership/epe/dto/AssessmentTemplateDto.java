package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentTemplateDto {

    private String id;
    private String title;
    private String description;
    private String jobTitle;
    private StatusEnum status;
    private Boolean isTemplate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    @JsonProperty("evaluationGroups")
    private List<EvaluationGroupDto> evaluationGroupDto;

    public AssessmentTemplateDto() {
    }

    public AssessmentTemplateDto(String id, String title, String description, String jobTitle,
                                 StatusEnum status, Boolean isTemplate, LocalDateTime creationDate,
                                 List<EvaluationGroupDto> evaluationGroupDto) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.jobTitle = jobTitle;
        this.status = status;
        this.isTemplate = isTemplate;
        this.creationDate = creationDate;
        this.evaluationGroupDto = evaluationGroupDto;
    }

    public static AssessmentTemplateDto fromAssessment(Assessment assessment) {

        AssessmentTemplateDto assessmentTemplateDto = new AssessmentTemplateDto();

        assessmentTemplateDto.setId(assessment.getId());
        assessmentTemplateDto.setTitle(assessment.getTitle());
        assessmentTemplateDto.setDescription(assessment.getDescription());
        assessmentTemplateDto.setJobTitle(assessment.getJob().getJobTitle());
        assessmentTemplateDto.setStatus(assessment.getStatus());
        assessmentTemplateDto.setIsTemplate(assessment.getIsTemplate());
        assessmentTemplateDto.setCreationDate(assessment.getCreationDate());

        List<EvaluationGroupDto> evaluationGroupDtos = new ArrayList<>();
        assessment.getEvaluationGroups()
                  .forEach(evaluationGroup ->
                      evaluationGroupDtos.add(EvaluationGroupDto.fromEvaluationGroup(evaluationGroup)));

        assessmentTemplateDto.setEvaluationGroupDto(evaluationGroupDtos);

        return assessmentTemplateDto;

    }

}
