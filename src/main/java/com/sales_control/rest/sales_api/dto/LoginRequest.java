package com.sales_control.rest.sales_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
