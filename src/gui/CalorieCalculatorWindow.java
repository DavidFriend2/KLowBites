package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import Information.Ingredient;
import Information.Meal;
import Information.Recipe;
import UnitConversion.CalorieConverter;
import UnitConversion.MassVolumeConverter;
import UnitConversion.MealCalorieConverter;
import UnitConversion.RecipeCalorieConverter;

/**
 * Creates the Calorie Calculator Window for KILowBites
 * 
 * @author Jayden S
 */
public class CalorieCalculatorWindow extends JFrame {
    private static final long serialVersionUID = 1293847255;

    private static final String ICON1_PATH = "/img/calculate.png";
    private static final String ICON2_PATH = "/img/reset.png";
    private static final String ICON3_PATH = "/img/open.png";

    private JPanel mainPanel;
    private JPanel iconPanel;
    private JPanel inputPanel;
    private JPanel caloriesPanel;
    private JButton calculateButton;
    private JButton resetButton;
    private JButton openButton;
    private JComboBox<String> ingredientComboBox;
    private JTextField amountField;
    private JComboBox<String> unitsComboBox;
    private JTextField caloriesField;

    private ResourceBundle strings;

    public CalorieCalculatorWindow(Locale locale) {
        strings = ResourceBundle.getBundle("resources.Strings", locale);
        loadStrings(locale);
        initializeWindow();
        createPanels();
        addComponentsToPanels();
        assembleMainPanel();
        setupActionListeners();
    }
    
    public void updateLanguage(Locale newLocale) {
      loadStrings(newLocale);
      updateComponentTexts();
      SwingUtilities.updateComponentTreeUI(this);
  }

  private void updateComponentTexts() {
      setTitle(strings.getString("calorie_calculator_title"));
      
      calculateButton.setToolTipText(strings.getString("calorie_calculator_calculate_tooltip"));
      resetButton.setToolTipText(strings.getString("calorie_calculator_reset_tooltip"));
      
      // Update labels
      ((JLabel)inputPanel.getComponent(0)).setText(strings.getString("calorie_calculator_ingredient_label"));
      ((JLabel)inputPanel.getComponent(2)).setText(strings.getString("calorie_calculator_amount_label"));
      ((JLabel)inputPanel.getComponent(4)).setText(strings.getString("calorie_calculator_units_label"));
      ((JLabel)caloriesPanel.getComponent(0)).setText(strings.getString("calorie_calculator_calories_label"));
      
      // Update units in the combo box
      unitsComboBox.removeAllItems();
      unitsComboBox.addItem("");
      unitsComboBox.addItem(strings.getString("unit_pinches"));
      unitsComboBox.addItem(strings.getString("unit_teaspoons"));
      unitsComboBox.addItem(strings.getString("unit_tablespoons"));
      unitsComboBox.addItem(strings.getString("unit_fluid_ounces"));
      unitsComboBox.addItem(strings.getString("unit_cups"));
      unitsComboBox.addItem(strings.getString("unit_pints"));
      unitsComboBox.addItem(strings.getString("unit_quarts"));
      unitsComboBox.addItem(strings.getString("unit_gallons"));
      unitsComboBox.addItem(strings.getString("unit_milliliters"));
      unitsComboBox.addItem(strings.getString("unit_drams"));
      unitsComboBox.addItem(strings.getString("unit_grams"));
      unitsComboBox.addItem(strings.getString("unit_ounces"));
      unitsComboBox.addItem(strings.getString("unit_pounds"));
      
      // Refresh the layout
      revalidate();
      repaint();
  }
    
    private void loadStrings(Locale locale) {
      try {
          strings = ResourceBundle.getBundle("resources.Strings", locale);
      } catch (MissingResourceException e) {
          System.err.println("Could not find resources.Strings for locale " + locale + ": " + e.getMessage());
          e.printStackTrace();
      }
  }

