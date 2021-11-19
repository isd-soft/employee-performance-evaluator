package com.isdintership.epe.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.isdintership.epe.entity.Team;
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

//    // added by me !!!
//    private String team;
//    // end added !!!

    private String buddyId;
    private String token;

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setEmploymentDate(user.getEmploymentDate());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setJob(user.getJob().getJobTitle());
        userDto.setBio(user.getBio());

//        // added by me !!!
//        if(user.getTeam() != null) {
//            userDto.setTeam(user.getTeam().getName());
//        } else {
//            userDto.setTeam("");
//        }
//        // end added !!!

        userDto.setBuddyId(user.getBuddyId());

        return userDto;
    }
}
