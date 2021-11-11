package com.isdintership.epe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Messages to client about successful operation
@Data
@AllArgsConstructor
public class SuccessResponse {

    private String message;

}
