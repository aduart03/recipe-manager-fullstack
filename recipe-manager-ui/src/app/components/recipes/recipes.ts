import { Component } from '@angular/core';
import { Recipe } from '../../models/recipe';
import { RecipeService } from '../../services/recipe';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recipes',
  imports: [FormsModule, CommonModule],
  templateUrl: './recipes.html',
  styleUrl: './recipes.css',
})
export class Recipes {


    // Will wtore all recipes here
    recipes: Recipe[] = [];

    // Error message
    errorMessage: string = '';

    constructor(private recipeService: RecipeService) {}

    //Reset form
    private resetForm(): void {
      this.newRecipe = {
        name: '',
        description: '',
        ingredients: '',
        instructions: ''
      };

      this.selectedRecipe = null;
    }

    // Recipe Object and features
    newRecipe: Recipe = {
    name: '',
    description: '',
    ingredients: '',
    instructions: ''
    };

    // createRecipe()
    // Create recipe
    createRecipe(): void {
    this.recipeService.createRecipe(this.newRecipe).subscribe({
      next: (savedRecipe) => {

        this.errorMessage = '';
        this.recipes.push(savedRecipe);
        this.resetForm();
      },
      error: (error) => {
        this.errorMessage = 'Please fill out all fields.';
        console.error('Error creating recipe:', error);
      }
    });
    }

    // getAllRecipes()
    
    loadRecipes(): void {
      this.recipeService.getAllRecipes().subscribe({
        next: (data) => {
          this.recipes = data;
          console.log(data);
        },
        error: (error) => {
          console.error('Error fetching recipes:', error);
        }
      });
    }

    // On page load : will get all recipes and display on the UI
    ngOnInit(): void {
      this.loadRecipes();
    }

    selectedRecipe: Recipe | null = null;

    editRecipe(recipe: Recipe): void {
      this.selectedRecipe = recipe;

      this.newRecipe = {
        id: recipe.id,
        name: recipe.name,
        description: recipe.description,
        ingredients: recipe.ingredients,
        instructions: recipe.instructions
      };
    }

    // Update Recipe
    updateRecipe(): void {
      if (!this.newRecipe.id) {
        return;
      }

      this.recipeService.updateRecipe(this.newRecipe.id, this.newRecipe).subscribe({
        next: () => {

          
          this.errorMessage = '';
          this.loadRecipes();
          this.resetForm();

          this.selectedRecipe = null;
        },
        error: (error) => {
          this.errorMessage = 'Make sure all fields are filled';
          console.error('Error updating recipe:', error);
        }
      });
    }



    // Delete recipe
    deleteRecipe(id: number): void {

      this.recipeService.deleteRecipe(id).subscribe({
        next: () => this.loadRecipes(),
        error: (error) => console.error(error)
      });
    }



}
