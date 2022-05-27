import Ingredient.Ingredient;
import Recipe.Recipe;
import Recipe.Category;
import Recipe.CreateRecipe;
import Review.Review;
import User.User;
import persistence.*;
import ui.CookbookUI;
import ui.LoginUI;
import application.Cookbook;
import ui.UserUiModel;
import Ingredient.Unit;
import Review.ReviewStar;

import javax.swing.*;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class CookbookApplication {

    private static User getExampleUser() {
        Ingredient ingredient1 = new Ingredient("Wasser", 500, Unit.MILLILITER);
        Ingredient ingredient2 = new Ingredient("Mehl", 200, Unit.GRAM);
        Review review1 = new Review("Alice", "Great!", ReviewStar.FOUR);
        Review review2 = new Review("Alice", "Bad!", ReviewStar.ONE);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        Recipe recipe1 = CreateRecipe.identity(0).named("Pizza").inCategory(Category.FAMILY).cookingTime(30).instruction("Hier steht die Kochanleitung").ingredientList(ingredientList).reviewList(new ArrayList<Review>() {{
            add(review1);
        }});
        Recipe recipe2 = CreateRecipe.identity(1).named("Burger").inCategory(Category.FAMILY).cookingTime(30).instruction("Hier steht die Kochanleitung").ingredientList(ingredientList).reviewList(new ArrayList<Review>() {{
            add(review2);
        }});
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        return new User("Bob", recipeList);
    }

    public static void main(String[] args) {
        UserRepositoryImplementation userRepository = new UserRepositoryImplementation();
        RecipeRepositoryImplementation recipeRepository = new RecipeRepositoryImplementation(userRepository);
        ReviewRepositoryImplementation reviewRepository = new ReviewRepositoryImplementation(recipeRepository);
        userRepository.save(getExampleUser());

        Cookbook cookbook = new Cookbook(userRepository, recipeRepository, reviewRepository);
        UserUiModel user = null;
        while (user == null) {
            user = LoginUI.login(cookbook);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new CookbookUI(cookbook);
                frame.setVisible(true);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

    }


}
