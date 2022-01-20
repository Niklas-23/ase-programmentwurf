package Ingredient;

public enum Unit {
    LITER("L"),
    MILLILITER("ml"),
    KILO("kg"),
    GRAM("g"),
    PINCH("pinch"),
    PACK("pack"),
    TEASPOON("tsp."),
    TABLESPOON("tbsp.");

    private final String label;

    Unit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
