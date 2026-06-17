package com.recipestore.recipe_manager_api.dto;

public class AuthResponse {

    private Long id;
    private String username;
    private String message;
    private String token;

    public AuthResponse(Long id, String username, String message, String token){
        this.id = id;
        this.username = username;
        this.message = message;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
