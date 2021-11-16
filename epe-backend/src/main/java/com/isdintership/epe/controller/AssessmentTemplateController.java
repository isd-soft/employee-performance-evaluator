package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessments-templates")
@RequiredArgsConstructor
public class AssessmentTemplateController {

    public ResponseEntity<String> createAssessmentTemplate(
            @RequestBody AssessmentTemplateDto assessmentTemplateDto) {

        return null;
    }

}
