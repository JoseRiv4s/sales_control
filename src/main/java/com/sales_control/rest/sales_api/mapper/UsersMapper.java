package com.sales_control.rest.sales_api.mapper;

import com.sales_control.rest.sales_api.dto.users.UserResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsersEntity toEntity(UserResponseDTO userResponseDTO){
        return modelMapper.map(userResponseDTO, UsersEntity.class);
    }

    public void toEntity(UserResponseDTO userResponseDTO, UsersEntity existingUserEntity){
        modelMapper.map(userResponseDTO, existingUserEntity);
    }

    public UserResponseDTO toDTO (UsersEntity usersEntity){
        return modelMapper.map(usersEntity, UserResponseDTO.class);
    }


}
