package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

    @Lob
    @Column(name = "image_bytes", columnDefinition = "bytea")
    private byte[] imageBytes;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Image() {
    }

    public Image(byte[] imageBytes, User user) {
        this.imageBytes = imageBytes;
        this.user = user;
    }
}
