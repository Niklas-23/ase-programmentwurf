package ui;

import Review.Review;

import java.util.function.Function;

public class ReviewToReviewUiModelMapper implements Function<Review, ReviewUiModel> {
    @Override
    public ReviewUiModel apply(Review review) {
        return map(review);
    }

    private ReviewUiModel map(Review review) {
        return new ReviewUiModel(
                review.getUsername(),
                review.getReviewText(),
                review.getReviewStars()
        );
    }
}
