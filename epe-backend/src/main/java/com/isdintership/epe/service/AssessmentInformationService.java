package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentInformationDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AssessmentInformationService {
    List<AssessmentInformationDto> getAllAssessmentInformation();
}
