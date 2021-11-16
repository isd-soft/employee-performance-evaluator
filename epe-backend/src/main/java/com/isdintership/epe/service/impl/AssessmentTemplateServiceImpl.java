package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.service.AssessmentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssessmentTemplateServiceImpl implements AssessmentTemplateService {

    private final JobRepository jobRepository;

    @Override
    @Transactional
    public AssessmentTemplateDto createAssessmentTemplate(AssessmentTemplateDto assessmentTemplateDto) {

        Job job = jobRepository.findByJobTitle(assessmentTemplateDto.getJobTitle()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + assessmentTemplateDto.getJobTitle() + " not found"));

        Assessment assessment = new Assessment();

        assessment.setTitle(assessmentTemplateDto.getTitle());
        assessment.setDescription(assessmentTemplateDto.getDescription());

        return null;
    }

}
