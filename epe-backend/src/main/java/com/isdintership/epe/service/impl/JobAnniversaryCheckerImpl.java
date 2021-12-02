package com.isdintership.epe.service.impl;

import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.EmailService;
import com.isdintership.epe.service.JobAnniversaryChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertFalse;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the JobAnniversaryChecker interface.
 * @author Girlea Adrian
 * */
@Service
@RequiredArgsConstructor
@EnableAsync
@Slf4j
class JobAnniversaryCheckerImpl implements JobAnniversaryChecker {

    private final UserRepository userRepository;
    private final EmailService emailService;

    /**
     * Method executed asynchronous every Sunday on 00:00 that checks if there are any
     * employees that have a employment date anniversary in the next month and sends a
     * notification email if such exist.
     * @author Adrian Girlea
     * */
    @Override
    @Transactional
    @Async
    @Scheduled(cron = "0 0 0 * * SUN")
    public void checkJobAnniversary() {
        userRepository.findAll()
                .forEach(user -> {
                    long yearsRemaining = ChronoUnit.YEARS.between(
                            user.getEmploymentDate(),
                            LocalDate.now()
                    );
                    long  daysRemaining = 365L - ChronoUnit.DAYS.between(
                            user.getEmploymentDate().plusYears(yearsRemaining),
                            LocalDate.now()

                    );
                    if (daysRemaining >= 26 && daysRemaining <= 33)
                        emailService.sendRemainder(user);
                });
    }
}