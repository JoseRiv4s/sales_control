package com.sales_control.rest.sales_api.repository;

import com.sales_control.rest.sales_api.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long>{

    Optional<UsersEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
