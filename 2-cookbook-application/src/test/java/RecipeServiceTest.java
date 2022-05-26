import Recipe.Recipe;
import Recipe.RecipeRepository;
import Recipe.Category;
import User.User;
import application.Authorization.AuthorizationService;
import application.Exceptions.UnauthorizedException;
import application.Recipe.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    private RecipeService recipeService;
    private AuthorizationService auth;

    @BeforeEach
    void beforeEach() {
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test_username");
        when(user.getRecipes()).thenReturn(new ArrayList<>());
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe1 = mock(Recipe.class);
        Recipe recipe2 = mock(Recipe.class);
        when(recipe1.getCategory()).thenReturn(Category.FAMILY);
        when(recipe1.getRecipeName()).thenReturn("recipe_one_name");
        when(recipe1.getId()).thenReturn((long) 11);
        when(recipe2.getCategory()).thenReturn(Category.VEGAN);
        when(recipe2.getRecipeName()).thenReturn("recipe_two_name");
        when(recipe2.getId()).thenReturn((long) 22);
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        when(recipeRepository.findAllRecipes()).thenReturn(recipeList);
        when(recipeRepository.findRecipeById(any(Long.class))).thenReturn(recipe1);
        auth = mock(AuthorizationService.class);
        recipeService = new RecipeService(recipeRepository, auth, user);
    }

    @Test
    void testRecipeSearchCaseIgnore() {
        List<Recipe> searchResult = recipeService.searchRecipesByName("Recipe_ONE_Name");
        assertAll("recipe search",
                () -> assertEquals(1, searchResult.size()),
                () -> assertEquals("recipe_one_name", searchResult.get(0).getRecipeName()),
                () -> assertEquals(11, searchResult.get(0).getId())
        );
    }

    @Test
    void testRecipeSearchContainValue() {
        List<Recipe> searchResult = recipeService.searchRecipesByName("recipe");
        assertAll("recipe search",
                () -> assertEquals(2, searchResult.size()),
                () -> assertEquals(11, searchResult.get(0).getId()),
                () -> assertEquals(22, searchResult.get(1).getId())
        );
    }

    @Test
    void testCombinedSearchRecipeName() {
        List<Recipe> searchResult = recipeService.combinedSearch("recipe_one_name");
        assertAll("recipe search",
                () -> assertEquals(1, searchResult.size()),
                () -> assertEquals("recipe_one_name", searchResult.get(0).getRecipeName()),
                () -> assertEquals(11, searchResult.get(0).getId())
        );
    }

    @Test
    void testCombinedSearchCategory() {
        List<Recipe> searchResult = recipeService.combinedSearch("FAMILY");
        assertAll("recipe search",
                () -> assertEquals(1, searchResult.size()),
                () -> assertEquals("recipe_one_name", searchResult.get(0).getRecipeName()),
                () -> assertEquals(11, searchResult.get(0).getId())
        );
    }

    @Test
    void testUpdateRecipe() throws UnauthorizedException {
        //Update recipe
        Recipe updateRecipe = mock(Recipe.class);
        when(updateRecipe.getId()).thenReturn((long) 33);
        doNothing().when(auth).checkAuthorization(any(Long.class));
        assertDoesNotThrow(() -> recipeService.updateRecipe(updateRecipe));

        //Update recipe from other user
        Recipe recipeFromOtherUser = mock(Recipe.class);
        when(recipeFromOtherUser.getId()).thenReturn((long) 11);
        doThrow(UnauthorizedException.class).when(auth).checkAuthorization(any(Long.class));
        assertThrows(UnauthorizedException.class, () -> recipeService.updateRecipe(recipeFromOtherUser));

    }
}
