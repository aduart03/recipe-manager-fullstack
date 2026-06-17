package com.recipestore.recipe_manager_api.service;

import org.springframework.stereotype.Service;

import com.recipestore.recipe_manager_api.model.AppUser;
import com.recipestore.recipe_manager_api.repository.AppUserRepository;
import java.lang.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final  AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Constructor
    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    // Persist user to database if they dont already exist
    public AppUser registerUser(AppUser user){
        if (appUserRepository.existsByUsername(user.getUsername()) ){
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return appUserRepository.save(user);
    }

    // Will need to send user creds to server using POST method so we can check
    // if user exists and credentials match to the repo
    public String loginUser(AppUser loginRequest){
        AppUser user = appUserRepository.findByUsername(loginRequest.getUsername()).orElseThrow(

            () -> new RuntimeException("Invalid username or password")
        );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(
            user.getUsername(),
            user.getRole());

    }

}
