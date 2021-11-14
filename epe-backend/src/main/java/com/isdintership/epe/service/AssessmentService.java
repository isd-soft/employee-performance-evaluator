package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.SuccessResponse;

import java.util.List;

public interface AssessmentService {
    SuccessResponse createAssessment(AssessmentDto assessmentDto);
    List<AssessmentDto> getAllAssessments();
}
