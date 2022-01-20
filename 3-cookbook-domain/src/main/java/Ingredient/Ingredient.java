package Ingredient;

public final class Ingredient {

    private final String ingredientName;
    private final double amount;
    private final Unit unit;

    public Ingredient(String ingredientName, double amount, Unit unit) {
        this.ingredientName = ingredientName;
        if (unit == Unit.GRAM && amount >= 1000) {
            this.amount = amount / 1000;
            this.unit = Unit.KILO;
        } else if (unit == Unit.MILLILITER && amount >= 1000) {
            this.amount = amount / 1000;
            this.unit = Unit.LITER;
        } else {
            this.amount = amount;
            this.unit = unit;
        }
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getAmount() {
        return amount;
    }
}
