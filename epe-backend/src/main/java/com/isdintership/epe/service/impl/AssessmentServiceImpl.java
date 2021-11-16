package com.isdintership.epe.service.impl;


import com.isdintership.epe.dao.EvaluationFieldService;
import com.isdintership.epe.dao.EvaluationGroupService;
import com.isdintership.epe.dto.EvaluationFieldDto;
import com.isdintership.epe.dto.EvaluationGroupDto;
import com.isdintership.epe.dto.SubordinatesDto;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.AssessmentNotFoundException;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.repository.*;
import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.AssessmentService;
import com.isdintership.epe.dao.AssessmentService;

import com.isdintership.epe.util.AssessmentUtil;
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
public class AssessmentServiceImpl implements AssessmentService, EvaluationGroupService, EvaluationFieldService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final EvaluationGroupRepository evaluationGroupRepository;
    private final EvaluationFieldRepository evaluationFieldRepository;

    @Override
    @Transactional
    public String createAssessment(AssessmentDto assessmentDto) {
        Assessment assessment = getAssessmentFromDtoAssessment(assessmentDto);
        assessmentRepository.save(assessment);
        return "Assessment created successfully";
    }

    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessments() {
        var assessments = assessmentRepository.findAll();
        return assessments.stream()
                .map(AssessmentUtil::toAssessmentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String deleteAssessment(String id) {
        assessmentRepository.findById(id)
                .orElseThrow(AssessmentNotFoundException::new);
        assessmentRepository.removeById(id);
        return ("Assessment with id " + id + " was deleted");
    }

    @Override
    @Transactional
    public String updateAssessment(AssessmentDto assessmentDto) {
        String assessmentId = assessmentDto.getId();
        assessmentRepository.findById(assessmentId)
                .ifPresentOrElse(
                        assessment -> assessmentRepository.removeById(assessmentId),
                        AssessmentNotFoundException::new);

        Assessment newAssessment = getAssessmentFromDtoAssessment(assessmentDto);
        assessmentRepository.save(newAssessment);
        return "Assessment successfully changed";
    }

    private Assessment getAssessmentFromDtoAssessment(AssessmentDto assessmentDto) {

        Assessment assessment = new Assessment();
        assessment.setTitle(assessmentDto.getTitle());
        assessment.setDescription(assessmentDto.getDescription());

        String jobTitle = assessmentDto.getJobPosition();
        Job job = jobRepository.findByJobTitle(jobTitle)
                .orElseThrow(JobNotFoundException::new);
        assessment.setJob(job);// find job

        System.out.println(assessmentDto.getEvaluationGroupDto());

        assessment.setStatus(StatusEnum.FIRST_PHASE);
        assessment.setOverallScore(assessmentDto.getOverallScore());
        assessment.setIsTemplate(assessmentDto.getIsTemplate());
        assessment.setStartDate(assessmentDto.getStartDate());
        assessment.setEndDate(assessmentDto.getFinishDate());
        String userId = assessmentDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        assessment.setUser(user);

        return assessment;
    }

    @Override
    @Transactional
    public AssessmentDto getAssessment(String userId) {
        var assessment = assessmentRepository.findById(userId)
                .orElseThrow(() -> new AssessmentNotFoundException("The assessment with this id was not found"));

        return AssessmentUtil.toAssessmentDto(assessment);
    }


    @Override
    @Transactional
    public List<EvaluationGroupDto> getEvaluationGroupsByAssessmentId(String id) {
        var evaluationGroup = evaluationGroupRepository.findEvaluationGroupByAssessmentId(id);
        if (evaluationGroup.isEmpty())
            throw new AssessmentNotFoundException("The Evaluation Group with this id was not found");

        List<EvaluationGroupDto> evaluationGroupDtos = new ArrayList<>();

        evaluationGroup.forEach(group -> {
            evaluationGroupDtos.add(EvaluationGroupDto.fromEvaluationGroup(group));
        });

        return evaluationGroupDtos;
    }

    @Override
    @Transactional
    public List<EvaluationFieldDto> getEvaluationFieldsByEvaluationGroupId(String id) {
        var evaluationFields = evaluationFieldRepository.findEvaluationFieldsByEvaluationGroupId(id);
        if (evaluationFields.isEmpty())
            throw new AssessmentNotFoundException("The Evaluation Group with this id was not found");

        List<EvaluationFieldDto> evaluationFieldDtos = new ArrayList<>();

        evaluationFields.forEach(field -> {
            evaluationFieldDtos.add(EvaluationFieldDto.fromEvaluationField(field));
        });

        return evaluationFieldDtos;
    }
}
