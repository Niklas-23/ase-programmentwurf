import Recipe.Recipe;
import User.User;
import application.Authorization.AuthorizationService;
import application.Exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {

    @Test
    void testCheckAuthorization() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test_username");
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe = mock(Recipe.class);
        when(recipe.getId()).thenReturn((long) 2);
        recipeList.add(recipe);
        when(user.getRecipes()).thenReturn(recipeList);
        AuthorizationService auth = new AuthorizationService(user);
        assertDoesNotThrow(() -> auth.checkAuthorization(2));
        assertThrows(UnauthorizedException.class, () -> auth.checkAuthorization(3));
    }
}
