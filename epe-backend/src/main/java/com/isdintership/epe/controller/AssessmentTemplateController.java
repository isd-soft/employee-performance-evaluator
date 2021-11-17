package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.service.AssessmentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;

@RestController
@RequestMapping("/api/assessments-templates")
@RequiredArgsConstructor
public class AssessmentTemplateController {

    private final String origin = "http://localhost:4200";

    public final AssessmentTemplateService assessmentTemplateService;

    @PostMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<AssessmentTemplateDto> createAssessmentTemplate(
            @RequestBody AssessmentTemplateDto assessmentTemplateDto) {

        return new ResponseEntity<>(assessmentTemplateService.createAssessmentTemplate(assessmentTemplateDto),
                                    HttpStatus.CREATED);
    }

    @GetMapping
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<List<AssessmentTemplateDto>> getAllAssessmentTemplates() {

        return new ResponseEntity<>(assessmentTemplateService.getAllAssessmentTemplates(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_USER, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<AssessmentTemplateDto> getAssessmentTemplate(@PathVariable(name = "id") String id) {

        return new ResponseEntity<>(assessmentTemplateService.getAssessmentTemplate(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<AssessmentTemplateDto> updateAssessmentTemplate
            (@PathVariable(name = "id") String id,
             @RequestBody AssessmentTemplateDto assessmentTemplateDto) {

        return new ResponseEntity<>(assessmentTemplateService.updateAssessmentTemplate(id, assessmentTemplateDto),
                                    HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ROLE_ADMIN, ROLE_SYSADMIN})
    @CrossOrigin(origins = origin)
    public ResponseEntity<AssessmentTemplateDto> deleteAssessmentTemplate(@PathVariable(name = "id") String id) {

        return new ResponseEntity<>(assessmentTemplateService.deleteAssessmentTemplate(id), HttpStatus.OK);

    }

}