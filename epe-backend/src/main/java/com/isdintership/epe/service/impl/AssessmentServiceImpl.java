package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    AssessmentRepository repository;

    @Override
    @Transactional
    public SuccessResponse createAssessment(AssessmentTemplateDto assessmentTemplateDto) {
        repository.save(assessmentTemplateDto.toTable());
        //convert AssesesmentDto to assessement and save to the repo
        return new SuccessResponse("Successful");
    }
}
