package com.isdintership.epe.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class JwtUser implements UserDetails {

    private final String id;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String id, String firstname, String lastname,
                   String password, String email,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "authorities=" + authorities +
                '}';
    }
}
