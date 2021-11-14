package com.isdintership.epe.rest;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.service.impl.AssessmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.ROLE_USER;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentServiceImpl assessmentService;

    @RolesAllowed(ROLE_USER)
    @PostMapping
    public ResponseEntity<SuccessResponse> createAssessment(@RequestBody AssessmentDto assessmentDto){
        return new ResponseEntity<>(assessmentService.createAssessment(assessmentDto), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<AssessmentDto>> getAssessments(){
        return new ResponseEntity<>(assessmentService.getAllAssessments(), HttpStatus.OK);
    }

}
