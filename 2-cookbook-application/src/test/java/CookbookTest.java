import Recipe.RecipeRepository;
import Review.ReviewRepository;
import User.User;
import User.UserRepository;
import application.Cookbook;
import application.Exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CookbookTest {

    @Test
    void testCreateNewUser() {
        UserRepository userRepository = mock(UserRepository.class);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        Cookbook cookbook = new Cookbook(userRepository, recipeRepository, reviewRepository);
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test_username");
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);
        assertDoesNotThrow(() -> cookbook.createNewUser("test_username"));
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(user);
        assertThrows(UserAlreadyExistsException.class, () -> cookbook.createNewUser("test_username"));
    }
}
