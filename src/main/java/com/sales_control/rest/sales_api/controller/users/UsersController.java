package com.sales_control.rest.sales_api.controller.users;

import com.sales_control.rest.sales_api.dto.users.UserResponseDTO;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private static Logger log = Logger.getLogger(String.valueOf(UsersController.class));

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserResponseDTO users){
        log.info("Inicia metodo updateUser en UsersController");
        UserResponseDTO userUpdated = usersService.updateUser(userId, users);
        log.info("Termina metodo updateUser en UsersController");
        return ResponseEntity.ok(userUpdated);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId){
        log.info("Inicia metodo getUser en UsersController");
        UserResponseDTO userFound = usersService.findUserById(userId);
        log.info("Termina metodo getUser en UsersController");
        return ResponseEntity.ok(userFound);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> findAllUsers (){
        log.info("Inicia metodo getAllUsers en UsersController");
        List<UserResponseDTO> usersFound = usersService.findAllUsers();
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
