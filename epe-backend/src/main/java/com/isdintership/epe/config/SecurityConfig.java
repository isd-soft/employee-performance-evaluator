package com.isdintership.epe.config;


import com.isdintership.epe.security.ExceptionHandlerFilter;
import com.isdintership.epe.security.jwt.JwtConfigurer;
import com.isdintership.epe.security.jwt.JwtTokenProvider;
import org.apache.catalina.filters.CorsFilter;
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
    private ExceptionHandlerFilter exceptionHandlerFilter;

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String AUTHENTICATION_ENDPOINT = "/api/auth/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, ExceptionHandlerFilter exceptionHandlerFilter) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
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
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(exceptionHandlerFilter, ChannelProcessingFilter.class)
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
