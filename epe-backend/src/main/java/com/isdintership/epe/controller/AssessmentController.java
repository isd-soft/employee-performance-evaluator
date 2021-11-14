package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dao.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> deleteAssessment(@PathVariable("id") String id) {
        return ResponseEntity.ok(assessmentService.deleteAssessment(id));
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<String> createAssessment(@RequestBody AssessmentDto assessmentDto){
        return ResponseEntity.ok(assessmentService.createAssessment(assessmentDto));
    }

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<List<AssessmentDto>> getAssessments(){
        return ResponseEntity.ok(assessmentService.getAllAssessments());

    }

    @GetMapping("/{userId}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    public ResponseEntity<AssessmentDto> getAssessment(@PathVariable String userId){
        return ResponseEntity.ok(assessmentService.getAssessment(userId));
    }


    @PutMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<String> updateAssessment(@Valid @RequestBody AssessmentDto assessmentDto) {
        return ResponseEntity.ok(assessmentService.updateAssessment(assessmentDto));
    }

}
