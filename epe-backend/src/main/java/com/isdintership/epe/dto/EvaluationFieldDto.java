package com.isdintership.epe.dto;

import com.isdintership.epe.entity.EvaluationField;
import lombok.Data;

@Data
public class EvaluationFieldDto {
    private String id;
    private String title;
    private String comment;
    private Integer firstScore;
    private Integer secondScore;
    private String evaluationGroupId;

    public static EvaluationFieldDto fromEvaluationField(EvaluationField field){

        EvaluationFieldDto fieldDto = new EvaluationFieldDto();
        fieldDto.setId(field.getId());
        fieldDto.setTitle(field.getTitle());
        fieldDto.setComment(field.getComment());
        fieldDto.setFirstScore(field.getFirstScore());
        fieldDto.setSecondScore(field.getSecondScore());
        fieldDto.setEvaluationGroupId(field.getEvaluationGroup().getId());

        return fieldDto;
    }
}


