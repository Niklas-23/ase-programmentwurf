package Review;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAllReviewsByRecipeId(long recipeId);

    Review save(Review review, long recipeId);

    void delete(String username, long recipeId);
}
