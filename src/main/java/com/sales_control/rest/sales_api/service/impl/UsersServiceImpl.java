package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.RegisterUserDTO;
import com.sales_control.rest.sales_api.dto.UsersDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.BadRequestException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.mapper.RegisterUserMapper;
import com.sales_control.rest.sales_api.mapper.UsersMapper;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UsersServiceImpl implements UsersService {

    private static Logger log = Logger.getLogger(String.valueOf(UsersServiceImpl.class));

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RegisterUserMapper registerUserMapper;

    @Override
    public RegisterUserDTO createUser(RegisterUserDTO registerUserDTO) {
        log.info("Inicio metodo createUser en UsersServiceImpl");
        if (usersRepository.existsByEmail(registerUserDTO.getEmail())){
            throw new BadRequestException("Ya existe un user con ese email.");
        }

        UsersEntity usersEntity = registerUserMapper.toEntity(registerUserDTO);
        UsersEntity savedUser = usersRepository.save(usersEntity);

        log.info("Termina metodo newUser en UsersServiceImpl");
        return registerUserMapper.toDTO(savedUser);
    }

    @Override
    public UsersDTO updateUser(Long userID, UsersDTO usersDTO) {
        log.info("Inicia metodo updateUser en UsersServiceImpl");
        UsersEntity userFound = usersRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userID + " NO encontrado"));

        if (!userFound.getEmail().equals(usersDTO.getEmail()) && usersRepository.existsByEmail(usersDTO.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email.");
        }

        userFound.setFirstName(usersDTO.getFirstName());
        userFound.setLastName(usersDTO.getLastName());
        userFound.setEmail(usersDTO.getEmail());

        UsersEntity updatedUser = usersRepository.save(userFound);
        log.info("Termina metodo updateUser en UsersServiceImpl");
        return usersMapper.toDTO(updatedUser);
    }

    @Override
    public UsersDTO findUserById(Long userId) {
        log.info("Inicio metodo getUserById en UsersServiceImpl");
        UsersEntity getUserById = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userId + " NO encontrado"));
        log.info("Termina metodo getUserById en UsersServiceImpl");
        return usersMapper.toDTO(getUserById);
    }

    @Override
    public List<UsersDTO> findAllUsers() {
        log.info("Inicio metodo getAllsUsers en UsersServiceImpl");
        List<UsersEntity> getAllUsers = usersRepository.findAll();
        log.info("Termina metodo getUserById en UsersServiceImpl");
        return getAllUsers.stream()
                .map(usersMapper::toDTO)
                .toList();
    }

    @Override
    public UsersEntity deleteUserById(Long userId) {
        log.info("Inicio metodo deleteUserById en UsersServiceImpl");
        UsersEntity userToDelete = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userId + " NO encontrado"));
        usersRepository.deleteById(userId);
        log.info("Termina metodo deleteUserById en UsersServiceImpl");
        return userToDelete;
    }

}
