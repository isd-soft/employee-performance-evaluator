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

//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    private String user_id;

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

//    public Image(byte[] imageBytes, String user_id) {
//        this.imageBytes = imageBytes;
//        this.user_id = user_id;
//    }
}
