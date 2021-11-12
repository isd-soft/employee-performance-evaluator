package com.isdintership.epe.rest;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_ADMIN;


@RestController
@RequestMapping("/api/assessment/")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createAssessment(@RequestBody AssessmentTemplateDto assessmentTemplateDto){
        System.out.println("afaw");
        return new ResponseEntity<>(assessmentService.createAssessment(assessmentTemplateDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @RolesAllowed({ROLE_ADMIN})
    public ResponseEntity<SuccessResponse> deleteAssessment(@PathVariable("id") String id) {
        return new ResponseEntity<>(assessmentService.deleteAssessment(id), HttpStatus.OK);
    }

}
