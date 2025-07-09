package com.sales_control.rest.sales_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private int statusCode;

    private LocalDateTime timestamp;

    private String errorDatails;


    public ErrorResponse(String message, int statusCode, String errorDatails) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorDatails = errorDatails;
    }
}
