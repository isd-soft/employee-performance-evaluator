package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.AssessmentDto;
import com.isdintership.epe.entity.Status;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.EmailService;
import com.isdintership.epe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;



    @Override
    @Transactional
    @Async
    public void sendEmail(AssessmentDto assessmentDto){
        String userId = assessmentDto.getUserId();
        Set<User> users = getNotificatedUser(assessmentDto.getUserId());
        User user = userRepository.getById(userId);

        users.forEach( person -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(person.getEmail());
            mailMessage.setFrom("employeeperformanceevaluator@gmail.com");
            //Sets the subject and the text of the email
            mailMessage.setSubject("Employee evaluation");
            mailMessage.setText(
                    "Hello, " + person.getFirstname() + ",\n" +
                    "The assessment associated with the user " + user.getFirstname() + " " + user.getLastname() + "\n" +
                    "has changed its status changed to " + assessmentDto.getStatus().toString() + "\n\n" +
                    "Assessment: " + assessmentDto.getTitle() + "\n" +
                    "Description: " + assessmentDto.getDescription() + "\n\n" +
                    "May the force be with you!");
            javaMailSender.send(mailMessage);
        });
    }

    @Override
    public void sendRemainder(User user) {
        Set<User> userSet = getNotificatedUser(user.getId());
        userSet.forEach(person -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(person.getEmail());
            mailMessage.setFrom("employeeperformanceevaluator@gmail.com");
            mailMessage.setSubject("Employee Evaluation Starting Soon");
            mailMessage.setText(
                    "Hello, " + person.getFirstname() + ",\n" +
                    "The assessment on the user " + user.getFirstname() + " " + user.getLastname() +
                    "will start soon \n" +
                    "Have a good day!");

            javaMailSender.send(mailMessage);
        });
    }

    private Set<User> getNotificatedUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<User> userSet =
                new HashSet<>(userRepository.findByRoleIdIn(List.of(2, 3)));
        userSet.add(user);

        if (user.getBuddyId() != null){
            Optional<User> buddyUser = userRepository.findById(user.getBuddyId());
            buddyUser.ifPresent(userSet::add);
        }if (user.getTeam()!=null){
            userSet.add(user.getTeam().getTeamLeader());
        }

        System.out.println(userSet);
        return userSet;
    }
}
