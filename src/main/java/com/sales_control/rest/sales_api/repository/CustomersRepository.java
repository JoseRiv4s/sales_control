package com.sales_control.rest.sales_api.repository;

import com.sales_control.rest.sales_api.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByDocumentId(String documentId);
}
