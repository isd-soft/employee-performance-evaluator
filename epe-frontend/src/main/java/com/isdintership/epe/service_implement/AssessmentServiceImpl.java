package com.isdintership.epe.service_implement;


import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.exception.AssessmentNotFoundException;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.entity.StatusEnum;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.dao.AssessmentService;

import com.isdintership.epe.util.AssessmentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public AssessmentDto getAssessment(String userId) {
        var assessment = assessmentRepository.findById(userId)
                .orElseThrow(() -> new AssessmentNotFoundException("The assessment with this id was not found"));
        return AssessmentUtil.toAssessmentDto(assessment);
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

}
