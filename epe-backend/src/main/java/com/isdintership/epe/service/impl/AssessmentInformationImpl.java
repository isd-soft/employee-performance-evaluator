package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentInformationDto;
import com.isdintership.epe.entity.AssessmentInformation;
import com.isdintership.epe.repository.AssessmentInformationRepository;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.service.AssessmentInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
 class AssessmentInformationImpl implements AssessmentInformationService {

    private final AssessmentInformationRepository assessmentInformationRepository;
    private final AssessmentRepository assessmentRepository;

    @Override
    @Transactional
    public List<AssessmentInformationDto> getAllAssessmentInformation() {

        List<AssessmentInformationDto> assessmentInformationDtos = new ArrayList<>();
        for (AssessmentInformation assessmentInformation : assessmentInformationRepository.findAll()) {
            AssessmentInformationDto assessmentInformationDto = new AssessmentInformationDto(assessmentInformation);
            assessmentRepository.findById(assessmentInformation.getAssessmentId())
                    .ifPresent(assessment -> {
                        assessment.getFeedbacks()
                                .forEach(feedback -> assessmentInformationDto.getFeedbackAuthorsIds().add(feedback.getAuthorId()));
                        assessmentInformationDto.setCurrentStatus(assessment.getStatus());
                    });
            assessmentInformationDtos.add(assessmentInformationDto);
        }


        return assessmentInformationDtos.stream()
                                        .sorted(Comparator
                                                .comparing(AssessmentInformationDto::getPerformedTime)
                                                .reversed())
                                        .collect(Collectors.toList());
    }
}
