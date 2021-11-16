package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.StatusEnum;
import lombok.Data;

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
    private List<EvaluationGroupDto> evaluationGroupDto;

    public AssessmentTemplateDto() {
    }

    public AssessmentTemplateDto(String id, String title, String description, String jobTitle,
                                 StatusEnum status, Boolean isTemplate,
                                 List<EvaluationGroupDto> evaluationGroupDto) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.jobTitle = jobTitle;
        this.status = status;
        this.isTemplate = isTemplate;
        this.evaluationGroupDto = evaluationGroupDto;
    }

}
