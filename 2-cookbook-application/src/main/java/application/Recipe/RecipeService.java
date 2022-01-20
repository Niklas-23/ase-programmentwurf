package application.Recipe;

import Recipe.Recipe;
import Recipe.Category;
import Recipe.RecipeRepository;
import User.User;
import application.Authorization.AuthorizationService;
import application.Exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final AuthorizationService auth;
    private final User user;

    public RecipeService(RecipeRepository recipeRepository, AuthorizationService auth, User user) {
        this.recipeRepository = recipeRepository;
        this.auth = auth;
        this.user = user;
    }

    /**
     * Search a recipe by recipe name.
     * The passed search value does not have to be not be equal to the recipe name, but only contained in the recipe name.
     * The search is not case sensitive.
     *
     * @param searchValue the recipe name to search for
     * @return a list of the recipes found
     */
    public List<Recipe> searchRecipesByName(String searchValue) {
        List<Recipe> allRecipes = recipeRepository.findAllRecipes();
        List<Recipe> results = new ArrayList<>();
        for (Recipe element : allRecipes) {
            if (element.getRecipeName().toLowerCase().contains(searchValue.toLowerCase())) {
                results.add(element);
            }
        }
        return results;
    }

    /**
     * Search a recipe by category.
     *
     * @param category the category to search for
     * @return a list of the recipes found
     */
    public List<Recipe> filterRecipesByCategory(Category category) {
        List<Recipe> allRecipes = recipeRepository.findAllRecipes();
        List<Recipe> results = new ArrayList<>();
        for (Recipe element : allRecipes) {
            if (element.getCategory() == category) {
                results.add(element);
            }
        }
        return results;
    }

    /**
     * Search a recipe by name or category.
     * If the passed search value is a category the category search is used.
     * Otherwise the recipe name search is used.
     *
     * @param searchValue the category or name to search for
     * @return a list of the recipes found
     */
    public List<Recipe> combinedSearch(String searchValue) {
        searchValue = searchValue.toUpperCase();
        try {
            Category category = Category.valueOf(searchValue);
            return filterRecipesByCategory(category);
        } catch (IllegalArgumentException e) {
            return searchRecipesByName(searchValue);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllRecipes();
    }


    /**
     * Save a recipe or update a existing recipe.
     *
     * @param recipe the recipe to save or update
     * @throws UnauthorizedException is thrown when the user is not authorized to update the recipe because he is not the creator
     */
    public void saveRecipe(Recipe recipe) throws UnauthorizedException {
        if (recipeRepository.findRecipeById(recipe.getId()) == null) {
            recipeRepository.save(recipe, user.getUsername());
        } else {
            auth.checkAuthorization(recipe.getId());
            recipeRepository.update(recipe);
        }
    }

    /**
     * Delete a recipe.
     *
     * @param recipeId the id of the recipe to delete
     * @throws UnauthorizedException is thrown when the user is not authorized to delete the recipe because he is not the creator
     */
    public void deleteRecipe(long recipeId) throws UnauthorizedException {
        auth.checkAuthorization(recipeId);
        recipeRepository.delete(recipeId);
    }


}
