package com.isdintership.epe.security;

import com.isdintership.epe.entity.User;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.security.jwt.JwtUserFactory;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private  final Logger log
            = LoggerFactory.getLogger(JwtUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with firstname + username + not found"));
        return JwtUserFactory.create(user);
    }
}
