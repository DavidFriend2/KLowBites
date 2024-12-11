package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
 * Creates the Calorie Calculator Window for KILowBites.
 * 
 */
public class CalorieCalculatorWindow extends JFrame
{
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

  /**
   * Constructor to initialize everything in the window.
   * 
   * @param locale
   *          language used
   * @param unitSystem
   *          to use correct units
   */
  public CalorieCalculatorWindow(final Locale locale, 
      final UnitSystemPreferences.UnitSystem unitSystem)
  {
    strings = ResourceBundle.getBundle("resources.Strings", locale);
    loadStrings(locale);
    initializeWindow();
    createPanels();
    addComponentsToPanels(unitSystem); // Pass unit system here
    assembleMainPanel();
    setupActionListeners();
  }

  /**
   * Changes language of the window.
   * 
   * @param newLocale
   *          the language
   */
  public void updateLanguage(final Locale newLocale)
  {
    loadStrings(newLocale);
    updateComponentTexts();
    SwingUtilities.updateComponentTreeUI(this);
  }

  /**
   * Gets the correct strings based on language.
   * 
   */
  private void updateComponentTexts()
  {
    setTitle(strings.getString("calorie_calculator_title"));

    calculateButton.setToolTipText(strings.getString("calorie_calculator_calculate_tooltip"));
    resetButton.setToolTipText(strings.getString("calorie_calculator_reset_tooltip"));

    // Update labels
    ((JLabel) inputPanel.getComponent(0))
        .setText(strings.getString("calorie_calculator_ingredient_label"));
    ((JLabel) inputPanel.getComponent(2))
        .setText(strings.getString("calorie_calculator_amount_label"));
    ((JLabel) inputPanel.getComponent(4))
        .setText(strings.getString("calorie_calculator_units_label"));
    ((JLabel) caloriesPanel.getComponent(0))
        .setText(strings.getString("calorie_calculator_calories_label"));

    // Update units in the combo box
    unitsComboBox.removeAllItems();
    String[] units = UnitSystemPreferences.getUnitsForCurrentSystem(strings);
    for (String unit : units)
    {
      unitsComboBox.addItem(unit);
    }

    // Refresh the layout
    revalidate();
    repaint();
  }

