package persistence;

import User.User;
import User.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImplementation implements UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserByUsername(String username) {
        return userMap.get(username);
    }

    @Override
    public User save(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
