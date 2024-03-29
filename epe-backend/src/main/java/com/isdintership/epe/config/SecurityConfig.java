package com.isdintership.epe.config;

import com.isdintership.epe.security.ExceptionHandlerFilter;
import com.isdintership.epe.security.SimpleCORSFilter;
import com.isdintership.epe.security.jwt.JwtConfigurer;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final SimpleCORSFilter corsFilter;

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String AUTHENTICATION_ENDPOINT = "/api/auth/**";
    private static final String[] SWAGGER_ENDPOINTS = {"/swagger-resources/**", "/swagger-ui/",
            "/swagger-ui/**", "/v2/api-docs", "/webjars/**"};
    private static final String SPRING_ACTUATOR = "/api/actuator/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, ExceptionHandlerFilter exceptionHandlerFilter,
                          SimpleCORSFilter corsFilter) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
        this.corsFilter = corsFilter;
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTHENTICATION_ENDPOINT).permitAll()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(SPRING_ACTUATOR).hasRole("ADMIN")
                .antMatchers(SPRING_ACTUATOR).hasRole("SYSADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(exceptionHandlerFilter, ChannelProcessingFilter.class)
                .addFilterAfter(corsFilter, ExceptionHandlerFilter.class)
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
