package ui;

import Ingredient.Ingredient;

import java.util.function.Function;

public class IngredientToIngredientUiModelMapper implements Function<Ingredient, IngredientUiModel> {
    @Override
    public IngredientUiModel apply(Ingredient ingredient) {
        return map(ingredient);
    }

    private IngredientUiModel map(Ingredient ingredient) {
        return new IngredientUiModel(
                ingredient.getIngredientName(),
                String.valueOf(ingredient.getAmount()),
                ingredient.getUnit()
        );
    }
}
