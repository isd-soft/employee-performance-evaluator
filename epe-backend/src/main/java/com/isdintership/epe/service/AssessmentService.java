package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentTemplate;
import com.isdintership.epe.dto.SuccessResponse;

public interface AssessmentService {
    SuccessResponse createAssessment(AssessmentTemplate assessmentTemplate);

    SuccessResponse deleteAssessment(String id);

    AssessmentTemplate updateAssessment(AssessmentTemplate assessmentTemplate, String id);
}
