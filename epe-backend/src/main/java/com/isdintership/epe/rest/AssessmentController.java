package com.isdintership.epe.rest;

import com.isdintership.epe.dto.AssessmentTemplate;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_ADMIN;


@RestController
@RequestMapping("/api/assessment/")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createAssessment(@RequestBody AssessmentTemplate assessmentTemplate){
        return new ResponseEntity<>(assessmentService.createAssessment(assessmentTemplate), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public ResponseEntity<SuccessResponse> deleteAssessment(@PathVariable("id") String id) {
        return new ResponseEntity<>(assessmentService.deleteAssessment(id), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public ResponseEntity<AssessmentTemplate> updateAssessment(@Valid @RequestBody AssessmentTemplate assessmentTemplate,
                                                               @PathVariable("id") String id) {
        return new ResponseEntity<>(assessmentService.updateAssessment(assessmentTemplate, id), HttpStatus.OK);
    }

}
