package com.isdintership.epe.service;

import com.isdintership.epe.dto.*;

import java.util.List;

public interface AssessmentService {

    AssessmentDto startAssessment(String userId, AssessmentTemplateDto assessmentTemplateIdDto);
    AssessmentDto getAssessment(String id);
    List<AssessmentDto> getAllAssessmentsByUserId(String id);
    List<AssessmentDto> getAllAssessmentsByUserIdAndStatus(String id, String status);
    List<AssessmentDto> getAllAssessments();
    List<AssessmentDto> getAllAssignedAssessmentsByStatus(String userId, String status);
    FeedbackDto addFeedback(String userId, String assessmentId, FeedbackDto feedbackDto);
    AssessmentDto evaluateAssessment(String userId, String assessmentId, AssessmentDto assessmentDto);
    AssessmentDto updateAssessment(String id, AssessmentDto assessmentDto);
    AssessmentDto deleteAssessment(String id);
    NewAssessmentsThisYearDto countAllNewAssessmentsThisYear();
    CountDto countAll();
}
