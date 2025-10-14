package com.sales_control.rest.sales_api.dto.customers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {

    private Long customerId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String documentId;


}
