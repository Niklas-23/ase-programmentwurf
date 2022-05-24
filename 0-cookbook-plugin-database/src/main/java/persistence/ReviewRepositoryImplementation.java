package persistence;

import Recipe.Recipe;
import Review.Review;
import Review.ReviewRepository;

import java.util.List;

public class ReviewRepositoryImplementation implements ReviewRepository {

    private RecipeRepositoryImplementation recipeRepositoryImplementation;

    public ReviewRepositoryImplementation(RecipeRepositoryImplementation recipeRepositoryImplementation) {
        this.recipeRepositoryImplementation = recipeRepositoryImplementation;
    }

    @Override
    public List<Review> findAllReviewsByRecipeId(long recipeId) {
        Recipe recipe = recipeRepositoryImplementation.findRecipeById(recipeId);
        return recipe.getReviews();
    }

    @Override
    public Review save(Review review, long recipeId) {
        Recipe recipe = recipeRepositoryImplementation.findRecipeById(recipeId);
        recipe.getReviews().add(review);
        return review;
    }

    @Override
    public void delete(String username, long recipeId) {
        Recipe recipe = recipeRepositoryImplementation.findRecipeById(recipeId);
        for (int i = 0; i < recipe.getReviews().size(); i++) {
            if (recipe.getReviews().get(i).getUsername().equals(username)) {
                recipe.getReviews().remove(i);
                break;
            }
        }
    }
}
