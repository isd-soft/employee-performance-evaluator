package com.isdintership.epe.controller;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.Role;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.AssessmentNotFoundException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import com.isdintership.epe.export.ExcelExporter;
import com.isdintership.epe.export.PDFGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.isdintership.epe.entity.RoleEnum.Fields.*;


@RestController
@RequestMapping("api/export")
@RequiredArgsConstructor
public class ExportController {

    private final UserRepository userRepository;
    private final AssessmentRepository assessmentRepository;
    private final PDFGenerator pdfGenerator;



    @GetMapping("/pdf/users/{id}")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void userToPdf(@PathVariable(name = "id") String id,
                            HttpServletResponse response) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException("User with " + id + "was not found"));
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=test.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();
        this.pdfGenerator.exportUserToPdf(response,UserDto.fromUser(user));
    }

    @GetMapping("/pdf/users")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAllUsersToPdf(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();
        this.pdfGenerator.exportAllUsersToPdf(response,users);
    }

    @GetMapping("/{id}/excel")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportToExcel(@PathVariable(name = "id") String id,
                              HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);

        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with " + id + " was not found"));

        ExcelExporter excelExporter = new ExcelExporter(UserDto.fromUser(user));

        excelExporter.export(response);
    }

    @GetMapping("/excel/users")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAllUsersToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);
        List<User> users = userRepository.findAll();
        ExcelExporter excelExporter = new ExcelExporter();
        excelExporter.exportAllUsersToExcel(response,users);
    }

    @GetMapping(value = "/{id}/assessment/pdf")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAssessmentToPdf(@PathVariable(name = "id") String id,
                                      HttpServletResponse response) throws IOException{
        AssessmentDto assessmentDto = AssessmentDto.fromAssessment(assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with " + id + " was not founed")));
        UserDto evaluatedUser = UserDto.fromUser(userRepository.findById(assessmentDto.getUserId()).orElseThrow(() ->
                new UserNotFoundException("Evaluated user with " + id + " was not founed")));
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();
        this.pdfGenerator.exportAssessmentToPdf(response,assessmentDto,evaluatedUser);
    }

    @GetMapping(value = "/{id}/assessment/excel")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAssessmentToExcel(@PathVariable(name = "id") String id,
                                        HttpServletResponse response) throws IOException {
        AssessmentDto assessment = AssessmentDto.fromAssessment(assessmentRepository.findById(id).orElseThrow(() ->
                new AssessmentNotFoundException("Assessment with " + id + " was not founed")));
        UserDto evaluatedUser = UserDto.fromUser(userRepository.findById(assessment.getUserId()).orElseThrow(() ->
                new UserNotFoundException("User with " + id + " was not found")));
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelExporter excelExporter = new ExcelExporter();
        excelExporter.exportAssessmentToExcel(response,assessment,evaluatedUser);
    }

}