  /**
   * Loads the correct locale strings.
   * 
   * @param locale
   */
  private void loadStrings(final Locale locale)
  {
    try
    {
      strings = ResourceBundle.getBundle("resources.Strings", locale);
    }
    catch (MissingResourceException e)
    {
      System.err
          .println("Could not find resources.Strings for locale " + locale + ": " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Creates the window.
   * 
   */
  private void initializeWindow()
  {
    setTitle(strings.getString("calorie_calculator_title"));
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  /**
   * Creates the window panels.
   * 
   */
  private void createPanels()
  {
    mainPanel = new JPanel(new BorderLayout());
    iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    caloriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
  }

  /**
   * Adds the components to the window.
   * 
   * @param unitSystem
   *          to use correct units
   */
  private void addComponentsToPanels(final UnitSystemPreferences.UnitSystem unitSystem)
  {
    addIconsToPanel();
    addInputComponents(unitSystem);
    addCaloriesComponents();
  }

  /**
   * Adds all icons to panel.
   */
  private void addIconsToPanel()
  {
    openButton = createIconButton(ICON3_PATH, 30, 30, "tooltip_open");
    calculateButton = createIconButton(ICON1_PATH, 30, 30, "calorie_calculator_calculate_tooltip");
    resetButton = createIconButton(ICON2_PATH, 30, 30, "calorie_calculator_reset_tooltip");
    iconPanel.add(openButton);
    iconPanel.add(calculateButton);
    iconPanel.add(resetButton);
  }

  /**
   * Gathers the correct units for window.
   * 
   */
  public void updateUnits()
  {
    String[] units = UnitSystemPreferences.getUnitsForCurrentSystem(strings);
    unitsComboBox.setModel(new DefaultComboBoxModel<>(units));
    // Any other necessary updates
  }

  /**
   * Creates the icon.
   * 
   * @param imagePath
   *          path of image
   * @param width
   *          of icon
   * @param height
   *          of icon
   * @param toolTipKey
   *          dislays the key
   * @return the button
   */
  private JButton createIconButton(final String imagePath, final int width, final int height,
      final String toolTipKey)
  {
    ImageIcon icon = createImageIcon(imagePath);
    JButton button = new JButton();
    if (icon != null)
    {
      Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
      button.setIcon(new ImageIcon(img));
    }
    else
    {
      button.setText(strings.getString(toolTipKey));
    }
    button.setToolTipText(strings.getString(toolTipKey));
    button.setPreferredSize(new Dimension(width, height));
    return button;
  }

  /**
   * Adds labels and units.
   * 
   * @param unitSystem
   *          of the window
   */
  private void addInputComponents(final UnitSystemPreferences.UnitSystem unitSystem)
  {
    inputPanel.add(new JLabel(strings.getString("calorie_calculator_ingredient_label")));

    List<Ingredient> ingredientsList = Ingredient.getIngredients();
    String[] ingredientChoices = new String[ingredientsList.size() + 1];
    ingredientChoices[0] = "";

    int count = 1;
    for (Ingredient currIngredient : ingredientsList)
    {
      ingredientChoices[count] = currIngredient.getName();
      count++;
    }

    ingredientComboBox = new JComboBox<>(ingredientChoices);
    inputPanel.add(ingredientComboBox);

    inputPanel.add(new JLabel(strings.getString("calorie_calculator_amount_label")));

    amountField = new JTextField(5);
    inputPanel.add(amountField);

    inputPanel.add(new JLabel(strings.getString("calorie_calculator_units_label")));

    String[] units = UnitSystemPreferences.getUnitsForCurrentSystem(strings);
    unitsComboBox = new JComboBox<>(units);
    inputPanel.add(unitsComboBox);

    inputPanel.add(unitsComboBox);
  }

  /**
   * Adds the calorie label.
   * 
   */
  private void addCaloriesComponents()
  {
    caloriesPanel.add(new JLabel(strings.getString("calorie_calculator_calories_label")));

    caloriesField = new JTextField(10);
    caloriesField.setEditable(false);

    caloriesPanel.add(caloriesField);
  }

  /**
   * Creates main window.
   */
  private void assembleMainPanel()
  {
    JPanel contentPanel = new JPanel(new BorderLayout());

    contentPanel.add(iconPanel, BorderLayout.NORTH);
    contentPanel.add(inputPanel, BorderLayout.CENTER);
    contentPanel.add(caloriesPanel, BorderLayout.SOUTH);

    mainPanel.add(contentPanel, BorderLayout.NORTH);

    add(mainPanel);
  }

  /**
   * Gets the images for the window.
   * 
   * @param path
   *          of the icon
   * @return the icon
   */
  private ImageIcon createImageIcon(final String path)
  {
    java.net.URL imgURL = getClass().getResource(path);

    if (imgURL != null)
    {
      return new ImageIcon(imgURL);
    }
    else
    {
      System.err.println(strings.getString("error_file_not_found") + path);
      return null;
    }
  }

  /**
   * Adds listeners to do calculations and open file.
   */
  private void setupActionListeners()
  {
    calculateButton.addActionListener(e -> calculateCalories());

    resetButton.addActionListener(e -> resetFields());

    openButton.addActionListener(e -> openFiles());
  }

  /**
   * Outputs the correct calories.
   */
  private void calculateCalories()
  {
    String ingredient = (String) ingredientComboBox.getSelectedItem();
    String amountStr = amountField.getText();
    String localizedUnit = (String) unitsComboBox.getSelectedItem();

    try
    {
      double amount = Double.parseDouble(amountStr);
      Ingredient currIngredient = Ingredient.getIngredientbyName(ingredient);
      // Directly use the selected ingredient

      if (currIngredient == null)
      {
        JOptionPane.showMessageDialog(this, strings.getString("ingredient_not_found"),
            strings.getString("error_title"), JOptionPane.ERROR_MESSAGE);
        return;
      }

      // Convert localized unit to MassVolumeConverter.Unit enum
      MassVolumeConverter.Unit unit = stringToUnit(localizedUnit);

      // Calculate calories using the CalorieConverter
      String result = String.format("%.2f", CalorieConverter.convert(currIngredient, amount, unit));
      caloriesField.setText(result);
    }
    catch (NumberFormatException ex)
    {
      JOptionPane.showMessageDialog(this,
          strings.getString("calorie_calculator_error_invalid_number"),
          strings.getString("calorie_calculator_error_title"), JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Gets all units.
   * 
   * @param localizedUnit
   *          of the system
   * @return correct unit
   */
  private MassVolumeConverter.Unit stringToUnit(final String localizedUnit)
  {
    if (localizedUnit.equals(strings.getString("unit_pinches")))
      return MassVolumeConverter.Unit.PINCHES;
    if (localizedUnit.equals(strings.getString("unit_teaspoons")))
      return MassVolumeConverter.Unit.TEASPOONS;
    if (localizedUnit.equals(strings.getString("unit_tablespoons")))
      return MassVolumeConverter.Unit.TABLESPOONS;
    if (localizedUnit.equals(strings.getString("unit_fluid_ounces")))
      return MassVolumeConverter.Unit.FLUID_OUNCES;
    if (localizedUnit.equals(strings.getString("unit_cups")))
      return MassVolumeConverter.Unit.CUPS;
    if (localizedUnit.equals(strings.getString("unit_pints")))
      return MassVolumeConverter.Unit.PINTS;
    if (localizedUnit.equals(strings.getString("unit_quarts")))
      return MassVolumeConverter.Unit.QUARTS;
    if (localizedUnit.equals(strings.getString("unit_gallons")))
      return MassVolumeConverter.Unit.GALLONS;
    if (localizedUnit.equals(strings.getString("unit_milliliters")))
      return MassVolumeConverter.Unit.MILLILITERS;
    if (localizedUnit.equals(strings.getString("unit_drams")))
      return MassVolumeConverter.Unit.DRAMS;
    if (localizedUnit.equals(strings.getString("unit_grams")))
      return MassVolumeConverter.Unit.GRAMS;
    if (localizedUnit.equals(strings.getString("unit_ounces")))
      return MassVolumeConverter.Unit.OUNCES;
    if (localizedUnit.equals(strings.getString("unit_pounds")))
      return MassVolumeConverter.Unit.POUNDS;
    if (localizedUnit.equals(strings.getString("unit_liters")))
      return MassVolumeConverter.Unit.LITERS;
    if (localizedUnit.equals(strings.getString("unit_kilograms")))
      return MassVolumeConverter.Unit.KILOGRAMS;

    throw new IllegalArgumentException("Unknown unit: " + localizedUnit);
  }

  /**
   * Reset the window with blank fields.
   * 
   */
  private void resetFields()
  {
    ingredientComboBox.setSelectedIndex(0);
    amountField.setText("");
    unitsComboBox.setSelectedIndex(0);
    caloriesField.setText("");
  }

  /**
   * Method to open files.
   * 
   */
  private void openFiles()
  {
    resetFields();
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogTitle("Chose a file");

    int userSelection = chooser.showOpenDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION)
    {
      File chosen = chooser.getSelectedFile();
      if (chosen.getName().endsWith(".mel"))
      {
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
      }
      else if (chosen.getName().endsWith(".rcp"))
      {
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
      }
      else
      {
        caloriesField.setText("File not permitted");
      }
    }
  }

  /**
   * Main to display window.
   * 
   * @param args
   *          strings
   */
  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> 
    {
      Locale locale = Locale.getDefault(); // Use system default or specified locale
      UnitSystemPreferences.UnitSystem unitSystem = 
          UnitSystemPreferences.getCurrentUnitSystem(); // Get
      CalorieCalculatorWindow window = new CalorieCalculatorWindow(locale, unitSystem); // Pass both
      window.setVisible(true);
    });
  }
}
