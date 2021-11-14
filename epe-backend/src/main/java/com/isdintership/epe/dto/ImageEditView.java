package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageEditView {
    //File file;
    String imagePath;

    public static byte[] encodeImage(/*File imageFolder*/ String imagePath) throws IOException {
        //FileInputStream imageStream = new FileInputStream(imageFolder);
        FileInputStream imageStream = new FileInputStream(imagePath);

        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        byte[] finalData = imageString.getBytes();
        imageStream.close();

        return finalData;
    }
}
