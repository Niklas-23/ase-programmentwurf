package Review;

public enum ReviewStar {
    NONE(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int numberOfStars;

    ReviewStar(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }
}
