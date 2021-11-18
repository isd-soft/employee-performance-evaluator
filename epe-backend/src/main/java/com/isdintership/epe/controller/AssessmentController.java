package com.isdintership.epe.controller;

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
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final EvaluationGroupService evaluationGroupService;
    private final EvaluationFieldService evaluationFieldService;
    private final String origin = "http://localhost:4200";

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<String> deleteAssessment(@PathVariable("id") String id) {
        return ResponseEntity.ok(assessmentService.deleteAssessment(id));
    }

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<String> createAssessment(@RequestBody AssessmentDto assessmentDto){
        return ResponseEntity.ok(assessmentService.createAssessment(assessmentDto));
    }

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<AssessmentDto>> getAssessments(){
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }

    @GetMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<AssessmentDto> getAssessment(@PathVariable String id){
        return ResponseEntity.ok(assessmentService.getAssessment(id));
    }

    @PutMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<String> updateAssessment(@Valid @RequestBody AssessmentDto assessmentDto) {
        return ResponseEntity.ok(assessmentService.updateAssessment(assessmentDto));
    }

    @GetMapping("/{id}/evaluations")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<EvaluationGroupDto>> getEvaluationGroups(@PathVariable String id){
        return ResponseEntity.ok(evaluationGroupService.getEvaluationGroupsByAssessmentId(id));
    }

    @GetMapping("/{id}/fields")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<EvaluationFieldDto>> getEvaluationFields(@PathVariable String id){
        return ResponseEntity.ok(evaluationFieldService.getEvaluationFieldsByEvaluationGroupId(id));
    }


}
