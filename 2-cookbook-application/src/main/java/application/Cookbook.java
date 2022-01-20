package application;

import Recipe.RecipeRepository;
import Review.ReviewRepository;
import User.User;
import User.UserRepository;
import application.Authorization.AuthorizationService;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;
import application.Recipe.RecipeService;
import application.Review.ReviewService;
import application.User.UserService;

import java.util.ArrayList;

public class Cookbook {

    private User currentUser;

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;

    private UserService userService;
    private RecipeService recipeService;
    private ReviewService reviewService;
    private AuthorizationService auth;

    public Cookbook(UserRepository userRepository, RecipeRepository recipeRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.reviewRepository = reviewRepository;
    }

    public User login(String username) throws UserDoesNotExistException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            currentUser = user;
            initiateServices();
            return currentUser;
        } else {
            throw new UserDoesNotExistException();
        }
    }

    public User createNewUser(String username) throws UserAlreadyExistsException {
        if (userRepository.findUserByUsername(username) != null) {
            throw new UserAlreadyExistsException();
        } else {
            User user = new User(username, new ArrayList<>());
            userRepository.save(user);
            currentUser = user;
            initiateServices();
            return currentUser;
        }
    }

    private void initiateServices() {
        this.auth = new AuthorizationService(currentUser);
        this.recipeService = new RecipeService(recipeRepository, auth, currentUser);
        this.userService = new UserService(userRepository, currentUser);
        this.reviewService = new ReviewService(reviewRepository, currentUser);
    }

    public UserService getUserService() {
        return userService;
    }

    public RecipeService getRecipeService() {
        return recipeService;
    }

    public AuthorizationService getAuthService() {
        return auth;
    }

    public ReviewService getReviewService() {
        return reviewService;
    }
}
