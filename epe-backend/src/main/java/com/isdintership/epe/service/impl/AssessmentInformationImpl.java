package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentInformationDto;
import com.isdintership.epe.repository.AssessmentInformationRepository;
import com.isdintership.epe.service.AssessmentInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
 class AssessmentInformationImpl implements AssessmentInformationService {

    private final AssessmentInformationRepository assessmentInformationRepository;

    @Override
    public List<AssessmentInformationDto> getAllAssessmentInformation() {
        return assessmentInformationRepository.findAll().stream()
                                                .map(AssessmentInformationDto::new)
                                                .collect(Collectors.toList());
    }
}
