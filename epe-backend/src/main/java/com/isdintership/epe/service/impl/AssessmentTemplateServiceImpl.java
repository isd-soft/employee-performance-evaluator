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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class manages all the API requests related to AssessmentTemplates objects
 * */
@Service
@RequiredArgsConstructor
class AssessmentTemplateServiceImpl implements AssessmentTemplateService {
    private static final Logger log
            = LoggerFactory.getLogger(AssessmentTemplateServiceImpl.class);
    private final JobRepository jobRepository;
    private final AssessmentRepository assessmentRepository;

    /**
     * Creates an AssessmentTemplate entity object and saves it
     * @param assessmentTemplateDto contains all the information required to create an AssessmentTemplate
     * @return an AssessmentTemplateDto object
     * @throws JobNotFoundException if the job position in assessment template doesn't exist
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentTemplateDto createAssessmentTemplate(AssessmentTemplateDto assessmentTemplateDto) {

        Job job = jobRepository.findByJobTitle(assessmentTemplateDto.getJobTitle())
                .orElseThrow(() -> {
                    log.error("Job with name " + assessmentTemplateDto.getJobTitle() + " not found");
                    return new JobNotFoundException("Job with name " + assessmentTemplateDto.getJobTitle() + " not found");
                });

        Assessment assessmentTemplate = new Assessment();

        assessmentTemplate.setTitle(assessmentTemplateDto.getTitle());
        assessmentTemplate.setDescription(assessmentTemplateDto.getDescription());
        assessmentTemplate.setJob(job);
        assessmentTemplate.setStatus(Status.FIRST_PHASE);
        assessmentTemplate.setIsTemplate(true);
        assessmentTemplate.setCreationDate(LocalDateTime.now());

        List<EvaluationGroup> evaluationGroups = new ArrayList<>();

        for (EvaluationGroupDto groupDto : assessmentTemplateDto.getEvaluationGroupDto()) {

            EvaluationGroup group = new EvaluationGroup();
            group.setAssessment(assessmentTemplate);
            group.setTitle(groupDto.getTitle());

            List<EvaluationField> evaluationFields = new ArrayList<>();

            for (EvaluationFieldDto fieldDto : groupDto.getEvaluationFields()) {

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
        log.info("Successfully saved the assessment with id "+ assessmentTemplate.getId());
        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);
    }

    /**
     * Returns an AssessmentTemplateDto object based on the id
     * @param id the id of the AssessmentTemplate
     * @throws AssessmentTemplateNotFoundException if the assessment template doesn't exist
     * @return an AssessmentTemplateDto object
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentTemplateDto getAssessmentTemplate(String id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Assessment template with id " + id + " was not found");
                    return new AssessmentTemplateNotFoundException("Assessment template with id " + id + " was not found");
                });
        log.info("Returning assessment with id "+ id);
        return AssessmentTemplateDto.fromAssessment(assessment);
    }

    /**
     * Returns all the existing assessments templates dto object
     * @return A list of AssessmentTemplateDto
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public List<AssessmentTemplateDto> getAllAssessmentTemplates() {

        List<Assessment> assessmentTemplates = assessmentRepository.findByIsTemplate(true);
        List<AssessmentTemplateDto> assessmentTemplateDtos = new ArrayList<>();

        assessmentTemplates.forEach(assessmentTemplate ->
                assessmentTemplateDtos.add(AssessmentTemplateDto.fromAssessment(assessmentTemplate)));
        log.info("Returning all assessment templates");
        return assessmentTemplateDtos;
    }

    /**
     * Update an already existing AssessmentTemplate entity object
     * @param id the id of the Assessment Template entity object
     * @param assessmentTemplateDto Dto object that contains all the information that needs updated
     * @return updated AssesmentTemplatedDto object
     * @throws AssessmentTemplateNotFoundException if the assessmentTemplate doesn't exist
     * @throws AssessmentTemplateExistsException if an assessmentTemplate with given parameters already exists
     * @throws JobNotFoundException if the job doesn't exist
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentTemplateDto updateAssessmentTemplate
            (String id, AssessmentTemplateDto assessmentTemplateDto) {

        Assessment assessmentTemplate = assessmentRepository
                .findByIdAndIsTemplate(id, true)
                .orElseThrow(() -> {
                    log.error("Assessment template with id " + id + " was not found");
                    return new AssessmentTemplateNotFoundException
                            ("Assessment template with id " + id + " was not found");
                });

        if (!assessmentTemplateDto.getTitle().equals(assessmentTemplate.getTitle())) {

            Optional<Assessment> existingAssessment =
                    assessmentRepository.findByTitleAndIsTemplate(assessmentTemplateDto.getTitle(), true);
            if (existingAssessment.isPresent()) {
                log.error("Assessment template with name " + assessmentTemplateDto.getTitle() + " already exists");
                throw new AssessmentTemplateExistsException
                        ("Assessment template with name " + assessmentTemplateDto.getTitle() + " already exists");
            }
        }


        Job job = jobRepository
                .findByJobTitle(assessmentTemplateDto.getJobTitle())
                .orElseThrow(() -> {
                    log.error("Job with name " + assessmentTemplateDto.getJobTitle() + " was not found");
                    return new JobNotFoundException("Job with name " + assessmentTemplateDto.getJobTitle() + " was not found");
                });


        assessmentTemplate.setTitle(assessmentTemplateDto.getTitle());
        assessmentTemplate.setDescription(assessmentTemplateDto.getDescription());
        assessmentTemplate.setJob(job);

        assessmentTemplate.getEvaluationGroups().clear();

        for (EvaluationGroupDto groupDto : assessmentTemplateDto.getEvaluationGroupDto()) {

            EvaluationGroup group = new EvaluationGroup();
            group.setAssessment(assessmentTemplate);
            group.setTitle(groupDto.getTitle());

            group.getEvaluationFields().clear();

            for (EvaluationFieldDto fieldDto : groupDto.getEvaluationFields()) {

                EvaluationField field = new EvaluationField();

                field.setEvaluationGroup(group);
                field.setTitle(fieldDto.getTitle());
                field.setComment(fieldDto.getComment());

                group.getEvaluationFields().add(field);

            }

            assessmentTemplate.getEvaluationGroups().add(group);

        }
        log.info("Updated assessment template with id "+ assessmentTemplate.getId());
        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);

    }

    /**
     * Deletes an AssessmentTemplate entity object
     * @param id the id of the AssessmentTemplate entity object
     * @return the AssessmentTemplateDto of the deleted AssessmentTemplate
     * @throws AssessmentTemplateNotFoundException if AssessmentTemplate entity does not exist
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentTemplateDto deleteAssessmentTemplate(String id) {

        Assessment assessmentTemplate = assessmentRepository
                .findByIdAndIsTemplate(id, true)
                .orElseThrow(() -> {
                    log.error("Assessment template with id " + id + " was not found");
                    return new AssessmentTemplateNotFoundException("Assessment template with id " + id + " was not found");
                });

        assessmentRepository.removeById(id);
        log.info("Deleted assessment template with id "+ id);
        return AssessmentTemplateDto.fromAssessment(assessmentTemplate);
    }

}
