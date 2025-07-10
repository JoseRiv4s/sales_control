package com.sales_control.rest.sales_api.mapper;

import com.sales_control.rest.sales_api.dto.UsersDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsersEntity toEntity(UsersDTO usersDTO){
        return modelMapper.map(usersDTO, UsersEntity.class);
    }

    public void toEntity(UsersDTO usersDTO, UsersEntity existingUserEntity){
        modelMapper.map(usersDTO, existingUserEntity);
    }

    public UsersDTO toDTO (UsersEntity usersEntity){
        return modelMapper.map(usersEntity, UsersDTO.class);
    }


}
