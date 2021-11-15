package com.isdintership.epe.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image_bytes")
    private byte[] imageBytes;

    @OneToOne(/*mappedBy = "photo",*/ cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    //@MapsId
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
