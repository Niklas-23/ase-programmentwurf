package ui;

import Review.ReviewStar;
import application.Cookbook;
import application.Exceptions.UnallowedReviewException;

import javax.swing.*;
import java.awt.*;

public class ReviewUI extends JFrame {

    private final ReviewUiModelToReviewMapper reviewUiModelToReviewMapper = new ReviewUiModelToReviewMapper();

    public ReviewUI(CookbookUI cookbookUI, Cookbook cookbook, RecipeUiModel recipe){
        setTitle("Review for "+recipe.getRecipeName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 200, 300, 300);
        setLayout(new BorderLayout(5, 5));

        //ComboBox for selecting number of stars
        JComboBox<ReviewStar> reviewStarJComboBox = new JComboBox<>();
        DefaultComboBoxModel<ReviewStar> reviewStarDefaultComboBoxModel = new DefaultComboBoxModel<>(ReviewStar.values());
        reviewStarJComboBox.setModel(reviewStarDefaultComboBoxModel);

        //TextArea for writing the review
        JTextArea reviewTextArea = new JTextArea();
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);

        //Panel with buttons for user interaction
        JPanel buttonPanel = new JPanel();
        JButton saveReviewButton = new JButton("Save");
        saveReviewButton.addActionListener(e -> {
            String reviewText = reviewTextArea.getText();
            ReviewStar reviewStar = reviewStarJComboBox.getItemAt(reviewStarJComboBox.getSelectedIndex());
            ReviewUiModel review = new ReviewUiModel(cookbook.getUserService().getUser().getUsername(), reviewText, reviewStar);
            try {
                cookbook.getReviewService().writeReviewForRecipe(reviewUiModelToReviewMapper.apply(review), recipe.getId());
                //After saving the new review, the review is manually added to the list presented in the ui.
                //Refreshing the list would require saving the currently selected recipe and reopening the recipe after refreshing.
                recipe.getReviews().add(review);
                cookbookUI.reviewEditingFinished();
                dispose();
            } catch (UnallowedReviewException unallowedReviewException) {
                JOptionPane.showMessageDialog(null, "Your are not allowed to write more than one review per recipe");
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        //Add buttons to buttonPanel
        buttonPanel.add(saveReviewButton);
        buttonPanel.add(cancelButton);

        //Add ui elements to frame
        add(reviewStarJComboBox, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
