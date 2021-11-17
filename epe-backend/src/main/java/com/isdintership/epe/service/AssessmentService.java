package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentDto;

import java.util.List;

public interface AssessmentService {

    String deleteAssessment(String id);
    String updateAssessment(AssessmentDto assessmentDto);
    String createAssessment(AssessmentDto assessmentDto);
    List<AssessmentDto> getAllAssessments();
    AssessmentDto getAssessment(String userId);
}
