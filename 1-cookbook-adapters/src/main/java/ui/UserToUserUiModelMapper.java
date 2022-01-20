package ui;

import User.User;

import java.util.function.Function;

public class UserToUserUiModelMapper implements Function<User, UserUiModel> {
    @Override
    public UserUiModel apply(User user) {
        return map(user);
    }

    private UserUiModel map(User user) {
        return new UserUiModel(
                user.getUsername(),
                user.getRecipes()
        );
    }
}
