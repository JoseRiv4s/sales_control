package com.sales_control.rest.sales_api.dto.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
