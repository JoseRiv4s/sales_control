package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.dto.users.UserResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;

import java.util.List;

public interface UsersService {

    UserResponseDTO updateUser(Long userID, UserResponseDTO users);

    UserResponseDTO findUserById(Long userId);

    List<UserResponseDTO> findAllUsers();

    UsersEntity deleteUserById(Long userId);
}
