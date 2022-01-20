package Recipe;

import java.util.ArrayList;
import java.util.List;

import Review.Review;
import Ingredient.Ingredient;

public class Recipe {

    private final long id;
    private String recipeName;
    private Category category;
    private int cookingTime;
    private String cookingInstruction;
    private final List<Ingredient> ingredients;
    private final List<Review> reviews;

    public Recipe(long id, String recipeName, Category category, int cookingTime, String cookingInstruction, List<Ingredient> ingredients, List<Review> reviews) {
        setRecipeName(recipeName);
        setCategory(category);
        setCookingTime(cookingTime);
        setCookingInstruction(cookingInstruction);
        this.id = id;
        this.ingredients = new ArrayList<>(ingredients);
        this.reviews = new ArrayList<>(reviews);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String newRecipeName) {
        if (!newRecipeName.equals("")) {
            this.recipeName = newRecipeName;
        }
    }

    public String getCookingInstruction() {
        return cookingInstruction;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime > 0) {
            this.cookingTime = cookingTime;
        }
    }

    public void setCookingInstruction(String cookingInstruction) {
        if (!cookingInstruction.equals("")) {
            this.cookingInstruction = cookingInstruction;
        }
    }

    public long getId() {
        return id;
    }
}
