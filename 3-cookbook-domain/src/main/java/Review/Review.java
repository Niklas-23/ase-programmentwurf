package Review;


public final class Review {

    private final String username;
    private final String reviewText;
    private final ReviewStar reviewStars;

    public Review(String username, String reviewText, ReviewStar reviewStars) {
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
}
