package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.dto.RegisterUserDTO;
import com.sales_control.rest.sales_api.dto.UsersDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;

import java.util.List;

public interface UsersService {

    RegisterUserDTO createUser (RegisterUserDTO users);

    UsersDTO updateUser(Long userID, UsersDTO users);

    UsersDTO findUserById(Long userId);

    List<UsersDTO> findAllUsers();

    UsersEntity deleteUserById(Long userId);
}
