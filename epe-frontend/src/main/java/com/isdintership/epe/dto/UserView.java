package com.isdintership.epe.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.util.AssessmentUtil;
import lombok.Data;
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
    private LocalDate birthDate;
    private LocalDate employmentDate;
    private String phoneNumber;
    private String job;
    private String bio;
    private String buddyId;
    private String token;

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

        return userView;
    }
}
