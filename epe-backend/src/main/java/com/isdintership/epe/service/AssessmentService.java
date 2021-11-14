package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentTemplate;
import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.SuccessResponse;

import java.util.List;

public interface AssessmentService {

    SuccessResponse deleteAssessment(String id);

    AssessmentTemplate updateAssessment(AssessmentTemplate assessmentTemplate, String id);
    SuccessResponse createAssessment(AssessmentDto assessmentDto);
    List<AssessmentDto> getAllAssessments();
}
