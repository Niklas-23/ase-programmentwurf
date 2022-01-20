package ui;

import Recipe.Recipe;

import java.util.List;

public class UserUiModel {

    private final String username;
    private final List<Recipe> recipes;

    public UserUiModel(String username, List<Recipe> recipes) {
        this.username = username;
        this.recipes = recipes;
    }

    public String getUsername() {
        return username;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
