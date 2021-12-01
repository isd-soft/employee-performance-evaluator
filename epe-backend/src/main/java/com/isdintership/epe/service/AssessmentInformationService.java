package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentInformationDto;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The interface Assessment information service.
 *
 * @author Adrian Girlea
 * @since 25.11.2021
 */
public interface AssessmentInformationService {

    /**
     * Gets all the information about the assessment when its status changes.
     * <ul>
     *     <il>FIRST_PHASE</il>
     * </ul>
     * @author Adrian Gîrlea
     * @return the all assessment information
     */
    List<AssessmentInformationDto> getAllAssessmentInformation();
}
