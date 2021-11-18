package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.AssessmentTemplateDto;

import java.util.List;

public interface AssessmentService {

    AssessmentDto startAssessment(String userId, AssessmentTemplateDto assessmentTemplateIdDto);
    AssessmentDto getAssessment(String id);
    List<AssessmentDto> getAllAssessmentsByUserId(String id);
    List<AssessmentDto> getAllAssessments();
//    AssessmentDto continueAssessment(String userId, AssessmentDto assessmentDto);
    AssessmentDto updateAssessment(String id, AssessmentDto assessmentDto);
    AssessmentDto deleteAssessment(String id);

}
