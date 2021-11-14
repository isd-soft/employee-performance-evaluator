package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.AssessmentTemplate;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.exception.AssessmentNotFoundException;
import com.isdintership.epe.entity.exception.JobNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.JobRepository;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.StatusEnum;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.entity.exception.JobNotFoundException;
import com.isdintership.epe.entity.exception.UserNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.repository.UserRepository;

import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SuccessResponse createAssessment(AssessmentDto assessmentDto) {
        Assessment assessment = getAssessmentFromDtoAssessment(assessmentDto);
        assessmentRepository.save(assessment);
        return new SuccessResponse("Successful");
    }


    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessments() {
        //Assessments -> AssessmentsDto
        var assessments = assessmentRepository.findAll();
        return assessments.stream()
                .map(assessment -> new AssessmentDto(
                        assessment.getTitle(),
                        assessment.getDescription(),
                        assessment.getJob().getJobTitle(),
                        assessment.getOverallScore(),
                        assessment.getStatus(),
                        assessment.getIsTemplate(),
                        assessment.getStartDate(),
                        assessment.getEndDate(),
                        assessment.getUser().getId()
                ))
                .collect(Collectors.toList());

    }

    private Assessment getAssessmentFromDtoAssessment(AssessmentDto assessmentDto) {
        Assessment assessment = new Assessment();
        assessment.setTitle(assessmentDto.getTitle());
        assessment.setDescription(assessmentDto.getDescription());

        String jobTitle = assessmentDto.getJobPosition();
        Job job = jobRepository.findByJobTitle(jobTitle)
                .orElseThrow(() -> new JobNotFoundException("The " + jobTitle + " doesn't exist"));
        assessment.setJob(job);// find job

        assessment.setStatus(StatusEnum.FIRST_PHASE);
        assessment.setOverallScore(assessmentDto.getOverallScore());
        assessment.setIsTemplate(assessmentDto.getIsTemplate());
        assessment.setStartDate(assessmentDto.getStartDate());
        assessment.setEndDate(assessmentDto.getFinishDate());

        String userId = assessmentDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("The user doesn't not exist"));
        assessment.setUser(user);
        return assessment;
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
