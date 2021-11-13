package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "team")
public class Team extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private User teamLeader;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private List<User> members;

    public Team() {
    }

    public Team(String name, User teamLeader, List<User> members) {
        this.name = name;
        this.teamLeader = teamLeader;
        this.members = members;
    }
}
