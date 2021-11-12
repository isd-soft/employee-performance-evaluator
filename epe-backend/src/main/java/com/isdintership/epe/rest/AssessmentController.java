package com.isdintership.epe.rest;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.service.impl.AssessmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/api/assessment")
@RequiredArgsConstructor
public class AssessmentController {

    AssessmentServiceImpl assessmentService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createAssessment(@RequestBody AssessmentTemplateDto assessmentTemplateDto){
        System.out.println("afaw");
        return new ResponseEntity<>(assessmentService.createAssessment(assessmentTemplateDto), HttpStatus.OK);
    }

}
