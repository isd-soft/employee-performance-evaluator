package com.isdintership.epe.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.isdintership.epe.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

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
    private byte[] image_bytes;
    private String image;

    private String role;

    public static UserDto fromUser(User user) {
        UserDto userView = new UserDto();
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

        //userView.setRole(String.valueOf(user.getRole().getRole()));
        if (String.valueOf(user.getRole().getRole()).equals("ROLE_USER")) {
            userView.setRole("User");
        } else if (String.valueOf(user.getRole().getRole()).equals("ROLE_ADMIN")) {
            userView.setRole("Administrator");
        }
        return userView;
    }
}
