package Recipe;

import java.util.List;

public interface RecipeRepository {

    List<Recipe> findAllRecipes();

    Recipe findRecipeById(long id);

    Recipe save(Recipe recipe, String username);

    Recipe update(Recipe recipe);

    void delete(long recipeId);
}
