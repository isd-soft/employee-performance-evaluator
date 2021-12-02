package com.isdintership.epe.controller;

import com.isdintership.epe.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping("api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/pdf/users/{id}")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    public void userToPdf(@PathVariable(name = "id") String id,
                            HttpServletResponse response) throws IOException {
        exportService.exportUserToPdf(response, id);
    }

    @GetMapping("/pdf/users")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAllUsersToPdf(HttpServletResponse response) throws IOException {
        exportService.exportAllUsersToPdf(response);
    }

    @GetMapping("/{id}/excel")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportToExcel(@PathVariable(name = "id") String id,
                              HttpServletResponse response) throws IOException {
        exportService.export(id, response);
    }

    @GetMapping("/excel/users")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAllUsersToExcel(HttpServletResponse response) throws IOException {
        exportService.exportAllUsersToExcel(response);
    }

    @GetMapping(value = "/{id}/assessment/pdf")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAssessmentToPdf(@PathVariable(name = "id") String id,
                                      HttpServletResponse response) throws IOException{
        exportService.exportAssessmentToPdf(response, id);
    }

    @GetMapping(value = "/{id}/assessment/excel")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAssessmentToExcel(@PathVariable(name = "id") String id,
                                        HttpServletResponse response) throws IOException {
        exportService.exportAssessmentToExcel(response, id);
    }

}
