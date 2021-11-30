package com.isdintership.epe.service.impl;

import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.EmailService;
import com.isdintership.epe.service.JobAnniversaryChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
class JobAnniversaryCheckerImpl implements JobAnniversaryChecker {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * SUN")
//    @Scheduled(fixedDelay = 50000L)
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