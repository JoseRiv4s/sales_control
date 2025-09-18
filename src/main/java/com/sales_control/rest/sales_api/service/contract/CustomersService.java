package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.dto.customers.CustomerRequestDTO;
import com.sales_control.rest.sales_api.dto.customers.CustomerResponseDTO;

import java.util.List;

public interface CustomersService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO, Long userId);
    CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO findCustomerById (Long customerId);
    List<CustomerResponseDTO> findAllCustomers();
    void deleteCustomerById(Long customerId);
}
