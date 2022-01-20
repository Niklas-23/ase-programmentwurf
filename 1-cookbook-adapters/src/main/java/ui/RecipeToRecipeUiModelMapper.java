package ui;

import Recipe.Recipe;

import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeToRecipeUiModelMapper implements Function<Recipe, RecipeUiModel> {

    private final IngredientToIngredientUiModelMapper ingredientModelMapper = new IngredientToIngredientUiModelMapper();
    private final ReviewToReviewUiModelMapper reviewModelMapper = new ReviewToReviewUiModelMapper();

    @Override
    public RecipeUiModel apply(Recipe recipe) {
        return map(recipe);
    }

    private RecipeUiModel map(Recipe recipe) {
        return new RecipeUiModel(
                recipe.getId(),
                recipe.getRecipeName(),
                recipe.getCategory(),
                String.valueOf(recipe.getCookingTime()),
                recipe.getCookingInstruction(),
                recipe.getIngredients().stream().map(ingredientModelMapper).collect(Collectors.toList()),
                recipe.getReviews().stream().map(reviewModelMapper).collect(Collectors.toList()));
    }
}
