package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.BadRequestException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
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

    @Override
    public UsersEntity createUser(UsersEntity users) {
        log.info("Inicio metodo createUser en UsersServiceImpl");
        if (usersRepository.existsByEmail(users.getEmail())){
            throw new BadRequestException("Ya existe un user con ese email.");
        }
        log.info("Termina metodo newUser en UsersServiceImpl");
        return usersRepository.save(users);
    }

    @Override
    public UsersEntity updateUser(Long userID, UsersEntity users) {
        log.info("Inicia metodo updateUser en UsersServiceImpl");
        UsersEntity userFound = usersRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userID + " NO encontrado"));

        if (!userFound.getEmail().equals(users.getEmail()) && usersRepository.existsByEmail(users.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email.");
        }

        userFound.setFirstName(users.getFirstName());
        userFound.setLastName(users.getLastName());
        userFound.setEmail(users.getEmail());
        userFound.setPassword(users.getPassword());

        UsersEntity updatedUser = usersRepository.save(userFound);
        log.info("Termina metodo updateUser en UsersServiceImpl");
        return updatedUser;
    }

    @Override
    public UsersEntity findUserById(Long userId) {
        log.info("Inicio metodo getUserById en UsersServiceImpl");
        UsersEntity getUserById = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User con ID " + userId + " NO encontrado"));
        log.info("Termina metodo getUserById en UsersServiceImpl");
        return getUserById;
    }

    @Override
    public List<UsersEntity> findAllUsers() {
        log.info("Inicio metodo getAllsUsers en UsersServiceImpl");
        List<UsersEntity> getAllUsers = usersRepository.findAll();
        log.info("Termina metodo getUserById en UsersServiceImpl");
        return getAllUsers;
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
