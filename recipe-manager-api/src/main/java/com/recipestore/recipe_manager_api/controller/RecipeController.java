package com.recipestore.recipe_manager_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.recipestore.recipe_manager_api.model.Recipe;
import com.recipestore.recipe_manager_api.repository.RecipeRepository;
import com.recipestore.recipe_manager_api.service.RecipeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Optional<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe updateRecipe) {
        return recipeService.updateRecipe(id, updateRecipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
    }

}
