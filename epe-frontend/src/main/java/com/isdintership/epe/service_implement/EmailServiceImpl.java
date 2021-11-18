package com.isdintership.epe.service_implement;

import com.isdintership.epe.dao.EmailService;
import com.isdintership.epe.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    public void sendEmail(User user){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("employeeperformanceevaluator@gmail.com");


        //Sets the subject and the text of the email
        mailMessage.setSubject("Employee evaluation");
        mailMessage.setText("Random generated string: " + UUID.randomUUID());

        javaMailSender.send(mailMessage);
    }

}
