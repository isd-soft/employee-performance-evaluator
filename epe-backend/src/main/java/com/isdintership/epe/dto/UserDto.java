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
        userDto.setBuddyId(user.getBuddyId());
        userDto.setImage_bytes(user.getImageBytes());
        return userDto;
    }
}
