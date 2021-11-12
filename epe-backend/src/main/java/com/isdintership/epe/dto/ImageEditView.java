package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.File;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageEditView {
    //File file;
    String imagePath;
}
