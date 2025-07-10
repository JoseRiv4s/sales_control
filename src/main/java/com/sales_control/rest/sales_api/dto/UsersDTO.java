package com.sales_control.rest.sales_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
