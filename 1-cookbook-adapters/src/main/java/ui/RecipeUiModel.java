package ui;

import Recipe.Category;

import java.util.List;

public class RecipeUiModel {
    private final long id;
    private final String recipeName;
    private final Category category;
    private final String cookingTime;
    private final String cookingInstruction;
    private final List<IngredientUiModel> ingredients;
    private final List<ReviewUiModel> reviews;

    public RecipeUiModel(long id, String recipeName, Category category, String cookingTime, String cookingInstruction, List<IngredientUiModel> ingredients, List<ReviewUiModel> reviews) {
        this.id = id;
        this.recipeName = recipeName;
        this.category = category;
        this.cookingTime = cookingTime;
        this.cookingInstruction = cookingInstruction;
        this.ingredients = ingredients;
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public List<IngredientUiModel> getIngredients() {
        return ingredients;
    }

    public List<ReviewUiModel> getReviews() {
        return reviews;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getCookingInstruction() {
        return cookingInstruction;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return getRecipeName();
    }
}
