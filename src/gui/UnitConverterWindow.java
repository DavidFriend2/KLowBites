package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import Information.Ingredient;
import gui.UnitConverterListeners.*;

/**
 * Unit converter window.
 * 
 * @author David Friend, Jayden Smith
 */
public class UnitConverterWindow extends JFrame 
{
  private static final long serialVersionUID = 1L;
  
  private JPanel fromTo;
  private JPanel from;
  private JPanel to;
  private JPanel ingredient;
  private JPanel fromAndToAmount;
  private JPanel fromAmount;
  private JPanel temp;
  private JComboBox<String> fromDrop;
  private JComboBox<String> toDrop;
  private JComboBox<String> inDrop;
  private JTextField text;
  private JLabel toAmountLabel;
  
  private ResourceBundle strings;
  private Locale currentLocale;
  
  /**
   * Default constructor.
   * 
   * @param locale
   * @param unitSystem
   */
  public UnitConverterWindow(final Locale locale, 
      final UnitSystemPreferences.UnitSystem unitSystem) 
  {
    this.currentLocale = locale;
    loadStrings(locale);
    initializeWindow();
    createPanels();
    addComponents(unitSystem);
    createActionDependentComponents();
    finish();
  }
  
  private void loadStrings(final Locale locale) 
  {
    try 
    {
      strings = ResourceBundle.getBundle("resources.Strings", locale);
    } catch (MissingResourceException e) 
    {
      System.err.println("Could not find resources.Strings for locale "
          + locale + ": " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  private void initializeWindow() 
  {
    setTitle(strings.getString("unit_converter_title"));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(750, 250);
    setResizable(false);
    setLayout(new BorderLayout());
  }
  
  private void createPanels() 
  {
    fromTo = new JPanel(new BorderLayout());
    from = new JPanel(new FlowLayout(FlowLayout.LEFT));
    to = new JPanel();
    ingredient = new JPanel();
    fromAndToAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fromAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
  }
  
  private void addComponents(final UnitSystemPreferences.UnitSystem unitSystem) 
  {
    addFromToComponents(unitSystem);
    addIngredientComponents();
    addFromAmountComponents();
  }
  
  private void createActionDependentComponents() 
  {
    createToAmountLabel();
    createCalculateButton();
    createResetButton();
  }
  
  private void finish() 
  {
    addListeners();
    add(temp, BorderLayout.NORTH);
  }
  
  /**
   * Updates units.
   * 
   * @param unitSystem
   */
  public void updateUnits(final UnitSystemPreferences.UnitSystem unitSystem) 
  {
    String[] units = UnitSystemPreferences.getUnitsForCurrentSystem(strings);
    updateComboBox(fromDrop, units);
    updateComboBox(toDrop, units);
    revalidate();
    repaint();
  }
  
  private void addFromToComponents(final UnitSystemPreferences.UnitSystem unitSystem) 
  {
    JLabel fromLabel = new JLabel(strings.getString("from_units_label"));
    from.add(fromLabel);
    fromDrop = new JComboBox<>(UnitSystemPreferences.getUnitsForCurrentSystem(strings));
    from.add(fromDrop);
    
    JLabel toLabel = new JLabel(strings.getString("to_units_label"));
    to.add(toLabel);
    toDrop = new JComboBox<>(UnitSystemPreferences.getUnitsForCurrentSystem(strings));
    to.add(toDrop);
    from.add(to);
  }
  
  private String[] getLocalizedUnits() 
  {
    return new String[] {
        "", 
      strings.getString("unit_cups"),
      strings.getString("unit_drams"),
      strings.getString("unit_fluid_ounces"),
      strings.getString("unit_gallons"),
      strings.getString("unit_grams"),
      strings.getString("unit_milliliters"),
      strings.getString("unit_ounces"),
      strings.getString("unit_pinches"),
      strings.getString("unit_pints"),
      strings.getString("unit_pounds"),
      strings.getString("unit_quarts"),
      strings.getString("unit_tablespoons"),
      strings.getString("unit_teaspoons")
    };
  }
  
  private void addIngredientComponents() 
  {
    try 
    {
      
      Ingredient.setIngredients(Ingredient.loadIngredients("ingredients.ntr"));
      JLabel inLabel = new JLabel(strings.getString("ingredient_label"));
      ingredient.add(inLabel);
      List<Ingredient> ingred = Ingredient.getIngredients();
      ingred.sort(Comparator.comparing(Ingredient::getName));
      String[] ingredAr = new String[ingred.size() + 1];
      ingredAr[0] = "";
      int count = 1;
      for (Ingredient ingre : ingred) 
      {
        ingredAr[count] = ingre.getName();
        count++;
      }
      inDrop = new JComboBox<>(ingredAr);
      inDrop.setEnabled(false);
      ingredient.add(inDrop);
      from.add(ingredient);
      from.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
      fromTo.add(from, BorderLayout.NORTH);
      
    }
    catch(IOException | ClassNotFoundException ex) 
    {
      System.out.println("Couldnt load ingredients");
    }
    
  }
  
  private void addFromAmountComponents() 
  {
    JLabel fromAmountLabel = new JLabel(strings.getString("from_amount_label"));
    text = new JTextField();
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    text.setPreferredSize(new Dimension(110, 20));
    fromAmount.add(fromAmountLabel);
    fromAmount.add(text);
    fromAmount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
  }
  
  private void createToAmountLabel() 
  {
    toAmountLabel = new JLabel(strings.getString("to_amount_label") + " ______________");
    fromAndToAmount.add(fromAmount);
    fromAndToAmount.add(toAmountLabel);
    
    fromTo.add(fromAndToAmount, BorderLayout.CENTER);
    add(fromTo, BorderLayout.CENTER);
  }
  
  private void addListeners() 
  {
    IngredientListener iListener = new IngredientListener(fromDrop, toDrop, inDrop);
    fromDrop.addActionListener(iListener);
    toDrop.addActionListener(iListener);
  }

  private void createCalculateButton() 
  {
    ImageIcon icon = createImageIcon("/img/calculate.png");
    Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton calculator = new JButton(new ImageIcon(img));
    calculator.setPreferredSize(new Dimension(50, 50));
    calculator.setToolTipText(strings.getString("calculate_tooltip"));
    CalculatorListener listener = new CalculatorListener(toAmountLabel, 
        fromDrop, toDrop, inDrop, text, currentLocale);
    calculator.addActionListener(listener);
    temp.add(calculator);
  }

  private void createResetButton() 
  {
    ImageIcon icon2 = createImageIcon("/img/reset.png");
    Image img2 = icon2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton reset = new JButton(new ImageIcon(img2));
    reset.setPreferredSize(new Dimension(50, 50));
    reset.setToolTipText(strings.getString("reset_tooltip"));
    ResetListener rlistener = new ResetListener(toAmountLabel, 
        fromDrop, toDrop, inDrop, text, currentLocale);
    reset.addActionListener(rlistener);
    temp.add(reset);
  }
  
  
  private ImageIcon createImageIcon(final String path) 
  {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) 
    {
      return new ImageIcon(imgURL);
    } 
    else 
    {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
  /**
   * Updates language.
   * 
   * @param newLocale
   */
  public void updateLanguage(final Locale newLocale) 
  {
    loadStrings(newLocale);
    updateComponentTexts();
    // Update listeners with new locale
    ((CalculatorListener)((JButton)temp.getComponent(0)).
        getActionListeners()[0]).updateLocale(newLocale);
    ((ResetListener)((JButton)temp.getComponent(1)).
        getActionListeners()[0]).updateLocale(newLocale);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
  

  private void updateComponentTexts() 
  {
    setTitle(strings.getString("unit_converter_title"));
    // Update all labels
    ((JLabel)from.getComponent(0)).setText(strings.getString("from_units_label"));
    ((JLabel)to.getComponent(0)).setText(strings.getString("to_units_label"));
    ((JLabel)ingredient.getComponent(0)).setText(strings.getString("ingredient_label"));
    ((JLabel)fromAmount.getComponent(0)).setText(strings.getString("from_amount_label"));
    toAmountLabel.setText(strings.getString("to_amount_label") + " ______________");
    
    String[] units = UnitSystemPreferences.getUnitsForCurrentSystem(strings);
    updateComboBox(fromDrop, units);
    updateComboBox(toDrop, units);
    
    // Update comboboxes
    updateComboBox(fromDrop, getLocalizedUnits());
    updateComboBox(toDrop, getLocalizedUnits());
    
    // Update button tooltips
    ((JButton)temp.getComponent(0)).setToolTipText(strings.getString("calculate_tooltip"));
    ((JButton)temp.getComponent(1)).setToolTipText(strings.getString("reset_tooltip"));
    
    // Refresh the layout
    revalidate();
    repaint();
  }
  
  

  private void updateComboBox(final JComboBox<String> comboBox, final String[] newItems) 
  {
    String selectedItem = (String) comboBox.getSelectedItem();
    comboBox.removeAllItems();
    for (String item : newItems) 
    {
      comboBox.addItem(item);
    }
    comboBox.setSelectedItem(selectedItem);
  }
  
  /**
   * Main Method.
   * 
   * @param args
   */
  public static void main(final String[] args) 
  {
    SwingUtilities.invokeLater(() -> 
    {
      Locale locale = Locale.getDefault();
      UnitSystemPreferences.UnitSystem unitSystem = UnitSystemPreferences.getCurrentUnitSystem();
      UnitConverterWindow window = new UnitConverterWindow(locale, unitSystem);
      window.setVisible(true);
    });
  }
}
