package ui;

import application.Cookbook;
import application.Exceptions.UnauthorizedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class CookbookUI extends JFrame {

    //Application objects
    private final Cookbook cookbook;
    private final RecipeToRecipeUiModelMapper recipeModelMapper = new RecipeToRecipeUiModelMapper();

    //Recipe list need to be accessed to update list model
    private final JList<RecipeUiModel> recipeJList;

    //Recipe Panel and related parts need to be accessed to update the displayed recipe
    private final JPanel recipePanel;
    private final JLabel recipeNameText;
    private final JLabel cookingTimeText;
    private final JLabel recipeCategoryText;
    private final JLabel noRecipeSelectedLabel;
    private final JTextArea cookingInstructionText;
    private final JTextArea ingredientTextArea;
    private final JTextArea reviewTextArea;

    public CookbookUI(Cookbook cookbook) {
        this.cookbook = cookbook;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 700);
        setTitle("Cookbook - Logged in wit user: " + cookbook.getUserService().getUser().getUsername());
        setLayout(new BorderLayout(5, 5));

        //JList for recipe selection
        recipeJList = new JList<>();
        recipeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateListModel(cookbook.getRecipeService().getAllRecipes().stream().map(recipeModelMapper).collect(Collectors.toList()));
        JScrollPane scrollPane = new JScrollPane(recipeJList);
        add(scrollPane, BorderLayout.CENTER);

        //Mouse Listener for recipe selection
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<RecipeUiModel> sourceList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 1) {
                    int index = sourceList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        RecipeUiModel recipe = sourceList.getModel().getElementAt(index);
                        updateRecipeDetails(recipe);
                    }
                }
            }
        };
        recipeJList.addMouseListener(mouseListener);

        //Label to shown when no recipe is selected
        noRecipeSelectedLabel = new JLabel("Recipe information will appear here");
        noRecipeSelectedLabel.setPreferredSize(new Dimension(800, 300));
        add(noRecipeSelectedLabel, BorderLayout.EAST);

        ////Panel with buttons for user interaction
        JPanel buttonPanel = new JPanel();
        JButton createRecipeButton = new JButton("Create new recipe");
        createRecipeButton.addActionListener(e -> {
            openRecipeUi(null);
        });
        JButton updateRecipeButton = new JButton("Update selected recipe");
        updateRecipeButton.addActionListener(e -> {
            openRecipeUi(recipeJList.getSelectedValue());
        });
        JButton ownRecipesButton = new JButton("Show own recipes");
        ownRecipesButton.addActionListener(e -> {
            updateListModel(cookbook.getUserService().getUser().getRecipes().stream().map(recipeModelMapper).collect(Collectors.toList()));
        });
        JButton writeReviewButton = new JButton("Write review");
        writeReviewButton.addActionListener(e -> {
            writeReview();
        });
        JButton deleteReviewButton = new JButton("Delete review");
        deleteReviewButton.addActionListener(e -> {
            deleteReview();
        });
        //Add buttons to buttonPanel
        buttonPanel.add(createRecipeButton);
        buttonPanel.add(updateRecipeButton);
        buttonPanel.add(ownRecipesButton);
        buttonPanel.add(writeReviewButton);
        buttonPanel.add(deleteReviewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //Textfield and Button for recipe search
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JLabel searchForRecipesLabel = new JLabel("Search for recipes: ");
        JTextField recipeSearchTextField = new JTextField();
        recipeSearchTextField.setToolTipText("You can search by recipe name and by category");
        searchPanel.add(searchForRecipesLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(recipeSearchTextField);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        JButton searchRecipesButton = new JButton("Search recipes");
        searchRecipesButton.addActionListener(e -> {
            String searchValue = recipeSearchTextField.getText();
            updateListModel(cookbook.getRecipeService().combinedSearch(searchValue).stream().map(recipeModelMapper).collect(Collectors.toList()));
        });
        JButton showAllRecipesButton = new JButton("Show all recipes");
        showAllRecipesButton.addActionListener(e -> {
            updateListModel(cookbook.getRecipeService().getAllRecipes().stream().map(recipeModelMapper).collect(Collectors.toList()));
        });
        searchPanel.add(searchRecipesButton);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(showAllRecipesButton);
        add(searchPanel, BorderLayout.NORTH);

        //Panel for recipe details
        recipePanel = new JPanel();
        recipePanel.setLayout(new GridLayout(4, 1));
        recipePanel.setPreferredSize(new Dimension(800, 300));
        //The recipeInformationPanel is part of the recipePanel and shows the basic information (name, category, cooking time)
        JPanel recipeInformationPanel = new JPanel();
        recipeInformationPanel.setLayout(new GridLayout(3, 2));
        JLabel recipeNameLabel = new JLabel("Recipe name:");
        recipeNameText = new JLabel();
        recipeInformationPanel.add(recipeNameLabel);
        recipeInformationPanel.add(recipeNameText);
        JLabel recipeCategoryLabel = new JLabel("Recipe category:");
        recipeCategoryText = new JLabel();
        recipeInformationPanel.add(recipeCategoryLabel);
        recipeInformationPanel.add(recipeCategoryText);
        JLabel cookingTimeLabel = new JLabel("Cooking time:");
        cookingTimeText = new JLabel();
        recipeInformationPanel.add(cookingTimeLabel);
        recipeInformationPanel.add(cookingTimeText);
        //TextArea for cooking instruction
        cookingInstructionText = new JTextArea();
        cookingInstructionText.setEditable(false);
        cookingInstructionText.setLineWrap(true);
        cookingInstructionText.setWrapStyleWord(true);
        JScrollPane cookingInstructionScrollPane = new JScrollPane(cookingInstructionText);
        //Panel with Label and TextArea for ingredients
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
        JLabel ingredientLabel = new JLabel("Ingredients:");
        ingredientTextArea = new JTextArea();
        ingredientTextArea.setEditable(false);
        ingredientTextArea.setLineWrap(true);
        ingredientTextArea.setWrapStyleWord(true);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientTextArea);
        ingredientPanel.add(ingredientLabel);
        ingredientPanel.add(ingredientScrollPane);
        //Panel with Label and TextArea for reviews
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
        JLabel reviewLabel = new JLabel("Reviews:");
        reviewTextArea = new JTextArea();
        reviewTextArea.setEditable(false);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        JScrollPane reviewScrollPane = new JScrollPane(reviewTextArea);
        reviewPanel.add(reviewLabel);
        reviewPanel.add(reviewScrollPane);
        //Adding all ui parts to the panel for recipe details
        recipePanel.add(recipeInformationPanel);
        recipePanel.add(cookingInstructionScrollPane);
        recipePanel.add(ingredientPanel);
        recipePanel.add(reviewPanel);
    }

    /**
     * Open the ui to create or modify a recipe.
     * If the user is not authorized to modify the selected recipe, a message will be displayed.
     * When the recipe ui is opened the cookbook ui ist disabled until the recipe ui is closed.
     *
     * @param recipe the recipe to modify. If the parameter is null a new recipe will be created.
     */
    private void openRecipeUi(RecipeUiModel recipe) {
        try {
            if (recipe != null) {
                cookbook.getAuthService().checkAuthorization(recipe.getId());
            }
            RecipeUI recipeUI = new RecipeUI(recipe, cookbook, this);
            recipeUI.setVisible(true);
            setEnabled(false);
        } catch (UnauthorizedException e) {
            JOptionPane.showMessageDialog(null, "You are not authorized to modify this recipe");
        }
    }

    /**
     * Update the model of the JList that displays the recipes.
     * The displayed recipe details from the currently selected recipe will be removed and instead the noRecipeSelectedLabel will be displayed.
     *
     * @param newRecipes a list with the new recipes that should be displayed in the JList
     */
    private void updateListModel(List<RecipeUiModel> newRecipes) {
        DefaultListModel<RecipeUiModel> listModel = new DefaultListModel<>();
        for (RecipeUiModel r : newRecipes) {
            listModel.addElement(r);
        }
        recipeJList.setModel(listModel);
        try {
            remove(recipePanel);
            add(noRecipeSelectedLabel, BorderLayout.EAST);
            revalidate();
            repaint();
        } catch (Exception ignored) {
        }
    }

    /**
     * The noRecipeSelectedLabel will be removed from th ui and instead the recipePanel will be displayed.
     * All ui elements from the recipePanel are filled with the information from the given recipe.
     *
     * @param recipe the recipe to display
     */
    private void updateRecipeDetails(RecipeUiModel recipe) {
        remove(noRecipeSelectedLabel);

        recipeNameText.setText(recipe.getRecipeName());
        recipeCategoryText.setText(recipe.getCategory().name());
        cookingTimeText.setText(recipe.getCookingTime() + " minutes");
        cookingInstructionText.setText(recipe.getCookingInstruction());

        ingredientTextArea.setText("");
        for (IngredientUiModel i : recipe.getIngredients()) {
            ingredientTextArea.append(i.toString() + "\n");
        }
        reviewTextArea.setText("");
        for (ReviewUiModel r : recipe.getReviews()) {
            reviewTextArea.append(r.toString() + "\n\n");
        }

        add(recipePanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    /**
     * Open the review ui to write a review for the selected recipe.
     * If no recipe is selected a message will be displayed.
     */
    private void writeReview() {
        RecipeUiModel selectedRecipe = recipeJList.getSelectedValue();
        if (selectedRecipe != null) {
            ReviewUI reviewUI = new ReviewUI(this, cookbook, selectedRecipe);
            reviewUI.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a recipe");
        }
    }

    /**
     * The own review for the selected recipe wil be deleted.
     * If no recipe is selected a message will be displayed.
     */
    private void deleteReview() {
        RecipeUiModel selectedRecipe = recipeJList.getSelectedValue();
        if (selectedRecipe != null) {
            cookbook.getReviewService().deleteReviewForRecipe(selectedRecipe.getId());
            updateListModel(cookbook.getRecipeService().getAllRecipes().stream().map(recipeModelMapper).collect(Collectors.toList()));
            for (ReviewUiModel r : selectedRecipe.getReviews()) {
                if (r.getUsername().equals(cookbook.getUserService().getUser().getUsername())) {
                    selectedRecipe.getReviews().remove(r);
                    updateRecipeDetails(selectedRecipe);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a recipe");
        }
    }

    /**
     * Method for the recipe ui to call when recipe editing is finished.
     * If a recipe was added or modified the list model is updated.
     *
     * @param changes boolean if a recipe was created or modified
     */
    public void recipeEditingFinished(boolean changes) {
        setEnabled(true);
        if (changes) {
            updateListModel(cookbook.getUserService().getUser().getRecipes().stream().map(recipeModelMapper).collect(Collectors.toList()));
        }
    }

    /**
     * Method for the review ui to call when a review was added.
     * Then the recipe details will be updated to display the new review.
     */
    public void reviewEditingFinished() {
        updateRecipeDetails(recipeJList.getSelectedValue());
    }
}
