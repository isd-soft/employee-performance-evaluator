package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.dto.*;
import com.isdintership.epe.service.AssessmentInformationService;
import com.isdintership.epe.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import static com.isdintership.epe.entity.RoleEnum.Fields.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentInformationService assessmentInformationService;

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
            return new ResponseEntity<>(assessmentService.getAllAssessmentsByUserIdAndStatus(userId, status), HttpStatus.OK);
        }

        return new ResponseEntity<>(assessmentService.getAllAssessmentsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("assessments")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<?> getAllAssessments
            (@RequestParam(name = "count", required = false) String count) {
        if (count != null) {
            if (count.equals("all")) {
                return ResponseEntity.ok(assessmentService.countAll());
            }
            if (count.equals("current-year")) {
                return ResponseEntity.ok(assessmentService.countAllNewAssessmentsThisYear());
            }
        }
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }

    @GetMapping("/users/{id}/assigned-assessments")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<AssessmentDto>> getAllAssignedAssessments
            (@PathVariable(name = "id") String id,
             @RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(assessmentService.getAllAssignedAssessmentsByStatus(id, status));
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

    @PutMapping("users/{userId}/assessments/{assessmentId}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> evaluateAssessment
            (@PathVariable(name = "userId") String userId,
             @PathVariable(name = "assessmentId") String assessmentId,
             @RequestBody AssessmentDto assessmentDto) {

        return new ResponseEntity<>(assessmentService.evaluateAssessment
                (userId, assessmentId, assessmentDto), HttpStatus.OK);
    }

    @DeleteMapping("assessments/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> deleteAssessment (@PathVariable(name = "id") String id) {

        return new ResponseEntity<>(assessmentService.deleteAssessment(id), HttpStatus.OK);

    }

    @GetMapping("assessments-information")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<List<AssessmentInformationDto>> getAssessmentInformation(){
        return new ResponseEntity<>(assessmentInformationService.getAllAssessmentInformation(), HttpStatus.OK);
    }

}
