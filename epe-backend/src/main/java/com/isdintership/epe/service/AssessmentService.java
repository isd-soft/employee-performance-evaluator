package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.SuccessResponse;

public interface AssessmentService {
    SuccessResponse createAssessment(AssessmentTemplateDto assessmentTemplateDto);

    SuccessResponse deleteAssessment(String id);
}
