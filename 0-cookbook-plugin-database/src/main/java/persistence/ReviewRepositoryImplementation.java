package persistence;

import Review.Review;
import Review.ReviewStar;
import Review.ReviewRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImplementation implements ReviewRepository {

    @Override
    public List<Review> findAllReviewsByRecipeId(long recipeId) {
        String sqlStatement = "SELECT * FROM review WHERE recipeId=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Review> reviewList = new ArrayList<>();
            while (resultSet.next()) {
                String reviewText = resultSet.getString("reviewText");
                ReviewStar reviewStars = ReviewStar.valueOf(resultSet.getString("reviewStar"));
                String username = resultSet.getString("username");
                Review review = new Review(username, reviewText, reviewStars);
                reviewList.add(review);
            }
            return reviewList;

        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());;
            return null;
        }
    }

    @Override
    public Review save(Review review, long recipeId) {
        String sqlStatement = "SELECT * FROM review WHERE recipeId=? AND username=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            preparedStmt.setString(2, review.getUsername());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());;
            return null;
        }

        sqlStatement = "INSERT INTO review (reviewText, reviewStar, username, recipeId) VALUES (?,?,?,?)";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setString(1, review.getReviewText());
            preparedStmt.setString(2, review.getReviewStars().name());
            preparedStmt.setString(3, review.getUsername());
            preparedStmt.setLong(4, recipeId);
            preparedStmt.executeUpdate();
            return review;
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());;
            return null;
        }
    }

    @Override
    public void delete(String username, long recipeId) {
        String sqlStatement = "DELETE FROM review WHERE recipeId=? AND username=?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStmt = connection.prepareStatement(sqlStatement)) {
            preparedStmt.setLong(1, recipeId);
            preparedStmt.setString(2, username);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());;
        }
    }
}
