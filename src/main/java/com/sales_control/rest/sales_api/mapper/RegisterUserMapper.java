package com.sales_control.rest.sales_api.mapper;

import com.sales_control.rest.sales_api.dto.RegisterUserDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UsersEntity toEntity(RegisterUserDTO dto) {
        return modelMapper.map(dto, UsersEntity.class);
    }

    public RegisterUserDTO toDTO(UsersEntity usersEntity) {
        return modelMapper.map(usersEntity, RegisterUserDTO.class);
    }

}

