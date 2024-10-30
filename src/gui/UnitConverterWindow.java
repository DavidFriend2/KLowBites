package gui;

import javax.swing.*;


import Information.Ingredient;
import gui.UnitConverterListeners.*;
import java.awt.*;
import java.util.List;

/**
 * @author David Friend
 * Creates the UnitConverterWindow
 */
public class UnitConverterWindow extends JFrame 
{
  private static final long serialVersionUID = 1L;
  private static final String[] UNITS = 
  {"", "Cups", "Drams", "Fluid Ounces", "Gallons", "Grams", "Milliliters", 
        "Ounces", "Pinches", "Pints", "Pounds", "Quarts", "Tablespoons", "Teaspoons"};
  
  private JPanel fromTo;
  private JPanel from;
  private JPanel to;
  private JPanel ingredient;
  private JPanel fromAndToAmount;
  private JPanel fromAmount;
  private JPanel temp;
  private JComboBox<String>fromDrop;
  private JComboBox<String> toDrop;
  private JComboBox<String> inDrop;
  private JTextField text;
  private JLabel toAmountLabel;
  
  public UnitConverterWindow() 
  {
    initializeWindow();
    createPanels();
    addComponents();
    createActionDependentComponents();
    finish();
  }
  private void initializeWindow() {
    setTitle("KiLowBites Unit Converter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
  
  private void addComponents() {
    addFromToComponents();
    addIngredientComponents();
    addFromAmountComponents();
  }
  
  private void createActionDependentComponents() {
    createToAmountLabel();
    createCalculateButton();
    createResetButton();
  }
  
  private void finish() {
    addListeners();
    add(temp, BorderLayout.NORTH);
  }
  
  private void addFromToComponents() {
    JLabel fromLabel = new JLabel("From Units:");
    from.add(fromLabel);
    fromDrop = new JComboBox<>(UNITS);
    from.add(fromDrop);
    
    JLabel toLabel = new JLabel("To Units:");
    to.add(toLabel);
    toDrop = new JComboBox<>(UNITS);
    to.add(toDrop);
    from.add(to);
  }
  
  private void addIngredientComponents() {
    JLabel inLabel = new JLabel("Ingredient:");
    ingredient.add(inLabel);
    //Gets the ingredients from the ingredients file
    List<Ingredient> ingred = Ingredient.getIngredients();
    String[] ingredAr = new String[ingred.size() + 1];
    ingredAr[0] = "";
    int count = 1;
    for (Ingredient ingre : ingred) {
      ingredAr[count] = ingre.getName();
      count++;
    }
    inDrop = new JComboBox<>(ingredAr);
    //Initializes Ingredients drop down as disabled
    inDrop.setEnabled(false);
    ingredient.add(inDrop);
    from.add(ingredient);
    from.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
    fromTo.add(from, BorderLayout.NORTH);
  }
  
  private void addFromAmountComponents() {
    JLabel fromAmountLabel = new JLabel("From Amount:");
    text = new JTextField();
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    text.setPreferredSize(new Dimension(110, 20));
    fromAmount.add(fromAmountLabel);
    fromAmount.add(text);
    fromAmount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
  }
  
  private void createToAmountLabel() {
    toAmountLabel = new JLabel("To Amount: ______________");
    fromAndToAmount.add(fromAmount);
    fromAndToAmount.add(toAmountLabel);
    
    fromTo.add(fromAndToAmount, BorderLayout.CENTER);
    add(fromTo, BorderLayout.CENTER);
  }
  
  private void createCalculateButton() {
    ImageIcon icon = createImageIcon("/img/calculate.png");
    Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton calculator = new JButton(new ImageIcon(img));
    calculator.setPreferredSize(new Dimension(50, 50));
    calculator.setToolTipText("Calculate");
    CalculatorListener listener = new CalculatorListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    calculator.addActionListener(listener);
    temp.add(calculator);
  }
  
  private void createResetButton() {
    ImageIcon icon2 = createImageIcon("/img/reset.png");
    Image img2 = icon2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton reset = new JButton(new ImageIcon(img2));
    reset.setPreferredSize(new Dimension(50, 50));
    reset.setToolTipText("Reset");
    ResetListener rlistener = new ResetListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    reset.addActionListener(rlistener);
    temp.add(reset);
  }
  
  private void addListeners() {
    IngredientListener iListener = new IngredientListener(fromDrop, toDrop, inDrop);
    fromDrop.addActionListener(iListener);
    toDrop.addActionListener(iListener);
  }
  
  private ImageIcon createImageIcon(String path) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
  }
  
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      UnitConverterWindow window = new UnitConverterWindow();
      window.setVisible(true);
    });
  }
}