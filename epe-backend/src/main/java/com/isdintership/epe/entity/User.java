package com.isdintership.epe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "birth_date", nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @Column(name = "employment_date", nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate employmentDate;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "bio", columnDefinition = "text")
    private String bio;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image_bytes")
    private byte[] imageBytes;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    List<Assessment> assessments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(name = "buddy_id")
    private String buddyId;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public User() {
    }

    public User(String email, String firstname, String lastname, LocalDate birthDate,
                LocalDate employmentDate, String phoneNumber, Job job, String bio,
                byte[] imageBytes, String password, Role role, Team team, String buddyId) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.employmentDate = employmentDate;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.bio = bio;
        this.imageBytes = imageBytes;
        this.password = password;
        this.role = role;
        this.team = team;
        this.buddyId = buddyId;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthDate=" + birthDate +
                ", employmentDate=" + employmentDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", job=" + job +
                ", bio='" + bio + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", team=" + team +
                ", buddyId=" + buddyId +
//                ", roles=" + roles +
                '}';
    }

}