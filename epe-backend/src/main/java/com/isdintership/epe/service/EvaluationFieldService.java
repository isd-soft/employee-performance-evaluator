package com.isdintership.epe.dao;

import com.isdintership.epe.dto.EvaluationFieldDto;

import java.util.List;

public interface EvaluationFieldService {
    List<EvaluationFieldDto> getEvaluationFieldsByEvaluationGroupId(String id);
}
