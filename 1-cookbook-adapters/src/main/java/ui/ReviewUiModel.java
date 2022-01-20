package ui;

import Review.ReviewStar;

public class ReviewUiModel {
    private final String username;
    private final String reviewText;
    private final ReviewStar reviewStars;

    public ReviewUiModel(String username, String reviewText, ReviewStar reviewStars) {
        this.username = username;
        this.reviewText = reviewText;
        this.reviewStars = reviewStars;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getUsername() {
        return username;
    }

    public ReviewStar getReviewStars() {
        return reviewStars;
    }

    @Override
    public String toString() {
        return "(" + reviewStars.getNumberOfStars() + " Sterne) " + username + ": " + reviewText;
    }
}
