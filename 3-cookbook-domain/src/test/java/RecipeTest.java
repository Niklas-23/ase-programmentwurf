import Ingredient.Ingredient;
import Ingredient.Unit;
import Recipe.Recipe;
import Recipe.Category;
import Review.Review;
import Review.ReviewStar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    private Recipe recipe;

    private final long id = 0;
    private final String name = "Pizza";
    private final Category category = Category.FAMILY;
    private final int time = 30;
    private final String instruction = "instruction text for pizza";
    private final Ingredient ingredient = new Ingredient("cheese", 1.0, Unit.PACK);
    private final Review review = new Review("test_user", "review text", ReviewStar.FIVE);

    @BeforeEach
    void beforeEachTest() {
        List<Ingredient> ingredientList = new ArrayList<>();
        List<Review> reviewList = new ArrayList<>();
        ingredientList.add(ingredient);
        reviewList.add(review);
        recipe = new Recipe(id, name, category, time, instruction, ingredientList, reviewList);
    }

    @Test
    void createRecipe() {
        assertAll("recipe",
                () -> assertEquals(id, recipe.getId()),
                () -> assertEquals(name, recipe.getRecipeName()),
                () -> assertEquals(category, recipe.getCategory()),
                () -> assertEquals(time, recipe.getCookingTime()),
                () -> assertEquals(instruction, recipe.getCookingInstruction()),
                () -> assertEquals(ingredient, recipe.getIngredients().get(0)),
                () -> assertEquals(review, recipe.getReviews().get(0))
        );
    }

    @Test
    void updateRecipeName() {
        recipe.setRecipeName("");
        assertEquals(name, recipe.getRecipeName());
        recipe.setRecipeName("Burger");
        assertEquals("Burger", recipe.getRecipeName());
    }

    @Test
    void updateCookingInstruction() {
        recipe.setCookingInstruction("");
        assertEquals(instruction, recipe.getCookingInstruction());
        recipe.setCookingInstruction("updated cooking instruction");
        assertEquals("updated cooking instruction", recipe.getCookingInstruction());
    }

    @Test
    void updateCookingTime() {
        recipe.setCookingInstruction("");
        assertEquals(time, recipe.getCookingTime());
        recipe.setCookingTime(5);
        assertEquals(5, recipe.getCookingTime());
    }
}
