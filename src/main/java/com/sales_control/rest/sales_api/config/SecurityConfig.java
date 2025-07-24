package com.sales_control.rest.sales_api.config;

import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import lombok.RequiredArgsConstructor; // Ya no es necesario si solo UsersRepository queda como final

@Configuration
@EnableWebSecurity
// Ya no usamos @RequiredArgsConstructor porque eliminamos el 'final JwtAuthenticationFilter'
// y UsersRepository es la única dependencia final que necesita un constructor.
public class SecurityConfig {

    // Mantenemos UsersRepository si es necesario para el UserDetailsService
    private final UsersRepository usersRepository;

    // Constructor manual para UsersRepository, ya que eliminamos @RequiredArgsConstructor
    public SecurityConfig(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // El JwtAuthenticationFilter ahora se inyecta como un parámetro del método @Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // El filtro se añade aquí, Spring ya habrá creado el bean JwtAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
