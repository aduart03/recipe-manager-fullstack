package com.recipestore.recipe_manager_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipestore.recipe_manager_api.dto.AuthResponse;
import com.recipestore.recipe_manager_api.model.AppUser;
import com.recipestore.recipe_manager_api.service.AuthService;

import jakarta.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register") // what goes out, Requesest body: what comes in, usually json that needs to be turned to java object
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody AppUser user) {
        AppUser savedUser = authService.registerUser(user); // callback register method from authService business logic

        // response instead of returning password or sensitive info
        AuthResponse response = new AuthResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            "User registered successfully",
            null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response); //return status code and the object that you want to return, in this case the user thats going to be persisted to the db
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser( @RequestBody AppUser loginRequest) {
        String token = authService.loginUser(loginRequest);

        AuthResponse response = new AuthResponse(
            null,
            loginRequest.getUsername(),
            "Login successfull",
            token
        );
        return ResponseEntity.ok(response);
    }
    
    
}