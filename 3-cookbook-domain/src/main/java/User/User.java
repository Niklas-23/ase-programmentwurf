package User;

import java.util.ArrayList;
import java.util.List;

import Recipe.Recipe;

public class User {

    private String username;
    private List<Recipe> recipes;

    public User(String username, List<Recipe> recipes) {
        this.username = username;
        this.recipes = new ArrayList<>(recipes);
    }

    public String getUsername() {
        return username;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
