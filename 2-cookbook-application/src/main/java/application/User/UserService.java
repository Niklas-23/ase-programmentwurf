package application.User;

import User.User;
import User.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final User user;

    public UserService(UserRepository userRepository, User user) {
        this.userRepository = userRepository;
        this.user = user;
    }

    public User getUser() {
        return userRepository.findUserByUsername(user.getUsername());
    }
}
