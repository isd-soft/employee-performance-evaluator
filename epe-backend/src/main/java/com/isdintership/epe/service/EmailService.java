package com.isdintership.epe.service;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.entity.Status;
import com.isdintership.epe.entity.User;

import java.util.Set;

public interface EmailService {
    void sendEmail(AssessmentDto assessmentDto);
    void sendRemainder(User user);
}