    private void initializeWindow() {
        setTitle(strings.getString("calorie_calculator_title"));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createPanels() {
        mainPanel = new JPanel(new BorderLayout());
        iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        caloriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    private void addComponentsToPanels() {
        addIconsToPanel();
        addInputComponents();
        addCaloriesComponents();
    }

    private void addIconsToPanel() {
        openButton = createIconButton(ICON3_PATH, 30, 30, "tooltip_open");
        calculateButton = createIconButton(ICON1_PATH, 30, 30, "calorie_calculator_calculate_tooltip");
        resetButton = createIconButton(ICON2_PATH, 30, 30, "calorie_calculator_reset_tooltip");
        iconPanel.add(openButton);
        iconPanel.add(calculateButton);
        iconPanel.add(resetButton);
    }

    private JButton createIconButton(String imagePath, int width, int height, String toolTipKey) {
        ImageIcon icon = createImageIcon(imagePath);
        JButton button = new JButton();
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } else {
            button.setText(strings.getString(toolTipKey));
        }
        button.setToolTipText(strings.getString(toolTipKey));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private void addInputComponents() {
      try {
        // load in ingredients automatically
        Ingredient.setIngredients(Ingredient.loadIngredients("IngredientsNutrition/ingredients.ntr"));

        List<Ingredient> ingredientsList = Ingredient.getIngredients();
        ingredientsList.sort(Comparator.comparing(Ingredient::getName));
        
        inputPanel.add(new JLabel(strings.getString("calorie_calculator_ingredient_label")));
        
        String[] ingredientChoices = new String[ingredientsList.size() + 1];
        ingredientChoices[0] = ""; // Default empty item

        int count = 1;
        for (Ingredient currIngredient : ingredientsList) {
            ingredientChoices[count] = currIngredient.getName(); // Use English name directly
            count++;
        }

        ingredientComboBox = new JComboBox<>(ingredientChoices);
        inputPanel.add(ingredientComboBox);

        inputPanel.add(new JLabel(strings.getString("calorie_calculator_amount_label")));
        
        amountField = new JTextField(5);
        inputPanel.add(amountField);

        inputPanel.add(new JLabel(strings.getString("calorie_calculator_units_label")));
        
        unitsComboBox = new JComboBox<>(new String[]{
            "", 
            strings.getString("unit_pinches"),
            strings.getString("unit_teaspoons"),
            strings.getString("unit_tablespoons"),
            strings.getString("unit_fluid_ounces"),
            strings.getString("unit_cups"),
            strings.getString("unit_pints"),
            strings.getString("unit_quarts"),
            strings.getString("unit_gallons"),
            strings.getString("unit_milliliters"),
            strings.getString("unit_drams"),
            strings.getString("unit_grams"),
            strings.getString("unit_ounces"),
            strings.getString("unit_pounds")
        });
        
        inputPanel.add(unitsComboBox);
        
        
      }catch(IOException | ClassNotFoundException ex) {
        System.out.println("Couldnt load ingredients");
      }
      
  }

    private void addCaloriesComponents() {
        caloriesPanel.add(new JLabel(strings.getString("calorie_calculator_calories_label")));
        
        caloriesField = new JTextField(10);
        caloriesField.setEditable(false);
        
        caloriesPanel.add(caloriesField);
    }

    private void assembleMainPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        contentPanel.add(iconPanel, BorderLayout.NORTH);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(caloriesPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.NORTH);
        
        add(mainPanel);
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println(strings.getString("error_file_not_found") + path);
            return null;
        }
    }

    private void setupActionListeners() {
        calculateButton.addActionListener(e -> calculateCalories());
        
        resetButton.addActionListener(e -> resetFields());
        
        openButton.addActionListener(e -> openFiles());
    }

