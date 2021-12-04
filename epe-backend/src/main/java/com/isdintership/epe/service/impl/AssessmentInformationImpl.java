package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentInformationDto;
import com.isdintership.epe.entity.AssessmentInformation;
import com.isdintership.epe.repository.AssessmentInformationRepository;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.security.JwtUserDetailsService;
import com.isdintership.epe.service.AssessmentInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the AssessmentInformationService interface.
 * Service class that provides methods to work with the Assessment entity
 * @author Adrian Girlea
 * */
@Service
@RequiredArgsConstructor
@Slf4j
 class AssessmentInformationImpl implements AssessmentInformationService {

    private final AssessmentInformationRepository assessmentInformationRepository;
    private final AssessmentRepository assessmentRepository;
    /**
     * Method that gets all the information about all the actions performed on the assessments
     * @author Adrian Girlea
     * @return List of Assessment Information
     * */
    @Override
    @Transactional
    public List<AssessmentInformationDto> getAllAssessmentInformation() {

        List<AssessmentInformationDto> assessmentInformationDtos = new ArrayList<>();
        for (AssessmentInformation assessmentInformation : assessmentInformationRepository.findAll()) {
            AssessmentInformationDto assessmentInformationDto = new AssessmentInformationDto(assessmentInformation);
            if (assessmentInformation.getAssessmentId() != null) {
                assessmentRepository.findById(assessmentInformation.getAssessmentId())
                        .ifPresent(assessment -> {
                            assessment.getFeedbacks()
                                    .forEach(feedback -> assessmentInformationDto.getFeedbackAuthorsIds().add(feedback.getAuthorId()));
                            assessmentInformationDto.setCurrentStatus(assessment.getStatus());
                        });
            }
            assessmentInformationDtos.add(assessmentInformationDto);
        }

        log.info("Getting all the assessment information");
        return assessmentInformationDtos.stream()
                                        .sorted(Comparator
                                                .comparing(AssessmentInformationDto::getPerformedTime)
                                                .reversed())
                                        .collect(Collectors.toList());
    }
}
