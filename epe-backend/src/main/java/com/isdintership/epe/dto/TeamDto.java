package com.isdintership.epe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {

    @NotNull
    private String name;

    @NotNull
    private String teamLeaderId;

    @NotNull
    private List<String> membersIds;

}
