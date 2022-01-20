package Recipe;

import java.util.List;

public interface RecipeRepository {

    List<Recipe> findAllRecipes();

    List<Recipe> findRecipesByUser(String username);

    Recipe findRecipeById(long id);

    Recipe save(Recipe recipe, String username);

    Recipe update(Recipe recipe);

    void delete(long recipeId);
}
