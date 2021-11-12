package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentTemplate;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.exception.AssessmentNotFoundException;
import com.isdintership.epe.entity.exception.JobNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;

    @Override
    @Transactional
    public SuccessResponse createAssessment(AssessmentTemplate assessmentTemplate) {
        assessmentRepository.save(assessmentTemplate.toTable());
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

    @Override
    @Transactional
    public AssessmentTemplate updateAssessment(AssessmentTemplate assessmentTemplate, String id) {
        Assessment assessment =  assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        assessment.setTitle(assessmentTemplate.getTitle());
        assessment.setDescription(assessmentTemplate.getDescription());
        Job job = jobRepository.findByJobTitle(assessmentTemplate.getJobPosition().getJobTitle()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + assessmentTemplate.getJobPosition() + " not found"));;
        assessment.setJob(job);

        return assessmentTemplate;
    }
}
