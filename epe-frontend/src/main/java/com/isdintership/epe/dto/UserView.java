package com.isdintership.epe.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.util.AssessmentUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {

    private String id;
    private String email;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate employmentDate;
    private String phoneNumber;
    private String job;
    private String bio;
    private String buddyId;
    private String token;
    private String image;

    //private File imageFile;

    public static UserView fromUser(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setEmail(user.getEmail());
        userView.setFirstname(user.getFirstname());
        userView.setLastname(user.getLastname());
        userView.setBirthDate(user.getBirthDate());
        userView.setEmploymentDate(user.getEmploymentDate());
        userView.setPhoneNumber(user.getPhoneNumber());
        userView.setJob(user.getJob().getJobTitle());
        userView.setBuddyId(user.getBuddyId());
        userView.setImage(new String(user.getImageBytes()));
        userView.setBio(user.getBio());
        return userView;
    }
}
