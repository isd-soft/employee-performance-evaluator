package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;



@Service
@Slf4j
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto exportToPdf(String id, HttpServletResponse response) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with" + id + " was not found"));
        response.setContentType("application/pdf");
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDateTime = String.valueOf(localDateTime);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + user.getFirstname()+ "_" + user.getLastname() + "_"  + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        PdfExporter exporter = new PdfExporter(user);

        exporter.export(response);
        return UserDto.fromUser(user);
    }
}
