import persistence.*;
import ui.CookbookUI;
import ui.LoginUI;
import application.Cookbook;
import ui.UserUiModel;

import javax.swing.*;
import java.awt.*;

public class CookbookApplication {

    public static void main(String[] args) {
        Database.setupDatabase();
        RecipeRepositoryImplementation recipeRepository = new RecipeRepositoryImplementation();
        UserRepositoryImplementation userRepository = new UserRepositoryImplementation();
        ReviewRepositoryImplementation reviewRepository = new ReviewRepositoryImplementation();

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
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new CookbookUI(cookbook);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


}
