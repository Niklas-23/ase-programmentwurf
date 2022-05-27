package ui;

import Recipe.Recipe;
import Recipe.CreateRecipe;

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
        return CreateRecipe.identity(recipe.getId())
                .named(recipe.getRecipeName())
                .inCategory(recipe.getCategory())
                .cookingTime(Integer.parseInt(recipe.getCookingTime()))
                .instruction(recipe.getCookingInstruction())
                .ingredientList(recipe.getIngredients().stream().map(ingredientUiModelToIngredientMapper).collect(Collectors.toList()))
                .reviewList(recipe.getReviews().stream().map(reviewUiModelToReviewMapper).collect(Collectors.toList()));
    }
}
