package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.JobNotFoundException;
import com.isdintership.epe.exception.UserExistsException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.*;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JobRepository jobRepository;
    private final AssessmentRepository assessmentRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String register(RegistrationRequest request) {
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

        return "Registration successful";

    }

    @Override
    @Transactional
    public UserDto login(LoginRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + "was not found")
        );

        String token = jwtTokenProvider.createToken(user);

        UserDto response = UserDto.fromUser(user);
        response.setToken(token);

        return response;
    }

    @Override
    @Transactional
    public UserDto createUser(RegistrationRequest request) {
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

        return (UserDto.fromUser(userRepository.save(user)));
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.fromUser(user));
        }

        return userDtos;
    }

    @Override
    @Transactional
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user with this id does not exist"));
        return UserDto.fromUser(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        if (userDto.getBuddyId() != null) {
            userRepository.findById(userDto.getBuddyId()).orElseThrow(() ->
                    new UserNotFoundException("Buddy with id " + id + " was not found"));
            user.setBuddyId(userDto.getBuddyId());
        }

        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setBirthDate(userDto.getBirthDate());
        user.setEmploymentDate(userDto.getEmploymentDate());
        user.setPhoneNumber(userDto.getPhoneNumber());

        Job job = jobRepository.findByJobTitle(userDto.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + userDto.getJob() + " not found"));

        user.setJob(job);
        user.setBio(userDto.getBio());

        return userDto;
    }

    @Override
    @Transactional
    public String deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));
        userRepository.removeById(id);

        return ("User with id " + id + " was deleted");
    }

    @Override
    @Transactional
    public List<JobsDto> getJobTitles() {
        return jobRepository.findAll().stream()
                .map(JobsDto::fromJob)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AssignedUserDto> getAssignedUsers(String id) {
        userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));

        List<User> assignedUsers = userRepository.findByBuddyId(id);
        Optional<Team> team = teamRepository.findByTeamLeaderId(id);
        team.ifPresent(value -> assignedUsers.addAll(value.getMembers()));

        List<AssignedUserDto> assignedUsersDtos = new ArrayList<>();
        assignedUsers.forEach(user -> assignedUsersDtos.add(AssignedUserDto.fromUser(user)));

        return assignedUsersDtos;
    }

}