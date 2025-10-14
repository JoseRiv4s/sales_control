package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.login.AuthResponse;
import com.sales_control.rest.sales_api.dto.login.LoginRequest;
import com.sales_control.rest.sales_api.dto.login.RegisterRequest;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.BadRequestException;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        UsersEntity user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after authentication"));
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Inicio metodo registerUser en AuthService");

        if (usersRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Ya existe un user con ese email.");
        }

        UsersEntity registeredUser = UsersEntity.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        usersRepository.save(registeredUser);

        String token = jwtService.generateToken(registeredUser);

        log.info("Usuario registrado exitosamente con email: {}", registeredUser.getEmail());

        return new AuthResponse(token);
    }
}
