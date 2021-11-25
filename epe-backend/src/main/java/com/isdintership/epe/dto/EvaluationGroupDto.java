package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isdintership.epe.entity.EvaluationField;
import com.isdintership.epe.entity.EvaluationGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EvaluationGroupDto {
    private String id;
    private Integer overallScore;
    private String title;
    private String assessmentId;
    @JsonProperty("evaluationFields")
    private List<EvaluationFieldDto> evaluationFields;


    public static EvaluationGroupDto fromEvaluationGroup(EvaluationGroup group){
        EvaluationGroupDto evaluationGroupDto = new EvaluationGroupDto();
        evaluationGroupDto.setId(group.getId());
        evaluationGroupDto.setOverallScore(group.getOverallScore());
        evaluationGroupDto.setTitle(group.getTitle());
        evaluationGroupDto.setAssessmentId(group.getAssessment().getId());
        List<EvaluationFieldDto> fieldsDto = new ArrayList<>();
        for (EvaluationField field : group.getEvaluationFields()){
            fieldsDto.add(EvaluationFieldDto.fromEvaluationField(field));
        }
        evaluationGroupDto.setEvaluationFields(fieldsDto);

        return evaluationGroupDto;
    }

}
