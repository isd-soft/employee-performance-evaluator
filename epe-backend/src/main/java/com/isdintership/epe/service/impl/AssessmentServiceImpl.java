package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.exception.AssessmentNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;

    @Override
    @Transactional
    public SuccessResponse createAssessment(AssessmentTemplateDto assessmentTemplateDto) {
        assessmentRepository.save(assessmentTemplateDto.toTable());
        //convert AssesesmentDto to assessement and save to the repo
        return new SuccessResponse("Successful");
    }

    @Override
    @Transactional
    public SuccessResponse deleteAssessment(String id) {
        assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        assessmentRepository.removeAssessmentById(id);

        return new SuccessResponse("Assessment with id " + id + " was not deleted");
    }
}
