package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.EvaluationFieldDto;
import com.isdintership.epe.dto.EvaluationGroupDto;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.AssessmentTemplateExistsException;
import com.isdintership.epe.exception.AssessmentTemplateNotFoundException;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.service.AssessmentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class AssessmentTemplateServiceImpl implements AssessmentTemplateService {

    private final JobRepository jobRepository;
    private final AssessmentRepository assessmentRepository;

    @Override
    @Transactional
    public AssessmentTemplateDto createAssessmentTemplate(AssessmentTemplateDto assessmentTemplateDto) {

        Optional<Assessment> existingAssessment =
                assessmentRepository.findByTitleAndIsTemplate(assessmentTemplateDto.getTitle(), true);
        if (existingAssessment.isPresent()) {
            throw new AssessmentTemplateExistsException
                    ("Assessment template with name " + assessmentTemplateDto.getTitle() + " already exists");
        }

        Job job = jobRepository.findByJobTitle(assessmentTemplateDto.getJobTitle()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + assessmentTemplateDto.getJobTitle() + " not found"));

        Assessment assessmentTemplate = new Assessment();

        assessmentTemplate.setTitle(assessmentTemplateDto.getTitle());
        assessmentTemplate.setDescription(assessmentTemplateDto.getDescription());
        assessmentTemplate.setJob(job);
        assessmentTemplate.setStatus(StatusEnum.FIRST_PHASE);
        assessmentTemplate.setIsTemplate(true);

        List<EvaluationGroup> evaluationGroups = new ArrayList<>();

        for (EvaluationGroupDto groupDto : assessmentTemplateDto.getEvaluationGroupDto()) {

            EvaluationGroup group = new EvaluationGroup();
            group.setAssessment(assessmentTemplate);
            group.setTitle(groupDto.getTitle());

            List<EvaluationField> evaluationFields = new ArrayList<>();

            for (EvaluationFieldDto fieldDto : groupDto.getEvaluationFieldDtos()) {

                EvaluationField field = new EvaluationField();

                field.setEvaluationGroup(group);
                field.setTitle(fieldDto.getTitle());
                field.setComment(fieldDto.getComment());

                evaluationFields.add(field);

            }

            group.setEvaluationFields(evaluationFields);

            evaluationGroups.add(group);

        }
        assessmentTemplate.setEvaluationGroups(evaluationGroups);

        assessmentRepository.save(assessmentTemplate);

        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);
    }

    @Override
    @Transactional
    public AssessmentTemplateDto getAssessmentTemplate(String id) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentTemplateNotFoundException("Assessment template with id " + id + " was not found"));

        return AssessmentTemplateDto.fromAssessment(assessment);
    }

    @Override
    @Transactional
    public List<AssessmentTemplateDto> getAllAssessmentTemplates() {

        List<Assessment> assessmentTemplates = assessmentRepository.findAllByIsTemplate(true);
        List<AssessmentTemplateDto> assessmentTemplateDtos = new ArrayList<>();

        assessmentTemplates.forEach(assessmentTemplate ->
                assessmentTemplateDtos.add(AssessmentTemplateDto.fromAssessment(assessmentTemplate)));

        return assessmentTemplateDtos;
    }

    @Override
    @Transactional
    public AssessmentTemplateDto updateAssessmentTemplate
            (String id, AssessmentTemplateDto assessmentTemplateDto) {

        Assessment assessmentTemplate = assessmentRepository.findByIdAndIsTemplate(id, true)
                .orElseThrow(() -> new AssessmentTemplateNotFoundException
                        ("Assessment template with id " + id + " was not found"));

        if (!assessmentTemplateDto.getTitle().equals(assessmentTemplate.getTitle())) {

            Optional<Assessment> existingAssessment =
                    assessmentRepository.findByTitleAndIsTemplate(assessmentTemplateDto.getTitle(), true);
            if (existingAssessment.isPresent()) {
                throw new AssessmentTemplateExistsException
                        ("Assessment template with name " + assessmentTemplateDto.getTitle() + " already exists");
            }
        }


        Job job = jobRepository.findByJobTitle(assessmentTemplateDto.getJobTitle()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + assessmentTemplateDto.getJobTitle() + " was not found"));


        assessmentTemplate.setTitle(assessmentTemplateDto.getTitle());
        assessmentTemplate.setDescription(assessmentTemplateDto.getDescription());
        assessmentTemplate.setJob(job);

        assessmentTemplate.getEvaluationGroups().clear();

        for (EvaluationGroupDto groupDto : assessmentTemplateDto.getEvaluationGroupDto()) {

            EvaluationGroup group = new EvaluationGroup();
            group.setAssessment(assessmentTemplate);
            group.setTitle(groupDto.getTitle());

            group.getEvaluationFields().clear();

            for (EvaluationFieldDto fieldDto : groupDto.getEvaluationFieldDtos()) {

                EvaluationField field = new EvaluationField();

                field.setEvaluationGroup(group);
                field.setTitle(fieldDto.getTitle());
                field.setComment(fieldDto.getComment());

                group.getEvaluationFields().add(field);

            }

            assessmentTemplate.getEvaluationGroups().add(group);

        }

        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);

    }

    @Override
    @Transactional
    public AssessmentTemplateDto deleteAssessmentTemplate(String id) {

        Assessment assessmentTemplate = assessmentRepository.findByIdAndIsTemplate(id, true).orElseThrow(() ->
                new AssessmentTemplateNotFoundException("Assessment template with id " + id + " was not found"));

        assessmentRepository.removeById(id);

        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);
    }

}
