package ui;

import Ingredient.Ingredient;

import java.util.function.Function;

public class IngredientUiModelToIngredientMapper implements Function<IngredientUiModel, Ingredient> {
    @Override
    public Ingredient apply(IngredientUiModel ingredientUiModel) {
        return map(ingredientUiModel);
    }

    private Ingredient map(IngredientUiModel ingredient) {
        return new Ingredient(
                ingredient.getIngredientName(),
                Double.parseDouble(ingredient.getAmount()),
                ingredient.getUnit()
        );
    }
}
