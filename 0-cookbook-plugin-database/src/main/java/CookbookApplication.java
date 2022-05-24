import persistence.*;
import ui.CookbookUI;
import ui.LoginUI;
import application.Cookbook;
import ui.UserUiModel;

import javax.swing.*;
import java.awt.*;

public class CookbookApplication {

    public static void main(String[] args) {
        UserRepositoryImplementation userRepository = new UserRepositoryImplementation();
        RecipeRepositoryImplementation recipeRepository = new RecipeRepositoryImplementation(userRepository);
        ReviewRepositoryImplementation reviewRepository = new ReviewRepositoryImplementation(recipeRepository);

        Cookbook cookbook = new Cookbook(userRepository, recipeRepository, reviewRepository);
        UserUiModel user = null;
        while (user == null) {
            user = LoginUI.login(cookbook);
        }
        System.out.println("Login successful");
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("SQL ERROR: " + e.getMessage());;
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new CookbookUI(cookbook);
                frame.setVisible(true);
            } catch (Exception e) {
                System.err.println("SQL ERROR: " + e.getMessage());;
            }
        });

    }


}
