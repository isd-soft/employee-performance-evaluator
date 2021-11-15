package com.isdintership.epe.dao;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.dto.*;

import java.util.List;
import java.util.Optional;

public interface    UserService {
    String register(RegistrationRequest user);
    UserView login (LoginRequest signInRequest);
    UserView createUser(RegistrationRequest user);
    List<UserView> getAllUsers();
    Optional<UserView> getUserById(String id);
    UserView updateUser(UserView userView, String id);
    String deleteUser(String id);
    PasswordView changePassword(PasswordView passwordView, String id);
    //    UserView findByEmail(String email);
    RoleView changeGroup(RoleView roleView, String id);
    //ImageEditView uploadImage(ImageEditView imageEditView, String id) throws IOException;
}
