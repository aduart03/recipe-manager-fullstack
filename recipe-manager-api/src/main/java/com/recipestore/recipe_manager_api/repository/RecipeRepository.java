package com.recipestore.recipe_manager_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipestore.recipe_manager_api.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // This is how I can talk to the database
    // Repository for persistance to database -> used for CRUD operations
    /*
        Includes methods such as:
        -save()
        -findAll()
        -etc...
     */
    
}
