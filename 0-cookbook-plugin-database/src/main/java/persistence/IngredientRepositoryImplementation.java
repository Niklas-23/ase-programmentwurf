package persistence;

import Ingredient.Ingredient;
import Ingredient.IngredientRepository;
import Ingredient.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepositoryImplementation implements IngredientRepository {
    @Override
    public List<Ingredient> findAllIngredientsByRecipeId(long recipeId) {
        String sqlStatement = "SELECT * FROM ingredient WHERE recipeId=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Ingredient> ingredientList = new ArrayList<>();
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredientName");
                double amount = resultSet.getDouble("amount");
                Unit unit = Unit.valueOf(resultSet.getString("unit"));
                Ingredient ingredient = new Ingredient(ingredientName, amount, unit);
                ingredientList.add(ingredient);
            }
            return ingredientList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Ingredient save(Ingredient ingredient, long recipeId) {
        String sqlStatement = "SELECT * FROM ingredient WHERE recipeId=? AND ingredientName=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            preparedStmt.setString(2, ingredient.getIngredientName());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        sqlStatement = "INSERT INTO ingredient (ingredientName, amount, unit, recipeId) VALUES (?,?,?,?)";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, ingredient.getIngredientName());
            preparedStmt.setDouble(2, ingredient.getAmount());
            preparedStmt.setString(3, ingredient.getUnit().name());
            preparedStmt.setLong(4, recipeId);
            preparedStmt.executeUpdate();
            return ingredient;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void delete(String ingredientName, long recipeId) {
        String sqlStatement = "DELETE FROM ingredient WHERE recipeId=? AND ingredientName=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            preparedStmt.setString(2, ingredientName);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