    private void calculateCalories() {
      String ingredient = (String) ingredientComboBox.getSelectedItem();
      String amountStr = amountField.getText();
      String localizedUnit = (String) unitsComboBox.getSelectedItem();

      try {
          double amount = Double.parseDouble(amountStr);
          Ingredient currIngredient = Ingredient.getIngredientbyName(ingredient); // Directly use the selected ingredient
          
          if (currIngredient == null) {
              JOptionPane.showMessageDialog(this, strings.getString("ingredient_not_found"), strings.getString("error_title"), JOptionPane.ERROR_MESSAGE);
              return;
          }

          // Convert localized unit to MassVolumeConverter.Unit enum
          MassVolumeConverter.Unit unit = stringToUnit(localizedUnit);
          
          // Calculate calories using the CalorieConverter
          String result = String.format("%.2f", CalorieConverter.convert(currIngredient, amount, unit));
          caloriesField.setText(result);
      } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(this, strings.getString("calorie_calculator_error_invalid_number"), strings.getString("calorie_calculator_error_title"), JOptionPane.ERROR_MESSAGE);
      }
  }

  private MassVolumeConverter.Unit stringToUnit(String localizedUnit) {
      if (localizedUnit.equals(strings.getString("unit_pinches"))) return MassVolumeConverter.Unit.PINCHES;
      if (localizedUnit.equals(strings.getString("unit_teaspoons"))) return MassVolumeConverter.Unit.TEASPOONS;
      if (localizedUnit.equals(strings.getString("unit_tablespoons"))) return MassVolumeConverter.Unit.TABLESPOONS;
      if (localizedUnit.equals(strings.getString("unit_fluid_ounces"))) return MassVolumeConverter.Unit.FLUID_OUNCES;
      if (localizedUnit.equals(strings.getString("unit_cups"))) return MassVolumeConverter.Unit.CUPS;
      if (localizedUnit.equals(strings.getString("unit_pints"))) return MassVolumeConverter.Unit.PINTS;
      if (localizedUnit.equals(strings.getString("unit_quarts"))) return MassVolumeConverter.Unit.QUARTS;
      if (localizedUnit.equals(strings.getString("unit_gallons"))) return MassVolumeConverter.Unit.GALLONS;
      if (localizedUnit.equals(strings.getString("unit_milliliters"))) return MassVolumeConverter.Unit.MILLILITERS;
      if (localizedUnit.equals(strings.getString("unit_drams"))) return MassVolumeConverter.Unit.DRAMS;
      if (localizedUnit.equals(strings.getString("unit_grams"))) return MassVolumeConverter.Unit.GRAMS;
      if (localizedUnit.equals(strings.getString("unit_ounces"))) return MassVolumeConverter.Unit.OUNCES;
      if (localizedUnit.equals(strings.getString("unit_pounds"))) return MassVolumeConverter.Unit.POUNDS;

      throw new IllegalArgumentException("Unknown unit: " + localizedUnit);
  }
     private void resetFields() {
         ingredientComboBox.setSelectedIndex(0);
         amountField.setText("");
         unitsComboBox.setSelectedIndex(0);
         caloriesField.setText("");
     }
     
     private void openFiles() {
       resetFields();
       JFileChooser chooser = new JFileChooser();
       chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       chooser.setDialogTitle("Chose a file");

       int userSelection = chooser.showOpenDialog(null);
       if (userSelection == JFileChooser.APPROVE_OPTION)
       {
         File chosen = chooser.getSelectedFile();
         if (chosen.getName().endsWith(".mel")) {
           Meal meal;
          try
          {
            meal = Meal.loadMealFromFile(chosen.getAbsolutePath());
            String result = String.format("%.2f", MealCalorieConverter.convertMeal(meal));
            caloriesField.setText(meal.getName() + " " + result);
          }
          catch (ClassNotFoundException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          catch (IOException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
         } else if (chosen.getName().endsWith(".rcp")) {
           Recipe recipe;
          try
          {
            recipe = Recipe.loadRecipeFromFile(chosen.getAbsolutePath());
            String result = String.format("%.2f", RecipeCalorieConverter.convertRecipe(recipe));
            caloriesField.setText(recipe.getName() + " " + result);
          }
          catch (ClassNotFoundException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          catch (IOException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
         } else {
           caloriesField.setText("File not permitted");
         }
       }
     }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             Locale locale = Locale.getDefault(); // change language
             CalorieCalculatorWindow window = new CalorieCalculatorWindow(locale); // Pass locale to constructor
             window.setVisible(true);
         });
     }
}