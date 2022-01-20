package ui;

import Ingredient.Unit;

import javax.swing.*;
import java.awt.*;

public class IngredientUI extends JFrame {

    public IngredientUI(RecipeUI recipeUI, RecipeUiModel recipe) {
        setTitle("Add ingredient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(600, 300, 300, 200);
        setLayout(new BorderLayout(5, 5));

        //Panel for ingredient information
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.setLayout(new GridLayout(3, 2));
        JLabel ingredientNameLabel = new JLabel("Ingredient name:");
        JTextField ingredientNameText = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountText = new JTextField();
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox<Unit> unitComboBox = new JComboBox<>();
        DefaultComboBoxModel<Unit> comboBoxModel = new DefaultComboBoxModel<>(Unit.values());
        unitComboBox.setModel(comboBoxModel);

        //Add all ingredient related parts to the ingredientPanel
        ingredientPanel.add(ingredientNameLabel);
        ingredientPanel.add(ingredientNameText);
        ingredientPanel.add(amountLabel);
        ingredientPanel.add(amountText);
        ingredientPanel.add(unitLabel);
        ingredientPanel.add((unitComboBox));

        //Panel with buttons for user interaction
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String ingredientName = ingredientNameText.getText();
            String amount = amountText.getText();
            Unit unit = comboBoxModel.getElementAt(unitComboBox.getSelectedIndex());
            IngredientUiModel ingredient = new IngredientUiModel(ingredientName, amount, unit);
            recipe.getIngredients().add(ingredient);
            recipeUI.updateIngredientListModel();
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        //Add buttons to buttonPanel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        //Add ui elements to the frame
        add(ingredientPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
