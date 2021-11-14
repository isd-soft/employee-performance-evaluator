package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Setter
@Getter
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

    @Lob
    @Column(name = "image_bytes", columnDefinition = "bytea")
    private byte[] imageBytes;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
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

    public static byte[] encodeImageFromFile(File imageFolder) throws IOException {
        FileInputStream imageStream = new FileInputStream(imageFolder);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }


    public static byte[] encodeImageFromFilePath(String imagePath) throws IOException {
        FileInputStream imageStream = new FileInputStream(imagePath);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }

}
