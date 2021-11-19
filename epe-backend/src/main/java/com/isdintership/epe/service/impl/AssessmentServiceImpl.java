package com.isdintership.epe.service.impl;
import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.*;
import com.isdintership.epe.repository.*;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.AssessmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AssessmentDto startAssessment(String userId, AssessmentTemplateDto assessmentTemplateDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " was not found"));

        String assessmentTemplateId = assessmentTemplateDto.getId();

        Assessment assessmentTemplate = assessmentRepository.findByIdAndIsTemplate(assessmentTemplateId, true)
                .orElseThrow(() ->
                new AssessmentTemplateNotFoundException
                        ("Assessment template with id " + assessmentTemplateId + " was not found"));

        Optional<Assessment> existingAssessment = assessmentRepository.findByTitleAndUser
                (assessmentTemplate.getTitle(), user);

        if ((existingAssessment.isPresent()) && (existingAssessment.get().getStatus() != StatusEnum.FINISHED)) {
            throw new AssessmentExistsException
                    ("Assessment " + assessmentTemplate.getTitle() + " already assigned to "
                     + "user " + user.getFirstname() + " " + user.getLastname() + " and is still unfinished");
        }

        if (user.getJob() != assessmentTemplate.getJob()) {
            throw new BadCredentialsException
                    ("Job position " + assessmentTemplate.getJob().getJobTitle() +
                     " from assessment template doesn't correspond to user job position " +
                     user.getJob().getJobTitle());
        }

        Assessment assessment = new Assessment();

        assessment.setUser(user);

        assessment.setTitle(assessmentTemplate.getTitle());
        assessment.setDescription(assessmentTemplate.getDescription());
        assessment.setJob(assessmentTemplate.getJob());
        assessment.setStatus(StatusEnum.FIRST_PHASE);
        assessment.setIsTemplate(false);
        assessment.setStartDate(LocalDateTime.now());

        List<EvaluationGroup> evaluationGroups = new ArrayList<>();

        for (EvaluationGroup templateGroup  : assessmentTemplate.getEvaluationGroups()) {

            EvaluationGroup group = new EvaluationGroup();
            group.setAssessment(assessment);
            group.setTitle(templateGroup.getTitle());

            List<EvaluationField> evaluationFields = new ArrayList<>();

            for (EvaluationField templateField : templateGroup.getEvaluationFields()) {

                EvaluationField field = new EvaluationField();

                field.setEvaluationGroup(group);
                field.setTitle(templateField.getTitle());
                field.setComment(templateField.getComment());

                evaluationFields.add(field);

            }
            group.setEvaluationFields(evaluationFields);

            evaluationGroups.add(group);
        }

        assessment.setEvaluationGroups(evaluationGroups);

        assessmentRepository.save(assessment);

        return AssessmentDto.fromAssessment(assessment);
    }

    @Override
    @Transactional
    public AssessmentDto getAssessment(String id) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        return AssessmentDto.fromAssessment(assessment);
    }

    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessmentsByUserId(String id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        List<AssessmentDto> assessmentDtos = new ArrayList<>();
        assessmentRepository.findByUser(user)
                            .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

        return assessmentDtos;
    }

    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessmentsByUserIdAndStatus(String id, String status) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        List<AssessmentDto> assessmentDtos = new ArrayList<>();

        if (status.equals("active")) {

            assessmentRepository.findByUserAndStatusIsNot(user, StatusEnum.FINISHED)
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

            return assessmentDtos;
        }


        try {

            StatusEnum assessmentStatus = StatusEnum.valueOf(status);
            assessmentRepository.findByUserAndStatus(user, assessmentStatus)
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

        } catch (IllegalArgumentException e) {

            throw new AssessmentStatusNotFound("Assessment status " + status + " was not found");

        }

        return assessmentDtos;

    }

    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessments() {

        List<AssessmentDto> assessmentDtos = new ArrayList<>();
        assessmentRepository.findAllByIsTemplate(false)
                            .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

        return assessmentDtos;
    }

    /*@Override
    @Transactional
    public AssessmentDto continueAssessment(String userId, AssessmentDto assessmentDto) {

        return null;

    }*/

    @Override
    @Transactional
    public AssessmentDto updateAssessment(String id, AssessmentDto assessmentDto) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        Job job = jobRepository.findByJobTitle(assessmentDto.getJobPosition()).orElseThrow(() ->
                new JobNotFoundException("Job with id " + assessmentDto.getJobPosition() + " was not found"));

        User user = userRepository.findById(assessmentDto.getUserId()).orElseThrow(() ->
                new UserNotFoundException("User with id " + assessmentDto.getUserId() + " was not found"));

        if (!assessmentDto.getTitle().equals(assessment.getTitle())) {

            Optional<Assessment> existingAssessment =
                    assessmentRepository.findByTitleAndUser(assessment.getTitle(), user);

            if ((existingAssessment.isPresent()) && (existingAssessment.get().getStatus() != StatusEnum.FINISHED)) {
                throw new AssessmentExistsException
                        ("User " + user.getFirstname() + " " + user.getLastname() + " already has unfinished assessment "
                         + "with title " + assessmentDto.getTitle());
            }
        }

        assessment.setTitle(assessmentDto.getTitle());
        assessment.setDescription(assessmentDto.getDescription());
        assessment.setJob(job);
        assessment.setOverallScore(assessmentDto.getOverallScore());
        assessment.setStatus(assessmentDto.getStatus());
        assessment.setIsTemplate(assessmentDto.getIsTemplate());
        assessment.setStartDate(assessmentDto.getStartDate());
        assessment.setEndDate(assessmentDto.getEndDate());
        assessment.setUser(user);

        assessment.getEvaluationGroups().clear();

        if (assessmentDto.getEvaluationGroupDtos() != null) {
            for (EvaluationGroupDto evaluationGroupDto  : assessmentDto.getEvaluationGroupDtos()) {

                EvaluationGroup group = new EvaluationGroup();
                group.setAssessment(assessment);
                group.setTitle(evaluationGroupDto.getTitle());
                group.setOverallScore(evaluationGroupDto.getOverallScore());

                for (EvaluationFieldDto evaluationFieldDto : evaluationGroupDto.getEvaluationFieldDtos()) {

                    EvaluationField field = new EvaluationField();

                    field.setEvaluationGroup(group);
                    field.setTitle(evaluationFieldDto.getTitle());
                    field.setComment(evaluationFieldDto.getComment());
                    field.setFirstScore(evaluationFieldDto.getFirstScore());
                    field.setSecondScore(evaluationFieldDto.getSecondScore());
                    field.setOverallScore(evaluationFieldDto.getOverallScore());

                    group.getEvaluationFields().add(field);
                }

                assessment.getEvaluationGroups().add(group);
            }
        }


        assessment.getPersonalGoals().clear();

        if (assessmentDto.getPersonalGoalDtos() != null) {
            for (PersonalGoalDto personalGoalDto : assessmentDto.getPersonalGoalDtos()) {

                PersonalGoal personalGoal = new PersonalGoal();

                personalGoal.setAssessment(assessment);
                personalGoal.setContext(personalGoalDto.getContext());

                assessment.getPersonalGoals().add(personalGoal);

            }
        }

        assessment.getDepartmentGoals().clear();

        if (assessmentDto.getDepartmentGoalDtos() != null) {
            for (DepartmentGoalDto departmentGoalDto : assessmentDto.getDepartmentGoalDtos()) {

                DepartmentGoal departmentGoal = new DepartmentGoal();

                departmentGoal.setAssessment(assessment);
                departmentGoal.setContext(departmentGoalDto.getContext());

                assessment.getDepartmentGoals().add(departmentGoal);

            }
        }

        assessment.getFeedbacks().clear();

        if (assessmentDto.getFeedbackDtos() != null) {

            for(FeedbackDto feedbackDto : assessmentDto.getFeedbackDtos()) {

                Feedback feedback = new Feedback();

                feedback.setAssessment(assessment);

                userRepository.findById(feedbackDto.getAuthorId()).orElseThrow(() ->
                        new UserNotFoundException("Comment author with id " + feedbackDto.getAuthorId() + " was not found"));

                feedback.setAuthorId(feedbackDto.getAuthorId());
                feedback.setContext(feedbackDto.getContext());

                assessment.getFeedbacks().add(feedback);

            }

        }



        return AssessmentDto.fromAssessment(assessment);

    }

    @Override
    @Transactional
    public AssessmentDto deleteAssessment(String id) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        assessmentRepository.removeById(id);

        return AssessmentDto.fromAssessment(assessment);
    }

}
