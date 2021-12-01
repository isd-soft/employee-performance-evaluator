package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountDto {
    private Long count;

    public CountDto() {
    }

    public CountDto(Long count) {
        this.count = count;
    }
}
