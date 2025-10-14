package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.customers.CustomerRequestDTO;
import com.sales_control.rest.sales_api.dto.customers.CustomerResponseDTO;
import com.sales_control.rest.sales_api.entities.CustomerEntity;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.BadRequestException;
import com.sales_control.rest.sales_api.exceptions.ResourceAlreadyExistsException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.CustomersRepository;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomersServiceImpl implements CustomersService {

    private static final Logger log = Logger.getLogger(CustomersServiceImpl.class.getName());

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO, Long userId) {
        log.info("Inicio metodo createCustomer en CustomersServiceImpl");

        if (customersRepository.existsByDocumentId(customerRequestDTO.getDocumentId())){
            throw new ResourceAlreadyExistsException("Ese número de documento ya esta registrado");
        }

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName(customerRequestDTO.getFirstName());
        customer.setLastName(customerRequestDTO.getLastName());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setPhone(customerRequestDTO.getPhone());
        customer.setDocumentId(customerRequestDTO.getDocumentId());
        customer.setUser(user);

        CustomerEntity savedCustomer = customersRepository.save(customer);

        CustomerResponseDTO createResponse = new CustomerResponseDTO();
        createResponse.setCustomerId(savedCustomer.getCustomerId());
        createResponse.setUserId(savedCustomer.getUser().getUserId());
        createResponse.setFirstName(savedCustomer.getFirstName());
        createResponse.setLastName(savedCustomer.getLastName());
        createResponse.setAddress(savedCustomer.getAddress());
        createResponse.setEmail(savedCustomer.getEmail());
        createResponse.setPhone(savedCustomer.getPhone());
        createResponse.setDocumentId(savedCustomer.getDocumentId());

        log.info("Termina metodo createCustomer en CustomersServiceImpl");
        return createResponse;
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO) {

        log.info("Inicio metodo updateCustomer en CustomersServiceImpl");

        CustomerEntity customer = customersRepository.findById(customerId)
                        .orElseThrow(()-> new ResourceNotFoundException("El cliente con ID" + customerId + " No encontrado"));

        if (!customer.getDocumentId().equals(customerRequestDTO.getDocumentId()) && customersRepository.existsByDocumentId(customerRequestDTO.getDocumentId())) {
            throw new BadRequestException("Ya existe un usuario con ese número de documento.");
        }

        customer.setFirstName(customerRequestDTO.getFirstName());
        customer.setLastName(customerRequestDTO.getLastName());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setPhone(customerRequestDTO.getPhone());
        customer.setDocumentId(customerRequestDTO.getDocumentId());

        CustomerEntity updatedCustomer = customersRepository.save(customer);

        CustomerResponseDTO updateResponse = new CustomerResponseDTO();
        updateResponse.setFirstName(updatedCustomer.getFirstName());
        updateResponse.setLastName(updatedCustomer.getLastName());
        updateResponse.setAddress(updatedCustomer.getAddress());
        updateResponse.setEmail(updatedCustomer.getEmail());
        updateResponse.setPhone(updatedCustomer.getPhone());
        updateResponse.setDocumentId(updatedCustomer.getDocumentId());

        log.info("Termina metodo updateCustomer en CustomersServiceImpl");
        return updateResponse;
    }

    @Override
    public CustomerResponseDTO findCustomerById(Long customerId) {

        log.info("Inicio metodo findCustomerById en CustomersServiceImpl");

        CustomerEntity customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " +customerId));

        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setFirstName(customer.getFirstName());
        responseDTO.setLastName(customer.getLastName());
        responseDTO.setAddress(customer.getAddress());
        responseDTO.setEmail(customer.getEmail());
        responseDTO.setPhone(customer.getPhone());
        responseDTO.setDocumentId(customer.getDocumentId());

        log.info("Termina metodo findCustomerById en CustomersServiceImpl");
        return responseDTO;
    }

    @Override
    public List<CustomerResponseDTO> findAllCustomers() {

        log.info("Inicio metodo findAllCustomers en CustomersServiceImpl");
        return customersRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerResponseDTO customerResponse = new CustomerResponseDTO();
                    customerResponse.setFirstName(customer.getFirstName());
                    customerResponse.setLastName(customer.getLastName());
                    customerResponse.setAddress(customer.getAddress());
                    customerResponse.setEmail(customer.getEmail());
                    customerResponse.setPhone(customer.getPhone());
                    log.info("Termina metodo findAllCustomers en CustomersServiceImpl");
                    return customerResponse;

                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomerById(Long customerId) {

        log.info("Inicio metodo deleteCustomerById en CustomersServiceImpl");
        if (!customersRepository.existsById(customerId)){
            throw new ResourceNotFoundException("Cliente con ID" +customerId+ " NO encontrado");
        }

        log.info("Termina metodo deleteCustomerById en CustomersServiceImpl");
        customersRepository.deleteById(customerId);

    }
}
