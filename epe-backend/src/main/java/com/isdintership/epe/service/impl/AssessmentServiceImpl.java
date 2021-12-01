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
import java.util.*;


/**
 * Provides methods for working with the assessment entity.
 * @author Maxim Gribencicov, Adrian Girlea, Nicolai Morari
 * */
@Service
@Slf4j
@RequiredArgsConstructor
class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final EmailServiceImpl emailService;
    private final AssessmentInformationRepository assessmentInformationRepository;

    /**
     * Method that start an assessment on a user
     * @param userId the id for which the assessment starts
     * @param assessmentTemplateDto object that contains the assessment's template id
     * @throws AssessmentTemplateNotFoundException if assessmentTemlpate doesn't exist
     * @throws UserNotFoundException if user doesn't exist
     * @throws AssessmentExistsException if the user already has an assessment
     * @throws BadCredentialsException if the user's job doesn't correspond to the assessment's job position
     * @return an AssessmentDto
     * @author Maxim Gribencicov, Adrian Girlea
     * */
    @Override
    @Transactional
    public AssessmentDto startAssessment(String userId, AssessmentTemplateDto assessmentTemplateDto) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " was not found"));

        String assessmentTemplateId = assessmentTemplateDto.getId();
        Assessment assessmentTemplate = assessmentRepository.findByIdAndIsTemplate(assessmentTemplateId, true)
                .orElseThrow(() ->
                        new AssessmentTemplateNotFoundException("Assessment template with id " + assessmentTemplateId + " was not found"));

        List<Assessment> existingAssessment = assessmentRepository.findByUserAndStatusIn
                (user, List.of(Status.FIRST_PHASE, Status.SECOND_PHASE));
        if (!existingAssessment.isEmpty()) {
            throw new AssessmentExistsException
                    ("User already has unfinished assessment " + existingAssessment.get(0).getTitle());
        }
        if (user.getJob() != assessmentTemplate.getJob()) {
            throw new BadCredentialsException
                    ("Job position " + assessmentTemplate.getJob().getJobTitle() +
                            " from assessment template doesn't correspond to user job position " +
                            user.getJob().getJobTitle());
        }

        Assessment assessment = new Assessment();
        assessment.setUser(user);
        assessment.setEvaluatedUserFullName(user.getFirstname() + " " + user.getLastname());
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

        AssessmentInformation assessmentInformation = getAssessmentInformation(assessmentTemplateDto, assessment);
        assessmentInformationRepository.save(assessmentInformation);

        AssessmentDto assessmentDto = AssessmentDto.fromAssessment(assessment);
        emailService.sendEmail(assessmentDto);



        return assessmentDto;
    }


    /**
     * Helper method that creates an AssessmentInformation object
     * @param assessment entity object that includes most of the information required for creating a AssessmentInformation object
     * @param assessmentTemplateDto dto object includes the id the of person starting the assessment
     * @throws UserNotFoundException if the user doesn't exist
     * @return an AssessmentInformation entity object
     * @author Adrian Girlea
     * */
    private AssessmentInformation getAssessmentInformation(AssessmentTemplateDto assessmentTemplateDto, Assessment assessment) {
        AssessmentInformation assessmentInformation = new AssessmentInformation();
        assessmentInformation.setAssessmentTitle(assessment.getTitle());
        assessmentInformation.setStatus(assessment.getStatus());
        assessmentInformation.setPerformedOnUser(assessment.getUser());
        User creationUser = userRepository.findById(assessmentTemplateDto.getCreatedUserById())
                .orElseThrow(UserNotFoundException::new);
        assessmentInformation.setPerformedByUser(creationUser);
        assessmentInformation.setPerformedTime(assessment.getStartDate());
        return assessmentInformation;
    }

    /**
     * Method that returns the information about an assessment based on its id
     * @param id the id of the assessment entity object
     * @return an AssessmentDto object if such present
     * @throws AssessmentNotFoundException if assessment doesn't exist
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentDto getAssessment(String id) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        return AssessmentDto.fromAssessment(assessment);
    }

    /**
     * Method that returns all the assessments of a user if he exists
     * @param id the id the user
     * @throws UserNotFoundException if user doesn't exist
     * @return list of assessments if user present
     * @author Maxim Gribencicov
     * */
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

    /**
     * Method that gets all the assessments of a user based on the assessment's status:
     * ACTIVE (FIRST_PHASE and SECOND_PHASE) or INACTIVE (CANCELED and FINISHED)
     * @param id the id the user
     * @param status the status of the assessment
     * @throws AssessmentStatusNotFound if status doesn't exists
     * @author Maxim Gribencicov
     * @return List of AssessmentDto objects
     * */
    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessmentsByUserIdAndStatus(String id, String status) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));

        List<AssessmentDto> assessmentDtos = new ArrayList<>();

        if (status.equals("ACTIVE")) {
            assessmentRepository.findByUserAndStatusIn(user, List.of(Status.FIRST_PHASE, Status.SECOND_PHASE))
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));
            return assessmentDtos;
        } else if (status.equals("INACTIVE")) {
            assessmentRepository.findByUserAndStatusIn(user, List.of(Status.FINISHED, Status.CANCELED))
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));
            return assessmentDtos;
        }
        // else throw new AssessmentStatusNotFound("Assessment status " + status + " was not found");
        try {
            assessmentRepository.findByUserAndStatus(user, Status.valueOf(status))
                    .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));
        } catch (IllegalArgumentException e) {
            throw new AssessmentStatusNotFound("Assessment status " + status + " was not found");
        }

        return assessmentDtos;

    }

    /**
     * Method that returns all the assessments
     * @return List of assessments if such exists
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public List<AssessmentDto> getAllAssessments() {

        List<AssessmentDto> assessmentDtos = new ArrayList<>();
        assessmentRepository.findAllByIsTemplate(false)
                .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));

        return assessmentDtos;
    }

    /**
     * Method that returns all the assessments assigned to a user based on its status
     * @param userId the id the user
     * @param status the status of the assessment
     * @throws AssessmentStatusNotFound if status doesn't exist
     * @return List of AssessmentDto objects if such exists
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public List<AssessmentDto> getAllAssignedAssessmentsByStatus(String userId, String status) {

        List<Status> statuses;
        if (status == null) {
            statuses = List.of(Status.values());
        } else if (status.equals("ACTIVE")) {
            statuses = List.of(Status.FIRST_PHASE, Status.SECOND_PHASE);
        } else if (status.equals("INACTIVE")) {
            statuses = List.of(Status.CANCELED, Status.FINISHED);
        } else {
            throw new AssessmentStatusNotFound("Status not found. Should be ACTIVE or INACTIVE");
        }

        List<User> assignedUsers = userRepository.findByBuddyId(userId);
        Optional<Team> team = teamRepository.findByTeamLeaderId(userId);
        team.ifPresent(value -> assignedUsers.addAll(value.getMembers()));
        List<AssessmentDto> assessmentDtos = new ArrayList<>();
        assessmentRepository.findByUserInAndStatusIn(assignedUsers, statuses)
                .forEach(assessment -> assessmentDtos.add(AssessmentDto.fromAssessment(assessment)));
        return assessmentDtos;
    }


    /**
     * Method that evaluates the assessment from its current phase to an upper one
     * @param userId the id og the user evaluated
     * @param assessmentId the id of the assessment
     * @param assessmentDto dto object that includes all the information about the assessment
     * @throws UserNotFoundException if user doesn't exists
     * @throws AssessmentNotFoundException if assessment doesn't exist
     * @author Maxim Gribencicov, Adrian Girlea
     * */
    @Override
    @Transactional
    public AssessmentDto evaluateAssessment(String userId, String assessmentId, AssessmentDto assessmentDto) {
        System.out.println(assessmentDto);

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
            assessment.setEvaluatorFullName(assessmentDto.getEvaluatorFullName());
            calculateOverallScore(assessment);
            assessment.setEndDate(LocalDateTime.now());
            assessment.setStatus(Status.FINISHED);
        }

        AssessmentInformation assessmentInformation = getAssessmentInformation(assessment, assessmentDto);
        assessmentInformationRepository.save(assessmentInformation);

        emailService.sendEmail(AssessmentDto.fromAssessment(assessment));
        return AssessmentDto.fromAssessment(assessment);
    }

    /**
     * Method that returns an AssessmentInformation entity for a operation performed on an assessment
     * @param assessmentDto includes: id the user who performed the operation and cancellationReason if applicable
     * @param assessment includes information about the assessment
     * @return an AssessmentInformation entity
     * @author Adrian Girlea
     * */
    private AssessmentInformation getAssessmentInformation(Assessment assessment, AssessmentDto assessmentDto) {
        AssessmentInformation assessmentInformation = new AssessmentInformation();
        assessmentInformation.setReason(assessmentDto.getCancellationReason());
        assessmentInformation.setAssessmentTitle(assessment.getTitle());
        assessmentInformation.setStatus(assessment.getStatus());
        User user = userRepository.findById(assessmentDto.getStartedById()).orElseThrow(UserNotFoundException::new);
        assessmentInformation.setPerformedByUser(user);
        assessmentInformation.setPerformedOnUser(assessment.getUser());
        assessmentInformation.setPerformedTime(LocalDateTime.now());
        return assessmentInformation;
    }

    /**
     * Calculates the overall score of an assessment
     * @param assessment the assessment for which the action takes place
     * @author Maxim Gribencicov
     * */
    private void calculateOverallScore(Assessment assessment) {

        List<EvaluationGroup> evaluationGroups = assessment.getEvaluationGroups();
        float groupsSum = 0;
        for (EvaluationGroup evaluationGroup : evaluationGroups) {
            calculateOverallScoreGroup(evaluationGroup);
            groupsSum += evaluationGroup.getOverallScore();
        }
        assessment.setOverallScore(getAvg(groupsSum, evaluationGroups.size()));
    }

    /**
     * Calculates the overall score of an evaluation group inside an assessment
     * @param evaluationGroup the evaluation group for which the action takes place
     * @author Maxim Gribencicov
     * */
    private void calculateOverallScoreGroup(EvaluationGroup evaluationGroup) {

        List<EvaluationField> evaluationFields = evaluationGroup.getEvaluationFields();
        int fieldsSum = 0;
        for (EvaluationField field : evaluationFields) {
            fieldsSum += field.getSecondScore();
        }
        evaluationGroup.setOverallScore(getAvg(fieldsSum, evaluationFields.size()));
    }

    /**
     *  Calculates the average score of an assessment
     * @param sum the sum of the all scores from the evaluation groups
     * @param count the number of evaluation groups inside an assessment
     * @return the overall score
     * @author Maxim Gribencicov
     * */
    private float getAvg(float sum, float count) {
        return (sum / count);
    }

    /**
     * Saving evaluationGroup information from dto objet to entity object
     * @throws InvalidAssessmentDataException if the number of evaluation fields or evaluation groups of the dto
     * doesn't correspond to the entity object
     * @author Maxim Gribencicov
     * */
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
                evaluationFields.get(j).setSecondScore(evaluationFieldDtos.get(j).getSecondScore());
            }
        }
    }

    /**
     * Saving data about feedback from dto objet to entity object
     * @author Maxim Gribencicov
     * */
    private void processFeedback(String userId, AssessmentDto assessmentDto, User user, Assessment assessment, List<Feedback> feedbacks) {

        if (isFeedbackPresent(assessmentDto)) {
            Feedback feedback = getFeedback(userId, assessmentDto, user, feedbacks);
            feedback.setAssessment(assessment);
            feedbacks.add(feedback);
        }

    }

    /**
     * Checks if there is any feedback in the assessment
     * @author Maxim Gribencicov
     * */
    private boolean isFeedbackPresent(AssessmentDto assessmentDto) {
        return assessmentDto.getFeedbacks().size() != 0;
    }

    /**
     * Returns the information about the feedback
     * @param userId the user's id
     * @param assessmentDto the assessment the feedback is part of
     * @param feedbacks list of feedbacks of the user
     * @param user user entity object
     * @return Feedback entity
     * @author Maxim Gribencicov
     * */
    private Feedback getFeedback(String userId, AssessmentDto assessmentDto, User user, List<Feedback> feedbacks) {
        Feedback feedback = new Feedback();
        feedback.setAuthorId(userId);
        feedback.setAuthorFullName(user.getFirstname() + " " + user.getLastname());
        feedback.setContext(assessmentDto.getFeedbacks().get(0).getContext());
        feedbacks.add(feedback);
        return feedback;
    }

    /**
     * Deletes previous personal goals if present and saves the new ones
     * @param assessmentDto AssessmentDto object, includes information about personal goals
     * @param assessment the assessment for which the operation takes place
     * @param personalGoals list of personal goals
     * @author Maxim Gribencicov
     * */
    private void processPersonalGoals(AssessmentDto assessmentDto, Assessment assessment, List<PersonalGoal> personalGoals) {
        personalGoals.clear();
        assessmentDto.getPersonalGoals().forEach(personalGoalDto -> getPersonalGoal(personalGoals, personalGoalDto).setAssessment(assessment));
    }

    /**
     * Method that returns the personal goals
     * @param personalGoals a list of personalGoal entity objects
     * @param personalGoalDto Dto object that contains the information about the personal goals
     * @return a personalGoal Entity
     * @author Maxim Gribencicov
     * */
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

    /**
     * Deletes previous department goals if present and saves the new ones.
     * @param assessmentDto AssessmentDto object, includes information about the department goals
     * @param assessment the assessment for which the operation takes place
     * @param departmentGoals list of department goals
     * @author Maxim Gribencicov
     * */
    private void processDepartmentGoals(AssessmentDto assessmentDto, Assessment assessment, List<DepartmentGoal> departmentGoals) {
        departmentGoals.clear();
        assessmentDto.getDepartmentGoals().forEach(departmentGoalDto -> getDepartmentGoal(departmentGoals, departmentGoalDto).setAssessment(assessment));
    }

    /**
     * Method that returns the department goals
     * @param departmentGoals a list of departmentGoals entity objects
     * @param departmentGoalDto Dto object that contains the information about the department goals
     * @author Maxim Gribencicov
     * */
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

    /**
     * Updates the assessment
     * @param id the id of the assessment
     * @param assessmentDto includes information about the assessment
     * @throws AssessmentNotFoundException if assessment doesn't exist
     * @throws JobNotFoundException if job doesn't exist
     * @throws UserNotFoundException if user doesn't exist
     * @author Maxim Gribencicov, Adrian Girlea, Nicolae Morari
     * */
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
//        assessment.setCancellationReason(assessmentDto.getCancellationReason());
        if (assessmentDto.getStatus().equals(Status.CANCELED)){
            AssessmentInformation assessmentInformation = getAssessmentInformation(assessment, assessmentDto);
            assessmentInformationRepository.save(assessmentInformation);
        }
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
                feedback.setAuthorId(feedbackDto.getAuthorId());
                feedback.setAuthorFullName(feedbackDto.getAuthorFullName());
                feedback.setContext(feedbackDto.getContext());
                assessment.getFeedbacks().add(feedback);
            }
        }
        return AssessmentDto.fromAssessment(assessment);
    }

    /**
     * Deletes an assessment and save the action in AssessmentInformation
     * @param id the of the assessment
     * @param assessmentDto contains the id of the user performing the action
     * @author Maxim Gribencicov, Adrian Girlea
     * @return the deleted assessment object
     * @throws AssessmentNotFoundException if assessment doesn't exists
     * @throws UserNotFoundException if user doesn't exists
     * */
    @Override
    @Transactional
    public AssessmentDto deleteAssessment(String id, AssessmentDto assessmentDto) {

        Assessment assessment = assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with id " + id + " was not found"));

        AssessmentInformation assessmentInformation = new AssessmentInformation();
        assessmentInformation.setAssessmentTitle(assessment.getTitle());
        assessmentInformation.setStatus(Status.DELETED);
        assessmentInformation.setPerformedTime(LocalDateTime.now());
        User startedByUser = userRepository.findById(assessmentDto.getStartedById()).orElseThrow(UserNotFoundException::new);
        assessmentInformation.setPerformedByUser(startedByUser);
        assessmentInformation.setPerformedOnUser(assessment.getUser());
        assessmentInformationRepository.save(assessmentInformation);

        assessmentRepository.removeById(id);

        return AssessmentDto.fromAssessment(assessment);
    }

    /**
     * Counts all the assessments that started in the current year
     * @return a list of amount of assessment that started each month of this year
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public NewAssessmentsThisYearDto countAllNewAssessmentsThisYear() {
        NewAssessmentsThisYearDto newAssessments = new NewAssessmentsThisYearDto();
        for (int i = 0; i < 12; i++) {
            LocalDateTime fromDate = LocalDateTime.of(LocalDateTime.now().getYear(), i + 1, 1, 0, 0);
            newAssessments.getMonths().add(i, assessmentRepository.countAllByStartDateBetween(fromDate, fromDate.plusMonths(1)));
        }

        return newAssessments;
    }

    /**
     * @return the number of all assessmetDtos
     * @author Maxim Gribencicov
     * */
    @Override
    @Transactional
    public AssessmentDto countAll() {
        AssessmentDto assessmentDto = new AssessmentDto();
        assessmentDto.setCount(assessmentRepository.count());
        return assessmentDto;
    }

}
