import Ingredient.Ingredient;
import Ingredient.Unit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    @Test
    public void createIngredient(){
        Ingredient ingredient = new Ingredient("salt", 1.0, Unit.PINCH);
        assertAll("ingredient",
                () -> assertEquals("salt", ingredient.getIngredientName()),
                () -> assertEquals(1.0, ingredient.getAmount()),
                () -> assertEquals(Unit.PINCH, ingredient.getUnit())
        );
    }

    @Test
    public void convertMilliliterToLiter(){
        Ingredient ingredient = new Ingredient("water", 1500, Unit.MILLILITER);
        assertEquals(1.5, ingredient.getAmount());
    }

    @Test
    public void convertGramToKilo(){
        Ingredient ingredient = new Ingredient("potatoes", 2500, Unit.GRAM);
        assertEquals(2.5, ingredient.getAmount());
    }
}
