package com.sales_control.rest.sales_api.controller;

import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.service.contract.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static Logger log = Logger.getLogger(String.valueOf(UsersController.class));

    @Autowired
    private UsersService usersService;


    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UsersEntity users){
        log.info("Inicio metodo createUsuario en UsersController");
        UsersEntity newUser = usersService.createUser(users);
        log.info("Termina metodo createUser en UsersController");
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UsersEntity users){
        log.info("Inicia metodo updateUser en UsersController");
        UsersEntity userUpdated = usersService.updateUser(userId, users);
        log.info("Termina metodo updateUser en UsersController");
        return ResponseEntity.ok(userUpdated);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId){
        log.info("Inicia metodo getUser en UsersController");
        UsersEntity userFound = usersService.findUserById(userId);
        log.info("Termina metodo getUser en UsersController");
        return ResponseEntity.ok(userFound);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> findAllUsers (){
        log.info("Inicia metodo getAllUsers en UsersController");
        List<UsersEntity> usersFound = usersService.findAllUsers();
        log.info("Termina metodo getAllUsers en UsersController");
        return ResponseEntity.ok(usersFound);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId){
        log.info("Inicia metodo deleteUser en UsersController");
        usersService.deleteUserById(userId);
        log.info("Termina metodo deleteUser en UsersController");
        return ResponseEntity.noContent().build(); //
    }
}
