package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.entity.StatusEnum;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.isdintership.epe.dao.EvaluationFieldService;
import com.isdintership.epe.dao.EvaluationGroupService;
import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.service.AssessmentService;
import com.isdintership.epe.dto.EvaluationFieldDto;
import com.isdintership.epe.dto.EvaluationGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping("users/{id}/assessments")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> startAssessment
            (@PathVariable(name = "id") String userId,
             @RequestBody AssessmentTemplateDto assessmentTemplateDto) {

        return new ResponseEntity<>(assessmentService.startAssessment(userId, assessmentTemplateDto), HttpStatus.CREATED);

    }

    @GetMapping("users/{id}/assessments")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<AssessmentDto>> getAllAssessmentsByUserId
            (@PathVariable(name = "id") String userId,
             @RequestParam(name = "status", required = false) String status) {

        if (status != null) {

            return new ResponseEntity<>(assessmentService.getAllAssessmentsByUserIdAndStatus(userId, status),
                                        HttpStatus.OK);

        }

        return new ResponseEntity<>(assessmentService.getAllAssessmentsByUserId(userId), HttpStatus.OK);

    }

    @GetMapping("assessments")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<AssessmentDto>> getAllAssessments() {

        return new ResponseEntity<>(assessmentService.getAllAssessments(), HttpStatus.OK);

    }

    @GetMapping("assessments/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> getAssessment(@PathVariable(name = "id") String id) {

        return new ResponseEntity<>(assessmentService.getAssessment(id), HttpStatus.OK);

    }


    @PutMapping("assessments/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> updateAssessment (@PathVariable(name = "id") String id,
                                                           @RequestBody AssessmentDto assessmentDto) {

        return new ResponseEntity<>(assessmentService.updateAssessment(id, assessmentDto), HttpStatus.OK);

    }

    @DeleteMapping("assessments/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> deleteAssessment (@PathVariable(name = "id") String id) {

        return new ResponseEntity<>(assessmentService.deleteAssessment(id), HttpStatus.OK);

    }

}
