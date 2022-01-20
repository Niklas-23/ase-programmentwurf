package ui;

import Recipe.Recipe;

import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeUiModelToRecipeMapper implements Function<RecipeUiModel, Recipe> {

    private final IngredientUiModelToIngredientMapper ingredientUiModelToIngredientMapper = new IngredientUiModelToIngredientMapper();
    private final ReviewUiModelToReviewMapper reviewUiModelToReviewMapper = new ReviewUiModelToReviewMapper();

    @Override
    public Recipe apply(RecipeUiModel recipeUiModel) {
        return map(recipeUiModel);
    }

    private Recipe map(RecipeUiModel recipe) {
        return new Recipe(
                recipe.getId(),
                recipe.getRecipeName(),
                recipe.getCategory(),
                Integer.parseInt(recipe.getCookingTime()),
                recipe.getCookingInstruction(),
                recipe.getIngredients().stream().map(ingredientUiModelToIngredientMapper).collect(Collectors.toList()),
                recipe.getReviews().stream().map(reviewUiModelToReviewMapper).collect(Collectors.toList())
        );
    }
}
