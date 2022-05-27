package ui;

import Recipe.Category;
import application.Cookbook;
import application.Exceptions.UnauthorizedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RecipeUI extends JFrame {

    private final RecipeUiModelToRecipeMapper recipeUiModelToRecipeMapper = new RecipeUiModelToRecipeMapper();
    private final Cookbook cookbook;
    private final CookbookUI cookbookUI;
    private RecipeUiModel recipe;
    private boolean isNewRecipe;

    private final JTextField recipeNameText;
    private final JComboBox<Category> recipeCategoryText;
    private final DefaultComboBoxModel<Category> recipeCategoryModel;
    private final JTextField cookingTimeText;
    private final JTextArea cookingInstructionText;
    private final JList<IngredientUiModel> ingredientJList;


    public RecipeUI(RecipeUiModel recipe, Cookbook cookbook, CookbookUI cookbookUI) {
        this.recipe = recipe;
        this.cookbook = cookbook;
        this.cookbookUI = cookbookUI;

        setTitle("Edit recipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 200, 450, 500);
        setLayout(new BorderLayout(5, 5));

        //Panel for all recipe information
        JPanel recipePanel = new JPanel();
        recipePanel.setLayout(new GridLayout(3, 1));

        //RecipeInformationPanel contains the ui elements for the basic recipe information
        JPanel recipeInformationPanel = new JPanel();
        recipeInformationPanel.setLayout(new GridLayout(3, 2));
        JLabel recipeNameLabel = new JLabel("Recipe name:");
        recipeNameText = new JTextField();
        recipeInformationPanel.add(recipeNameLabel);
        recipeInformationPanel.add(recipeNameText);
        JLabel recipeCategoryLabel = new JLabel("Recipe category:");
        recipeCategoryText = new JComboBox<>();
        recipeCategoryModel = new DefaultComboBoxModel<>(Category.values());
        recipeCategoryText.setModel(recipeCategoryModel);
        recipeInformationPanel.add(recipeCategoryLabel);
        recipeInformationPanel.add(recipeCategoryText);
        JLabel cookingTimeLabel = new JLabel("Cooking time (minutes):");
        cookingTimeText = new JTextField();
        recipeInformationPanel.add(cookingTimeLabel);
        recipeInformationPanel.add(cookingTimeText);

        //Panel for cooking instruction Label and TextArea
        JPanel cookingInstructionPanel = new JPanel();
        cookingInstructionPanel.setLayout(new BoxLayout(cookingInstructionPanel, BoxLayout.Y_AXIS));
        JLabel cookingInstructionLabel = new JLabel("Cooking instruction:");
        cookingInstructionText = new JTextArea();
        cookingInstructionText.setLineWrap(true);
        cookingInstructionText.setWrapStyleWord(true);
        JScrollPane cookingInstructionScrollPane = new JScrollPane(cookingInstructionText);
        cookingInstructionPanel.add(cookingInstructionLabel);
        cookingInstructionPanel.add(cookingInstructionScrollPane);

        //Panel for ingredients list
        JPanel ingredientPanel = new JPanel();
        ingredientJList = new JList<>();
        ingredientJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientJList);
        JLabel ingredientLabel = new JLabel("Ingredients:");
        ingredientPanel.add(ingredientLabel);
        ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
        ingredientPanel.add(ingredientScrollPane);

        //Add all recipe related parts to the recipePanel
        recipePanel.add(recipeInformationPanel);
        recipePanel.add(cookingInstructionPanel);
        recipePanel.add(ingredientPanel);

        //Panel with buttons for user interaction
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            saveRecipe();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            closeWindow(false);
        });
        JButton addIngredientButton = new JButton("Add ingredient");
        addIngredientButton.addActionListener(e -> {
            openIngredientUi();
        });
        JButton removeIngredientButton = new JButton("Remove ingredient");
        removeIngredientButton.addActionListener(e -> {
            removeIngredient();
        });
        JButton deleteRecipeButton = new JButton("Delete recipe");
        deleteRecipeButton.addActionListener(e -> {
            deleteRecipe();
        });
        //Add buttons to buttonPanel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(addIngredientButton);
        buttonPanel.add(removeIngredientButton);

        //Add ui elements to the frame
        add(recipePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        //Add a window listener to the window closing event.
        //This is necessary because if the closeWindow() method is not called the cookbookUI will not be enabled.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow(false);
            }
        });

        initializeRecipe();
    }

    /**
     * If the recipe passed in the constructor is null, a new recipe will be created.
     * Otherwise the ui elements are filled with the information from the passed recipe
     */
    private void initializeRecipe() {
        if (recipe == null) {
            recipe = new RecipeUiModel(0, "", Category.FAMILY, "", "", new ArrayList<>(), new ArrayList<>());
            isNewRecipe = true;
        } else {
            recipeNameText.setText(recipe.getRecipeName());
            recipeCategoryText.setSelectedItem(recipe.getCategory());
            cookingTimeText.setText(recipe.getCookingTime());
            cookingInstructionText.setText(recipe.getCookingInstruction());
            updateIngredientListModel();
            isNewRecipe = false;
        }
    }

    /**
     * Extract the recipe information from the input fields, build a new RecipeUiModel object and save it.
     */
    private void saveRecipe() {
        String recipeName = recipeNameText.getText();
        Category recipeCategory = recipeCategoryModel.getElementAt(recipeCategoryText.getSelectedIndex());
        String cookingTime = cookingTimeText.getText();
        String cookingInstruction = cookingInstructionText.getText();

        RecipeUiModel updatedRecipe = new RecipeUiModel(recipe.getId(), recipeName, recipeCategory, cookingTime, cookingInstruction, recipe.getIngredients(), recipe.getReviews());

        if (isNewRecipe) {
            cookbook.getRecipeService().saveRecipe(recipeUiModelToRecipeMapper.apply(updatedRecipe));
            closeWindow(true);
        } else {
            try {
                cookbook.getRecipeService().updateRecipe(recipeUiModelToRecipeMapper.apply(updatedRecipe));
                closeWindow(true);
            } catch (UnauthorizedException e) {
                JOptionPane.showMessageDialog(null, "You are not authorized to modify this recipe");
            }
        }


    }

    /**
     * Close the recipe ui window and inform the cookbook ui that recipe editing has finished.
     *
     * @param changes true if a recipe was created or modified, false if changes should be discarded
     */
    private void closeWindow(boolean changes) {
        this.cookbookUI.recipeEditingFinished(changes);
        dispose();
    }

    /**
     * Open the ingredient ui to add an ingredient.
     */
    private void openIngredientUi() {
        IngredientUI ingredientUI = new IngredientUI(this, recipe);
        ingredientUI.setVisible(true);
    }

    /**
     * Update the ingredient list model.
     * Method must be public to be accessible from the ingredient ui.
     */
    public void updateIngredientListModel() {
        DefaultListModel<IngredientUiModel> listModel = new DefaultListModel<>();
        for (IngredientUiModel i : recipe.getIngredients()) {
            listModel.addElement(i);
        }
        ingredientJList.setModel(listModel);
    }

    /**
     * Remove the selected ingredient.
     */
    private void removeIngredient() {
        IngredientUiModel ingredient = ingredientJList.getSelectedValue();
        recipe.getIngredients().remove(ingredient);
        updateIngredientListModel();
    }

    /**
     * Delete the opened recipe.
     */
    private void deleteRecipe() {
        try {
            cookbook.getRecipeService().deleteRecipe(recipe.getId());
        } catch (UnauthorizedException e) {
            JOptionPane.showMessageDialog(null, "You are not authorized to delete this recipe");
        }
    }
}
