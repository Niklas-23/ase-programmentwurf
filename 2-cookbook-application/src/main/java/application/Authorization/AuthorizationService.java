package application.Authorization;

import User.User;
import application.Exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationService {

    private final User user;

    public AuthorizationService(User user) {
        this.user = user;
    }

    /**
     * Check if the current user is authorized to modify the recipe.
     *
     * @param recipeId the recipe the user wants to modify
     * @throws UnauthorizedException exception is thrown if the user is not authorized to modify the recipe
     */
    public void checkAuthorization(long recipeId) throws UnauthorizedException {
        List<Long> recipeIds = new ArrayList<>();
        user.getRecipes().forEach(recipe -> {
            recipeIds.add(recipe.getId());
        });
        if (!recipeIds.contains(recipeId)) {
            throw new UnauthorizedException("You can't edit items from other users");
        }
    }
}
