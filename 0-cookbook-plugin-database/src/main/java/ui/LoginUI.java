package ui;

import application.Cookbook;
import User.User;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;

import javax.swing.*;

public class LoginUI {

    private static final UserToUserUiModelMapper userModelMapper = new UserToUserUiModelMapper();

    public static UserUiModel login(Cookbook cookbook) {
        int existingUser = JOptionPane.showConfirmDialog(null, "Do you already have a user?", "Login", JOptionPane.YES_NO_OPTION);
        if (existingUser == JOptionPane.YES_OPTION) {
            //Login with existing user
            String username = JOptionPane.showInputDialog("Enter your username");
            try {
                return userModelMapper.apply(cookbook.login(username));
            } catch (UserDoesNotExistException e) {
                JOptionPane.showMessageDialog(null, "Username does not exist");
                return null;
            }
        } else {
            //Create new user
            String username = JOptionPane.showInputDialog("Enter your new username");
            try {
                return userModelMapper.apply(cookbook.createNewUser(username));
            } catch (UserAlreadyExistsException e) {
                JOptionPane.showMessageDialog(null, "User already exists");
                return null;
            }
        }
    }
}
