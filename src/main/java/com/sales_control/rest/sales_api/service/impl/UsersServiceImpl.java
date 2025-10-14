package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.users.UserResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.BadRequestException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
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

    @Override
    public UserResponseDTO updateUser(Long userID, UserResponseDTO userResponseDTO) {
        log.info("Inicia metodo updateUser en UsersServiceImpl");
        UsersEntity userFound = usersRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userID + " NO encontrado"));

        if (!userFound.getEmail().equals(userResponseDTO.getEmail()) && usersRepository.existsByEmail(userResponseDTO.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email.");
        }

        userFound.setFirstName(userResponseDTO.getFirstName());
        userFound.setLastName(userResponseDTO.getLastName());
        userFound.setEmail(userResponseDTO.getEmail());

        UsersEntity updatedUser = usersRepository.save(userFound);
        log.info("Termina metodo updateUser en UsersServiceImpl");
        return usersMapper.toDTO(updatedUser);
    }

    @Override
    public UserResponseDTO findUserById(Long userId) {
        log.info("Inicio metodo getUserById en UsersServiceImpl");
        UsersEntity getUserById = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userId + " NO encontrado"));
        log.info("Termina metodo getUserById en UsersServiceImpl");
        return usersMapper.toDTO(getUserById);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
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
