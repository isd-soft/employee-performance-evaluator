package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.entity.User;

public interface EmailService {
    void sendEmail(User user, String title, String description);
}
