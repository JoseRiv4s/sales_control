package com.sales_control.rest.sales_api.controller.customers;

import com.sales_control.rest.sales_api.dto.customers.CustomerRequestDTO;
import com.sales_control.rest.sales_api.dto.customers.CustomerResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.CustomersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static Logger log = Logger.getLogger(String.valueOf(CustomerController.class));

    @Autowired
    private CustomersService customersService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/createCustomer")
    public ResponseEntity<CustomerResponseDTO> createCustomer (@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        log.info("Inicio metodo createCustomer en CustomerController");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + email));

        CustomerResponseDTO createdCustomer = customersService.createCustomer(customerRequestDTO, user.getUserId());

        log.info("Termina metodo createCustomer en CustomerController");

        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("updateCustomer/{customerId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer (@PathVariable Long customerId, @RequestBody CustomerRequestDTO customerRequestDTO){
        log.info("Inicio metodo updateCustomer en CustomerController");
        CustomerResponseDTO updatedCustomer = customersService.updateCustomer(customerId, customerRequestDTO);
        log.info("Termina metodo updateCustomer en CustomerController");

        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/findCustomerById/{customerId}")
    public ResponseEntity<CustomerResponseDTO> findCustomerById (@PathVariable Long customerId) {
        log.info("Inicio metodo findCustomerById en CustomerController");
        CustomerResponseDTO foundCustomerById = customersService.findCustomerById(customerId);
        log.info("Termina metodo findCustomerById en CustomerController");

        return ResponseEntity.ok(foundCustomerById);
    }

    @GetMapping("/findAllCustomers")
    public ResponseEntity<List<CustomerResponseDTO>> findAllCustomers() {
        log.info("Inicio metodo findAllCustomers en CustomerController");
        List<CustomerResponseDTO> clientsFound = customersService.findAllCustomers();
        log.info("Termina metodo findAllCustomers en CustomerController");
        return ResponseEntity.ok(clientsFound);
    }

    @DeleteMapping("/deleteCustomerById/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        log.info("Inicio metodo deleteCustomer en CustomerController");
        customersService.deleteCustomerById(customerId);
        log.info("Termina metodo deleteCustomer en CustomerController");
        return ResponseEntity.noContent().build();
    }


}
