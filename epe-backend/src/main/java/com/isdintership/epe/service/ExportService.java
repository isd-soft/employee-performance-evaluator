package com.isdintership.epe.service;

import com.isdintership.epe.dto.UserDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    UserDto exportToPdf(String id, HttpServletResponse response) throws IOException;
}
