package ui;

import Ingredient.Unit;

public class IngredientUiModel {

    private final String ingredientName;
    private final String amount;
    private final Unit unit;

    public IngredientUiModel(String ingredientName, String amount, Unit unit) {
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.unit = unit;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return ingredientName + "   " + amount + " " + unit.getLabel();
    }
}
