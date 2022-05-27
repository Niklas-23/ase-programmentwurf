package Recipe;

import Ingredient.Ingredient;
import Review.Review;

import java.util.List;

public final class CreateRecipe {

    private long id;
    private String recipeName;
    private Category category;
    private int cookingTime;
    private String cookingInstruction;
    private List<Ingredient> ingredients;
    private List<Review> reviews;

    private CreateRecipe(long id) {
        this.id = id;
    }

    public static CreateRecipe identity(long id) {
        return new CreateRecipe(id);
    }
    public CreateRecipe named(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    public CreateRecipe inCategory(Category category) {
        this.category = category;
        return this;
    }

    public CreateRecipe instruction(String cookingInstruction) {
        this.cookingInstruction = cookingInstruction;
        return this;
    }

    public CreateRecipe cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public CreateRecipe ingredientList(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Recipe reviewList(List<Review> reviews) {
        this.reviews = reviews;
        return this.build();
    }

    private Recipe build() {
        return new Recipe(-1, this.recipeName, this.category, this.cookingTime, this.cookingInstruction, this.ingredients, this.reviews);
    }
}
