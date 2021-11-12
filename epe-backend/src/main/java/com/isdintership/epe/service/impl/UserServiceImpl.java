package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.LoginRequest;
import com.isdintership.epe.dto.RegistrationRequest;
import com.isdintership.epe.dto.SuccessResponse;
import com.isdintership.epe.dto.UserView;
import com.isdintership.epe.entity.Job;
import com.isdintership.epe.entity.Role;
import com.isdintership.epe.entity.RoleEnum;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.entity.exception.JobNotFoundException;
import com.isdintership.epe.entity.exception.UserExistsException;
import com.isdintership.epe.entity.exception.UserNotFoundException;
import com.isdintership.epe.repository.JobRepository;
import com.isdintership.epe.repository.RoleRepository;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public SuccessResponse register(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new UserExistsException("User with email " + request.getEmail()
                    + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setEmploymentDate(request.getEmploymentDate());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setBio(request.getBio());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                        new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        log.info("Saving user {}", request.getEmail());
        userRepository.save(user);

        return new SuccessResponse("Registration successful");

    }

//    @Override
//    @Transactional
//    public UserView findByEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() ->
//                new UserNotFoundException("User with email " + email + "was not found"));
//
//        return UserView.fromUser(user);
//    }

    @Override
    @Transactional
    public UserView login(LoginRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + "was not found")
        );

        String token = jwtTokenProvider.createToken(user);

        UserView response = UserView.fromUser(user);
        response.setToken(token);

        return response;
    }

    @Override
    public UserView createUser(RegistrationRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new UserExistsException("User with email " + request.getEmail()
                    + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthDate(request.getBirthDate());
        user.setEmploymentDate(request.getEmploymentDate());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setBio(request.getBio());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        log.info("Saving user {}", request.getEmail());

        return (UserView.fromUser(userRepository.save(user)));
    }

    @Override
    @Transactional
    public List<UserView> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserView> userViews = new ArrayList<>();
        for (User user : users) {
            userViews.add(UserView.fromUser(user));
        }

        return userViews;
    }

    @Override
    @Transactional
    public UserView getUserById(String id) {

        User user = userRepository.getById(id);

        return UserView.fromUser(user);

    }

    @Override
    @Transactional
    public UserView updateUser(UserView userView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        user.setEmail(userView.getEmail());
        user.setFirstname(userView.getFirstname());
        user.setLastname(userView.getLastname());
        user.setBirthDate(userView.getBirthDate());
        user.setEmploymentDate(userView.getEmploymentDate());
        user.setPhoneNumber(userView.getPhoneNumber());

        Job job = jobRepository.findByJobTitle(userView.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + userView.getJob() + " not found"));;
        user.setJob(job);

        user.setBio(userView.getBio());

        return userView;
    }

    @Override
    @Transactional
    public SuccessResponse deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));

        userRepository.removeById(id);

        return new SuccessResponse("User with id " + id + " was deleted");
    }


}
