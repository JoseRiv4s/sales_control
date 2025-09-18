package com.sales_control.rest.sales_api.dto.customers;

import lombok.Data;

@Data
public class CustomerRequestDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String documentId;
    private Long userId;

}
