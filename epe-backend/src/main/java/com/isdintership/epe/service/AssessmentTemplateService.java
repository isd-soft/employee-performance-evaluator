package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentTemplateDto;

import java.util.List;

public interface AssessmentTemplateService {

    AssessmentTemplateDto createAssessmentTemplate(AssessmentTemplateDto assessmentTemplateDto);
    AssessmentTemplateDto getAssessmentTemplate(String id);
    List<AssessmentTemplateDto> getAllAssessmentTemplates();
    AssessmentTemplateDto updateAssessmentTemplate(String id, AssessmentTemplateDto assessmentTemplateDto);
    AssessmentTemplateDto deleteAssessmentTemplate(String id);

}
