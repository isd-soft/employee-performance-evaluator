package com.isdintership.epe.controller;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import com.isdintership.epe.export.ExcelExporter;
import com.isdintership.epe.service.PDFGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
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

    private final ExportService exportService;
    private final UserRepository userRepository;

    private final PDFGeneratorService pdfGeneratorService;


//    @GetMapping("/pdf/generate")
//    public void generatePdf(HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=test.pdf";
//        response.setHeader(headerKey, headerValue);
//        response.flushBuffer();
//        this.pdfGeneratorService.export(response);
//    }

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
        this.pdfGeneratorService.exportUserToPdf(response,UserDto.fromUser(user));
    }

    @GetMapping("/pdf/users")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    @Transactional
    public void exportAllUsersToPdf(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=test.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();
        this.pdfGeneratorService.exportAllUsersToPdf(response,users);
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

}
