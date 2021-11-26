package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.dto.AssessmentTemplateDto;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.service.EmailService;
import com.isdintership.epe.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    @Async
    public void sendEmail(User user, String title, String description){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("employeeperformanceevaluator@gmail.com");

        //Sets the subject and the text of the email
        mailMessage.setSubject("Employee evaluation");
        mailMessage.setText("Hello, " + user.getFirstname() + "," +
                " there is a new assessment starting with the title "
                + title + "\n" + description + "\nGood luck!");


        javaMailSender.send(mailMessage);
    }

}
