package com.isdintership.epe.dao;

import com.isdintership.epe.dto.EvaluationGroupDto;

import java.util.List;

public interface EvaluationGroupService {
    List<EvaluationGroupDto> getEvaluationGroupsByAssessmentId(String id);
}
