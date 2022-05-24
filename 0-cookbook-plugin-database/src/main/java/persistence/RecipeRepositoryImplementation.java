package persistence;

import Recipe.Recipe;
import Recipe.RecipeRepository;
import User.User;

import java.util.*;

public class RecipeRepositoryImplementation implements RecipeRepository {

    private UserRepositoryImplementation userRepositoryImplementation;
    private long nextRecipeId = 0;

    public RecipeRepositoryImplementation(UserRepositoryImplementation userRepositoryImplementation) {
        this.userRepositoryImplementation = userRepositoryImplementation;
        nextRecipeId = findAllRecipes().size();
    }

    @Override
    public List<Recipe> findAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        userRepositoryImplementation.getUserMap().forEach((key, value) -> {
            recipeList.addAll(value.getRecipes());
        });
        return recipeList;
    }

    @Override
    public Recipe save(Recipe recipe, String username) {
        Recipe modifiedRecipe = new Recipe(nextRecipeId, recipe.getRecipeName(), recipe.getCategory(), recipe.getCookingTime(), recipe.getCookingInstruction(), recipe.getIngredients(), recipe.getReviews());
        userRepositoryImplementation.getUserMap().get(username).getRecipes().add(modifiedRecipe);
        nextRecipeId++;
        return modifiedRecipe;
    }

    @Override
    public Recipe update(Recipe recipe) {
        for (Map.Entry<String, User> entry : userRepositoryImplementation.getUserMap().entrySet()) {
            for (int i = 0; i < entry.getValue().getRecipes().size(); i++) {
                if (entry.getValue().getRecipes().get(i).getId() == recipe.getId()) {
                    Recipe oldRecipe = entry.getValue().getRecipes().get(i);
                    Recipe modifiedRecipe = new Recipe(oldRecipe.getId(), recipe.getRecipeName(), recipe.getCategory(), recipe.getCookingTime(), recipe.getCookingInstruction(), recipe.getIngredients(), recipe.getReviews());
                    entry.getValue().getRecipes().set(i, modifiedRecipe);
                    return modifiedRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(long recipeId) {
        for (Map.Entry<String, User> entry : userRepositoryImplementation.getUserMap().entrySet()) {
            for (int i = 0; i < entry.getValue().getRecipes().size(); i++) {
                if (entry.getValue().getRecipes().get(i).getId() == recipeId) {
                    entry.getValue().getRecipes().remove(i);
                }
            }
        }
    }

    @Override
    public List<Recipe> findRecipesByUser(String username) {
        return userRepositoryImplementation.getUserMap().get(username).getRecipes();
    }

    @Override
    public Recipe findRecipeById(long id) {
        List<Recipe> recipeList = findAllRecipes();
        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getId() == id) {
                return recipeList.get(i);
            }
        }
        return null;
    }
}
