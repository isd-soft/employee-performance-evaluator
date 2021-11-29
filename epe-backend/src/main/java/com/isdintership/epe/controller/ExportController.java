package com.isdintership.epe.controller;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import com.isdintership.epe.export.ExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}/pdf")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    public ResponseEntity<UserDto> exportToPdf(@PathVariable(name = "id") String id, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(exportService.exportToPdf(id,response));
    }


    @GetMapping("/{id}/excel")
    @RolesAllowed({ROLE_USER, ROLE_ADMIN, ROLE_SYSADMIN})
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userRepository.findAll();

        ExcelExporter excelExporter = new ExcelExporter(listUsers);

        System.out.println("sout");

        excelExporter.export(response);
    }
//    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<InputStreamResource> userReport(@PathVariable(name = "id")String id) throws IOException {
//        User user = userRepository.findById(id).orElseThrow(() ->
//                new UserNotFoundException("User with " + id + " was not found"));
//
//        ByteArrayInputStream bis = PDFGenerator.userPDFReport(user);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=user.pdf");
//
//        return ResponseEntity.ok().headers(headers).contentType
//                        (MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(bis));
//    }

}
