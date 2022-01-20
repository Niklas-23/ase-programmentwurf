package ui;

import Review.Review;
import Review.ReviewStar;

import java.util.function.Function;

public class ReviewUiModelToReviewMapper implements Function<ReviewUiModel, Review> {
    @Override
    public Review apply(ReviewUiModel reviewUiModel) {
        return map(reviewUiModel);
    }

    private Review map(ReviewUiModel review) {
        return new Review(
                review.getUsername(),
                review.getReviewText(),
                review.getReviewStars()
        );
    }

    private ReviewStar getReviewStar(int stars) {
        switch (stars) {
            case 1:
                return ReviewStar.ONE;
            case 2:
                return ReviewStar.TWO;
            case 3:
                return ReviewStar.THREE;
            case 4:
                return ReviewStar.FOUR;
            case 5:
                return ReviewStar.FIVE;
            default:
                return ReviewStar.NONE;
        }
    }
}
