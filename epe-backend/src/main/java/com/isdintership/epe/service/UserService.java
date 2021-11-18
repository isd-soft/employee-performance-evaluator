package com.isdintership.epe.service;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.dto.*;

import java.util.List;

public interface UserService {
    String register(RegistrationRequest user);

    UserDto login (LoginRequest signInRequest);

    UserDto createUser(RegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUserById(String id);

    UserDto updateUser(UserDto userDto, String id);

    String deleteUser(String id);

    List<JobsDto> getJobTitles();

    PasswordView changePassword(PasswordView passwordView, String id);
    //    UserView findByEmail(String email);
    RoleView changeGroup(RoleView roleView, String id);
    //ImageEditView uploadImage(ImageEditView imageEditView, String id) throws IOException;

    List<AssignedUserDto> getAssignedUsers(String id);
}
