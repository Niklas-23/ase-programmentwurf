package application.Review;

import Review.Review;
import Review.ReviewRepository;
import User.User;
import application.Exceptions.UnallowedReviewException;

import java.util.List;

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final User user;

    public ReviewService(ReviewRepository reviewRepository, User user) {
        this.reviewRepository = reviewRepository;
        this.user = user;
    }

    public void writeReview(Review review, long recipeId) throws UnallowedReviewException {
        List<Review> existingReviews = reviewRepository.findAllReviewsByRecipeId(recipeId);
        for (Review r : existingReviews) {
            if (r.getUsername().equals(review.getUsername())) {
                throw new UnallowedReviewException();
            }
        }
        reviewRepository.save(review, recipeId);
    }

    public void deleteReviewForRecipe(long recipeId) {
        reviewRepository.delete(user.getUsername(), recipeId);
    }
}
