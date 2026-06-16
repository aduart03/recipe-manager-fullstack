package com.recipestore.recipe_manager_api.service;

import org.springframework.stereotype.Service;
import com.recipestore.recipe_manager_api.model.Recipe;
import com.recipestore.recipe_manager_api.repository.RecipeRepository;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class RecipeService {
    // Business Logic.
    // Communicates with: repository, and controller

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }
    

    // Methods
    public List<Recipe> getAllRecipes(){
        // Get all recipes
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id){
        // Get recipe by id
        // need Recipe and need to return specific recipe
        return recipeRepository.findById(id);

    }

     public Recipe createRecipe(Recipe recipe){
        // Create a recipe
        // AAA : Aquire, Action, Assert
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> updateRecipe(Long id, Recipe updatedRecipe){
        // update Recipe
        return recipeRepository.findById(id).map(existingRecipe -> {
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            return recipeRepository.save(existingRecipe);
        });

    }

    public void deleteRecipe(Long id){
        // delete Recipe
        recipeRepository.deleteById(id);
    }

}
