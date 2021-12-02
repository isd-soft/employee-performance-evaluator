package com.isdintership.epe.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    void exportUserToPdf(HttpServletResponse response, String id) throws IOException;
    void exportAllUsersToPdf(HttpServletResponse response) throws IOException;
    void exportAssessmentToPdf(HttpServletResponse response,String id) throws IOException;
    void export(String id, HttpServletResponse response)throws IOException;
    void exportAllUsersToExcel(HttpServletResponse response)throws IOException;
    void exportAssessmentToExcel(HttpServletResponse response, String id)throws IOException;
}
