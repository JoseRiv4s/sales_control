package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.entities.UsersEntity;

import java.util.List;

public interface UsersService {

    UsersEntity createUser (UsersEntity users);

    UsersEntity updateUser(Long userID, UsersEntity users);

    UsersEntity findUserById(Long userId);

    List<UsersEntity> findAllUsers();

    UsersEntity deleteUserById(Long userId);
}
