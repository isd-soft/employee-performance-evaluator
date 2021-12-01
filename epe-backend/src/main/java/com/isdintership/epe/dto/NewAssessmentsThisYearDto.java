package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewAssessmentsThisYearDto {
    private List<Long> months;

    public NewAssessmentsThisYearDto() {
        months = new ArrayList<>();
    }
}
