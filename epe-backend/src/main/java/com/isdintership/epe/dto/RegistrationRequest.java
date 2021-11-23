package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "First name shouldn't be empty")
    private String firstname;

    @NotEmpty(message = "Last name shouldn't be empty")
    private String lastname;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate employmentDate;

    private File imageFile;

    private String imageFolder;

    @NotEmpty(message = "Phone number shouldn't be empty")
    @Pattern(regexp =  "^\\+?(?:[0-9] ?){6,14}[0-9]$")
    private String phoneNumber;

    @NotEmpty(message = "Job position shouldn't be empty")
    private String job;

    @NotEmpty(message = "Bio shouldn't be empty")
    private String bio;

    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

}