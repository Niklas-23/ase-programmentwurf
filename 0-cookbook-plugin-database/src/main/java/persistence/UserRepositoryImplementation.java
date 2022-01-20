package persistence;

import Recipe.Recipe;
import Recipe.RecipeRepository;
import User.User;
import User.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImplementation implements UserRepository {

    RecipeRepository recipeRepository = new RecipeRepositoryImplementation();

    @Override
    public List<User> findAllUsers() {
        String sqlStatement = "SELECT * FROM USER";
        List<User> userList = new ArrayList<>();
        try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sqlStatement);
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                List<Recipe> recipes = recipeRepository.findRecipesByUser(username);
                User user = new User(username, recipes);
                userList.add(user);
                System.out.println("User: " + user.getUsername());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userList;
    }

    @Override
    public User findUserByUsername(String username) {
        String sqlStatement = "SELECT username FROM user WHERE username = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, username);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (!resultSet.next()) {
                System.err.println("User does not exist");
                return null;
            }
            List<Recipe> recipes = recipeRepository.findRecipesByUser(username);
            User user = new User(username, recipes);
            System.out.println("Found User: " + user.getUsername());
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User save(User user) {
        String sqlStatement = "INSERT INTO user (username) VALUES (?)";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.executeUpdate();
            System.out.println("Saved user:" + user.getUsername());
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
