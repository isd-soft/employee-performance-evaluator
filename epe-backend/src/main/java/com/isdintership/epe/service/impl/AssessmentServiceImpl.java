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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;

    @Override
    @Transactional
    public AssessmentDto startAssessment(String userId, AssessmentTemplateDto assessmentTemplateDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " was not found"));

        String assessmentTemplateId = assessmentTemplateDto.getId();

        Assessment assessmentTemplate = assessmentRepository.findByIdAndIsTemplate(assessmentTemplateId, true)
                .orElseThrow(() ->
                        new AssessmentTemplateNotFoundException("Assessment template with id " + assessmentTemplateId + " was not found"));

        Optional<Assessment> existingAssessment = assessmentRepository.findByUserAndStatusIn
                (user, List.of(Status.FIRST_PHASE, Status.SECOND_PHASE));

        if (existingAssessment.isPresent()) {
            throw new AssessmentExistsException
                    ("User already has unfinished assessment " + existingAssessment.get().getTitle());
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
        assessment.setStatus(Status.FIRST_PHASE);
        assessment.setIsTemplate(false);
        assessment.setStartDate(LocalDateTime.now());

        List<EvaluationGroup> evaluationGroups = new ArrayList<>();

        for (EvaluationGroup templateGroup : assessmentTemplate.getEvaluationGroups()) {

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

//        emailService.sendEmail(user, assessment.getTitle(), assessment.getDescription());

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
    public List<AssessmentDto> getAllAssessmentsByUserIdAndStatus(String id, Status status) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        List<AssessmentDto> assessmentDtos = new ArrayList<>();

        if (status != Status.FINISHED) {

            assessmentRepository.findByUserAndStatusIsNot(user, Status.FINISHED)
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

            return assessmentDtos;
        }


        try {

            assessmentRepository.findByUserAndStatus(user, status)
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

    @Override
    @Transactional
    public AssessmentDto evaluateAssessment(String userId, String assessmentId, AssessmentDto assessmentDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " was not found"));
        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + assessmentId + " was not found"));

        processEvaluationGroups(assessment, assessmentDto);
        processPersonalGoals(assessmentDto, assessment, assessment.getPersonalGoals());
        processDepartmentGoals(assessmentDto, assessment, assessment.getDepartmentGoals());
        processFeedback(userId, assessmentDto, user, assessment, assessment.getFeedbacks());

        if (assessmentDto.isFirstPhase()) {
            assessment.setStatus(Status.SECOND_PHASE);
        }
        if (assessmentDto.isSecondPhase()) {
            calculateOverallScore(assessment);
            assessment.setStatus(Status.FINISHED);
        }

        return AssessmentDto.fromAssessment(assessment);
    }

    private void calculateOverallScore(Assessment assessment) {

        int groupsSum = 0;
        for (EvaluationGroup evaluationGroup : assessment.getEvaluationGroups()) {
            calculateOverallScoreGroup(evaluationGroup);
            groupsSum += evaluationGroup.getOverallScore();
        }
        assessment.setOverallScore(getAvgInteger(groupsSum, assessment.getEvaluationGroups().size()));
    }

    private void calculateOverallScoreGroup(EvaluationGroup evaluationGroup) {

        int fieldsSum = 0;
        for (EvaluationField evaluationField : evaluationGroup.getEvaluationFields()) {
            evaluationField.setOverallScore
                (getOverallScoreField(evaluationField.getFirstScore(), evaluationField.getSecondScore()));
            fieldsSum += evaluationField.getOverallScore();
        }
        evaluationGroup.setOverallScore(
                getAvgInteger(fieldsSum, evaluationGroup.getEvaluationFields().size()));
    }

    private int getAvgInteger(double sum, int count) {
        return (int) Math.round(sum / count);
    }

    private int getOverallScoreField(int firstValue, int secondValue) {
        return getAvgInteger(firstValue + secondValue, 2);
    }

    private void processEvaluationGroups(Assessment assessment, AssessmentDto assessmentDto){
        List<EvaluationGroupDto> evaluationGroupDtos = assessmentDto.getEvaluationGroups();
        List<EvaluationGroup> evaluationGroups = assessment.getEvaluationGroups();

        if (evaluationGroupDtos.size() != evaluationGroups.size()) {
            throw new InvalidAssessmentDataException("Invalid quantity of evaluation groups");
        }

        for (int i = 0; i < evaluationGroups.size(); i++) {

            List<EvaluationFieldDto> evaluationFieldDtos = evaluationGroupDtos.get(i).getEvaluationFields();
            List<EvaluationField> evaluationFields = evaluationGroups.get(i).getEvaluationFields();

            if (evaluationFieldDtos.size() != evaluationFields.size()) {
                throw new InvalidAssessmentDataException("Invalid quantity of evaluation fields in group "
                        + evaluationGroupDtos.get(i).getTitle());
            }
            for (int j = 0; j < evaluationFields.size(); j++) {
                evaluationFields.get(j).setFirstScore(evaluationFieldDtos.get(j).getFirstScore());
            }
        }
    }

    private void processFeedback(String userId, AssessmentDto assessmentDto, User user, Assessment assessment, List<Feedback> feedbacks) {
        feedbacks.clear();

        if (!assessmentDto.getFeedbacks().isEmpty()) {
            Feedback feedback = getFeedback(userId, assessmentDto, user, feedbacks);
            feedback.setAssessment(assessment);
        }
    }

    private Feedback getFeedback(String userId, AssessmentDto assessmentDto, User user, List<Feedback> feedbacks) {
        Feedback feedback = new Feedback();
        feedback.setAuthorId(userId);
        feedback.setAuthorFullName(user.getFirstname() + " " + user.getLastname());
        feedback.setContext(assessmentDto.getFeedbacks().get(0).getContext());
        feedbacks.add(feedback);
        return feedback;
    }

    private void processPersonalGoals(AssessmentDto assessmentDto, Assessment assessment, List<PersonalGoal> personalGoals) {
        personalGoals.clear();
        assessmentDto.getPersonalGoals().forEach(personalGoalDto -> getPersonalGoal(personalGoals, personalGoalDto).setAssessment(assessment));
    }

    private PersonalGoal getPersonalGoal(List<PersonalGoal> personalGoals, PersonalGoalDto personalGoalDto) {
        PersonalGoal personalGoal = new PersonalGoal();
        personalGoal.setGoalSPart(personalGoalDto.getGoalSPart());
        personalGoal.setGoalMPart(personalGoalDto.getGoalMPart());
        personalGoal.setGoalAPart(personalGoalDto.getGoalAPart());
        personalGoal.setGoalRPart(personalGoalDto.getGoalRPart());
        personalGoal.setGoalTPart(personalGoalDto.getGoalTPart());
        personalGoals.add(personalGoal);
        return personalGoal;
    }

    private void processDepartmentGoals(AssessmentDto assessmentDto, Assessment assessment, List<DepartmentGoal> departmentGoals) {
        departmentGoals.clear();
        assessmentDto.getDepartmentGoals().forEach(departmentGoalDto -> getDepartmentGoal(departmentGoals, departmentGoalDto).setAssessment(assessment));
    }

    private DepartmentGoal getDepartmentGoal(List<DepartmentGoal> departmentGoals, DepartmentGoalDto departmentGoalDto) {
        DepartmentGoal departmentGoal = new DepartmentGoal();
        departmentGoal.setGoalSPart(departmentGoalDto.getGoalSPart());
        departmentGoal.setGoalMPart(departmentGoalDto.getGoalMPart());
        departmentGoal.setGoalAPart(departmentGoalDto.getGoalAPart());
        departmentGoal.setGoalRPart(departmentGoalDto.getGoalRPart());
        departmentGoal.setGoalTPart(departmentGoalDto.getGoalTPart());
        departmentGoals.add(departmentGoal);
        return departmentGoal;
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

        Job job = jobRepository.findByJobTitle(assessmentDto.getJobTitle()).orElseThrow(() ->
                new JobNotFoundException("Job with id " + assessmentDto.getJobTitle() + " was not found"));

        User user = userRepository.findById(assessmentDto.getUserId()).orElseThrow(() ->
                new UserNotFoundException("User with id " + assessmentDto.getUserId() + " was not found"));

        if (!assessmentDto.getTitle().equals(assessment.getTitle())) {

            Optional<Assessment> existingAssessment =
                    assessmentRepository.findByTitleAndUser(assessment.getTitle(), user);

            if ((existingAssessment.isPresent()) && (existingAssessment.get().getStatus() != Status.FINISHED)) {
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

        if (assessmentDto.getEvaluationGroups() != null) {
            for (EvaluationGroupDto evaluationGroupDto : assessmentDto.getEvaluationGroups()) {

                EvaluationGroup group = new EvaluationGroup();
                group.setAssessment(assessment);
                group.setTitle(evaluationGroupDto.getTitle());
                group.setOverallScore(evaluationGroupDto.getOverallScore());

                for (EvaluationFieldDto evaluationFieldDto : evaluationGroupDto.getEvaluationFields()) {

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

        if (assessmentDto.getPersonalGoals() != null) {
            for (PersonalGoalDto personalGoalDto : assessmentDto.getPersonalGoals()) {

                PersonalGoal personalGoal = new PersonalGoal();

                personalGoal.setAssessment(assessment);

                personalGoal.setGoalSPart(personalGoalDto.getGoalSPart());
                personalGoal.setGoalMPart(personalGoalDto.getGoalMPart());
                personalGoal.setGoalAPart(personalGoalDto.getGoalAPart());
                personalGoal.setGoalRPart(personalGoalDto.getGoalRPart());
                personalGoal.setGoalTPart(personalGoalDto.getGoalTPart());

                assessment.getPersonalGoals().add(personalGoal);

            }
        }

        assessment.getDepartmentGoals().clear();

        if (assessmentDto.getDepartmentGoals() != null) {
            for (DepartmentGoalDto departmentGoalDto : assessmentDto.getDepartmentGoals()) {

                DepartmentGoal departmentGoal = new DepartmentGoal();

                departmentGoal.setAssessment(assessment);

                departmentGoal.setGoalSPart(departmentGoalDto.getGoalSPart());
                departmentGoal.setGoalMPart(departmentGoalDto.getGoalMPart());
                departmentGoal.setGoalAPart(departmentGoalDto.getGoalAPart());
                departmentGoal.setGoalRPart(departmentGoalDto.getGoalRPart());
                departmentGoal.setGoalTPart(departmentGoalDto.getGoalTPart());

                assessment.getDepartmentGoals().add(departmentGoal);

            }
        }

        assessment.getFeedbacks().clear();

        if (assessmentDto.getFeedbacks() != null) {

            for (FeedbackDto feedbackDto : assessmentDto.getFeedbacks()) {

                Feedback feedback = new Feedback();

                feedback.setAssessment(assessment);

                feedback.setAuthorFullName(feedbackDto.getAuthorFullName());
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
