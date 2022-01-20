package Ingredient;

import java.util.List;

public interface IngredientRepository {

    List<Ingredient> findAllIngredientsByRecipeId(long recipeId);

    Ingredient save(Ingredient ingredient, long recipeId);

    void delete(String ingredientName, long recipeId);
}
