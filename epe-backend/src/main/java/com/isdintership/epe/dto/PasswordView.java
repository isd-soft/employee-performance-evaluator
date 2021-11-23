package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isdintership.epe.entity.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordView {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;

}
