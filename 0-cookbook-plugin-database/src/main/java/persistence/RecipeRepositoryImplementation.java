package persistence;

import Ingredient.Ingredient;
import Recipe.Recipe;
import Recipe.RecipeRepository;
import Review.Review;
import Recipe.Category;

import java.sql.*;
import java.util.*;

public class RecipeRepositoryImplementation implements RecipeRepository {

    private final IngredientRepositoryImplementation ingredientRepository = new IngredientRepositoryImplementation();
    private final ReviewRepositoryImplementation reviewRepository = new ReviewRepositoryImplementation();

    @Override
    public List<Recipe> findAllRecipes() {
        String sqlStatement = "SELECT * FROM recipe";
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sqlStatement);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String recipeName = resultSet.getString("recipeName");
                Category category = Category.valueOf(resultSet.getString("category"));
                int cookingTime = resultSet.getInt("cookingTime");
                String cookingInstruction = resultSet.getString("cookingInstruction");
                List<Ingredient> ingredients = ingredientRepository.findAllIngredientsByRecipeId(id);
                List<Review> reviews = reviewRepository.findAllReviewsByRecipeId(id);
                Recipe recipe = new Recipe(id, recipeName, category, cookingTime, cookingInstruction, ingredients, reviews);
                recipeList.add(recipe);
            }
            return recipeList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Recipe save(Recipe recipe, String username) {
        String sqlStatement = "INSERT INTO recipe (recipeName, category, cookingTime, cookingInstruction, username) VALUES (?,?,?,?,?)";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, recipe.getRecipeName());
            preparedStmt.setString(2, recipe.getCategory().name());
            preparedStmt.setInt(3, recipe.getCookingTime());
            preparedStmt.setString(4, recipe.getCookingInstruction());
            preparedStmt.setString(5, username);
            preparedStmt.executeUpdate();
            for (Ingredient i : recipe.getIngredients()) {
                ingredientRepository.save(i, recipe.getId());
            }
            for (Review r : recipe.getReviews()) {
                reviewRepository.save(r, recipe.getId());
            }
            return recipe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Recipe update(Recipe recipe) {
        String sqlStatement = "UPDATE recipe SET recipeName=?, category=?, cookingTime=?, cookingInstruction=? WHERE id=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, recipe.getRecipeName());
            preparedStmt.setString(2, recipe.getCategory().name());
            preparedStmt.setInt(3, recipe.getCookingTime());
            preparedStmt.setString(4, recipe.getCookingInstruction());
            preparedStmt.setLong(5, recipe.getId());
            preparedStmt.executeUpdate();
            List<Ingredient> existingIngredients = ingredientRepository.findAllIngredientsByRecipeId(recipe.getId());
            for (Ingredient i : existingIngredients) {
                if (!recipe.getIngredients().contains(i)) {
                    ingredientRepository.delete(i.getIngredientName(), recipe.getId());
                }
            }
            for (Ingredient i : recipe.getIngredients()) {
                ingredientRepository.save(i, recipe.getId());
            }
            for (Review r : recipe.getReviews()) {
                reviewRepository.save(r, recipe.getId());
            }
            return recipe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(long recipeId) {
        String sqlStatement = "DELETE FROM recipe WHERE recipeId=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Recipe> findRecipesByUser(String username) {
        String sqlStatement = "SELECT * FROM recipe WHERE username = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, username);
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Recipe> recipeList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String recipeName = resultSet.getString("recipeName");
                Category category = Category.valueOf(resultSet.getString("category"));
                int cookingTime = resultSet.getInt("cookingTime");
                String cookingInstruction = resultSet.getString("cookingInstruction");
                List<Ingredient> ingredients = ingredientRepository.findAllIngredientsByRecipeId(id);
                List<Review> reviews = reviewRepository.findAllReviewsByRecipeId(id);
                Recipe recipe = new Recipe(id, recipeName, category, cookingTime, cookingInstruction, ingredients, reviews);
                recipeList.add(recipe);
            }
            return recipeList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Recipe findRecipeById(long id) {
        String sqlStatement = "SELECT * FROM recipe WHERE id = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();
            resultSet.next();
            String recipeName = resultSet.getString("recipeName");
            Category category = Category.valueOf(resultSet.getString("category"));
            int cookingTime = resultSet.getInt("cookingTime");
            String cookingInstruction = resultSet.getString("cookingInstruction");
            List<Ingredient> ingredients = ingredientRepository.findAllIngredientsByRecipeId(id);
            List<Review> reviews = reviewRepository.findAllReviewsByRecipeId(id);
            return new Recipe(id, recipeName, category, cookingTime, cookingInstruction, ingredients, reviews);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
