import Review.Review;
import Review.ReviewRepository;
import User.User;
import application.Exceptions.UnallowedReviewException;
import application.Review.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewServiceTest {

    @Test
    void testWriteReview() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test_username");
        when(user.getRecipes()).thenReturn(new ArrayList<>());
        Review review = mock(Review.class);
        when(review.getUsername()).thenReturn("test_username");
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        when(reviewRepository.findAllReviewsByRecipeId(any(Long.class))).thenReturn(reviewList);
        ReviewService reviewService = new ReviewService(reviewRepository, user);
        Review newReview = mock(Review.class);
        when(newReview.getUsername()).thenReturn("username_from_another_user");
        assertDoesNotThrow(() -> reviewService.writeReview(newReview, 1));
        reviewList.add(newReview);
        assertThrows(UnallowedReviewException.class, () -> reviewService.writeReview(newReview, 1));
    }
}
