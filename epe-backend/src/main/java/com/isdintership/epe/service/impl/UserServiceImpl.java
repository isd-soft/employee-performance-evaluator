package com.isdintership.epe.service.impl;


import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.*;
import com.isdintership.epe.exception.*;

import com.isdintership.epe.repository.*;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import com.isdintership.epe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
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
    public UserDto register(RegistrationRequest request) {
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

        user.setRegistrationDate(LocalDateTime.now());

        Role roleUser = roleRepository.findByRole(RoleEnum.ROLE_USER);
        user.setRole(roleUser);

        Job jobUser = jobRepository.findByJobTitle(request.getJob()).orElseThrow(() ->
                new JobNotFoundException("Job with name " + request.getJob() + " not found"));
        user.setJob(jobUser);

        File imageSourceFile = new File("./epe-backend/userDefaultImage.png");

        try {
            user.setImageBytes(encodeImageFromFile(imageSourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Saving user {}", request.getEmail());
        userRepository.save(user);

        return UserDto.fromUser(user);

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

        File imageSourceFile = new File("../epe-backend//userDefaultImage.png");

        try {
            user.setImageBytes(encodeImageFromFile(imageSourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public List<UserDto> getAllBuddies(String id) {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                userDtos.add(UserDto.fromUser(user));
            }
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

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstname() != null) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (String.valueOf(userDto.getBirthDate()) != null) {
            user.setBirthDate(userDto.getBirthDate());
        }
        if (String.valueOf(userDto.getEmploymentDate()) != null) {
            user.setEmploymentDate(userDto.getEmploymentDate());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getJob() != null) {
            Job job = jobRepository.findByJobTitle(userDto.getJob()).orElseThrow(() ->
                    new JobNotFoundException("Job with name " + userDto.getJob() + " not found"));

            user.setJob(job);
        }
        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        if (userDto.getImage() != null) {
            user.setImageBytes(encodeImageFromString(userDto.getImage()));
        }

        return userDto;
    }

    @Override
    @Transactional
    public UserDto updateUserAsAdmin(UserDto userDto, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        if (userDto.getBuddyId() != null) {
            userRepository.findById(userDto.getBuddyId()).orElseThrow(() ->
                    new UserNotFoundException("Buddy with id " + id + " was not found"));
            user.setBuddyId(userDto.getBuddyId());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstname() != null) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (String.valueOf(userDto.getBirthDate()) != null) {
            user.setBirthDate(userDto.getBirthDate());
        }
        if (String.valueOf(userDto.getEmploymentDate()) != null) {
            user.setEmploymentDate(userDto.getEmploymentDate());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getJob() != null) {
            Job job = jobRepository.findByJobTitle(userDto.getJob()).orElseThrow(() ->
                    new JobNotFoundException("Job with name " + userDto.getJob() + " not found"));

            user.setJob(job);
        }
        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        if (userDto.getImage() != null) {
            user.setImageBytes(encodeImageFromString(userDto.getImage()));
        }



        if (userDto.getRole() != null) {
            Map<String,Integer> roles = new HashMap<>();
            roles.put("User",1);
            roles.put("Administrator",2);
            int roleId = roles.get(userDto.getRole());
            Role role = roleRepository.findById(roleId).orElseThrow(() ->
                    new RoleNotFoundException("Role with id " + roleId + " was not found"));
            user.setRole(role);
        }
        return userDto;
    }

    @Override
    @Transactional
    public CountDto countAll() {
        return new CountDto(userRepository.count());
    }

    @Override
    @Transactional
    public CountDto countAllBuddies() {
        return new CountDto(userRepository.countAllByBuddyIdNotNull());
    }

    @Override
    @Transactional
    public NewUsersThisYearDto countNewUsersThisYear() {
        NewUsersThisYearDto newUsers = new NewUsersThisYearDto();
        for (int i = 0; i < 12; i++) {
            LocalDateTime fromDate = LocalDateTime.of(LocalDateTime.now().getYear(), i + 1, 1, 0, 0);
            newUsers.getMonths().add(i, userRepository.countAllByRegistrationDateBetween(fromDate, fromDate.plusMonths(1)));
        }

        return newUsers;
    }


    @Override
    @Transactional
    public UserDto deleteUser(String id) {

        UserDto userDto = UserDto.fromUser(userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found")));
        userRepository.removeById(id);


        List<User> users = userRepository.findByBuddyId(id);

        for (User user : users) {
            user.setBuddyId(null);
        }

        //return ("User with id " + id + " was deleted");
        return userDto;
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
        teamRepository.findByTeamLeaderId(id).forEach(team -> assignedUsers.addAll(team.getMembers()));
        List<AssignedUserDto> assignedUsersDtos = new ArrayList<>();
        assignedUsers.forEach(user -> assignedUsersDtos.add(AssignedUserDto.fromUser(user)));

        return assignedUsersDtos;
    }

    @Override
    @Transactional
    public List<TeamDto> getTeamByUserId(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user with this id does not exist"));

        List<TeamDto> teamDtoList = new ArrayList();

        List<Team> teamList = teamRepository.findAllByTeamLeaderId(id);
        for (Team team: teamList) {
            teamDtoList.add(TeamDto.fromTeam(team));
        }

        if(user.getTeam() != null) {
            Team team = teamRepository.findById(user.getTeam().getId()).orElseThrow(() ->
                    new TeamNotFoundException("Team for user with id " + id + " was not found"));

            teamDtoList.add(TeamDto.fromTeam(team));
        }

        return teamDtoList;
    }

    @Override
    @Transactional
    public PasswordView changePassword(PasswordView passwordView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + "was not found"));

        boolean isPasswordMatched = passwordEncoder.matches(passwordView.getOldPassword(), user.getPassword());

        if (isPasswordMatched) {
            if (!(passwordView.getNewPassword().equals(passwordView.getNewPasswordConfirmation()))) {
                throw new BadCredentialsException("New password was not confirmed");
            } else if (passwordView.getNewPassword().equals(passwordView.getOldPassword())) {
                throw new BadCredentialsException("Old and new passwords are the same");
            } else {
                user.setPassword(passwordEncoder.encode(passwordView.getNewPassword()));
            }
        } else {
            throw new BadCredentialsException("Old password doesn't match with the old inserted password");
        }

        return passwordView;
    }

    @Override
    @Transactional
    public RoleView changeGroup(RoleView roleView, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));


        Role role = roleRepository.findById(roleView.getId()).orElseThrow(() ->
                new RoleNotFoundException("Role with id " + roleView.getId() + " was not found"));

        user.setRole(role);

        return roleView;
    }


    public static byte[] encodeImageFromFile(File imageFolder) throws IOException {
        FileInputStream imageStream = new FileInputStream(imageFolder);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }


    public static byte[] encodeImageFromFilePath(String imagePath) throws IOException {
        FileInputStream imageStream = new FileInputStream(imagePath);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }

    public static byte[] encodeImageFromString(String imageBase64Encode) {
        byte[] finalData = imageBase64Encode.getBytes();
        return finalData;
    }

}


