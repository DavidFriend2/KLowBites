package gui;

import javax.swing.*;

import Information.Ingredient;
import UnitConversion.MassUnitConverter;
import UnitConversion.MassVolumeConverter;
import UnitConversion.VolumeUnitConverter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
  
  //Action Listener for the Calculator button
  private class CalculatorListener implements ActionListener
  {
    JLabel toAmount;
    JComboBox<String> from;
    JComboBox<String> to;
    JComboBox<String> ingredient;
    JTextField amount;
    
    public CalculatorListener(JLabel toAmount, JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient, JTextField amount) 
    {
      this.toAmount = toAmount;
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
      this.amount = amount;
    }
    
    //Displays the calculated unit conversion if the user inputs everything correctly
    public void actionPerformed(ActionEvent e) 
    {
      Boolean isNumber = true;
      if(from.getSelectedItem() != "" && to.getSelectedItem() != "" && !(amount.getText().equals(""))) {
        for (int i = 0; i < amount.getText().length(); i++) {
          if (!Character.isDigit(amount.getText().charAt(i))) {
            isNumber = false;
          }
        }
        if (isNumber) 
        {
          ArrayList<String> massUnits = new ArrayList<>();
          massUnits.add("Pounds");
          massUnits.add("Grams");
          massUnits.add("Drams");
          massUnits.add("Ounces");
          if (massUnits.contains(from.getSelectedItem()) && massUnits.contains(to.getSelectedItem())) 
          {
            toAmount.setText("To Amount: " + String.format("%.2f" , MassUnitConverter.convert(Double.parseDouble(amount.getText()), (String) from.getSelectedItem(), 
                (String) to.getSelectedItem())) + " " + to.getSelectedItem());
          }
          else if (!(massUnits.contains(from.getSelectedItem())) && !(massUnits.contains(to.getSelectedItem()))) 
          {
            toAmount.setText("To Amount: " + String.format("%.2f", VolumeUnitConverter.convert(Double.parseDouble(amount.getText()), (String) from.getSelectedItem(), 
                (String) to.getSelectedItem())) + " " + to.getSelectedItem());
          }
          else {
            if (!(ingredient.getSelectedItem().equals(""))) {
              toAmount.setText("To Amount: " + String.format("%.2f", MassVolumeConverter.convert(Double.parseDouble(amount.getText()), (String) from.getSelectedItem(), 
                  (String) to.getSelectedItem(), Ingredient.getIngredientbyName((String) ingredient.getSelectedItem()))) + " " + to.getSelectedItem());
            }
          }
        } 
        else {
          toAmount.setText("To Amount: Not a number!");
        }
      }
    }
  }
  //ActionListener for the reset button
  private class ResetListener implements ActionListener
  {
    JLabel toAmount;
    JComboBox<String> from;
    JComboBox<String> to;
    JComboBox<String> ingredient;
    JTextField amount;
   
    public ResetListener(JLabel toAmount, JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient, JTextField amount) 
    {
      this.toAmount = toAmount;
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
      this.amount = amount;
    }
    
    //Resets the window
    public void actionPerformed(ActionEvent e)
    {
      toAmount.setText("To Amount: ______________");
      from.setSelectedItem(UNITS[0]);
      to.setSelectedItem(UNITS[0]);
      ingredient.setSelectedItem("");
      ingredient.setEnabled(false);
      amount.setText("");
    }
    
  }
  //ActionListener for the Ingredient JComboBox since it starts disabled
  private class IngredientListener implements ActionListener 
  {
    JComboBox<String> from;
    JComboBox<String> to;
    JComboBox<String> ingredient;
    
    //Initializes the attributes needed to change ingredient combo box
    public IngredientListener(JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient) {
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
    }
    
    //Determines if the Ingredients drop down menu should be enabled or disabled
    public void actionPerformed(ActionEvent e)
    {
      ArrayList<String> massUnits = new ArrayList<>();
      massUnits.add("Pounds");
      massUnits.add("Grams");
      massUnits.add("Drams");
      massUnits.add("Ounces");
      if ((massUnits.contains(from.getSelectedItem()) && !(from.getSelectedItem().equals(""))) && !(massUnits.contains(to.getSelectedItem())) && !(to.getSelectedItem().equals(""))) {
        ingredient.setEnabled(true);
      }
      else if (massUnits.contains(to.getSelectedItem()) && !(to.getSelectedItem().equals("")) && !(massUnits.contains(from.getSelectedItem())) && !(from.getSelectedItem().equals(""))) {
        ingredient.setEnabled(true);
      } 
      else {
        ingredient.setEnabled(false);
      }
    }
    
  }
  //Creates the window
  public UnitConverterWindow() 
  {
    //Initializes window
    setTitle("KiLowBites Unit Converter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(750, 250);
    setResizable(false);
    
    setLayout(new BorderLayout());
    
    //Creates the "From Units" Label and the units JComboBox
    JPanel fromTo = new JPanel(new BorderLayout());
    JPanel from = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromLabel = new JLabel("From Units:");
    from.add(fromLabel);
    JComboBox<String> fromDrop = new JComboBox<>(UNITS);
    from.add(fromDrop);
    
    //Creates the "To Units" Label and the units JComboBox
    JPanel to = new JPanel();
    JLabel toLabel = new JLabel("To Units:");
    to.add(toLabel);
    JComboBox<String> toDrop = new JComboBox<>(UNITS);
    to.add(toDrop);
    from.add(to);
    
    //Creates the "Ingredient" label and the ingredients JComboBox
    JPanel ingredient = new JPanel();
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
    JComboBox<String> inDrop = new JComboBox<>(ingredAr);
    //Initializes Ingredients drop down as disabled
    inDrop.setEnabled(false);
    ingredient.add(inDrop);
    from.add(ingredient);
    from.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
    fromTo.add(from, BorderLayout.NORTH);
    
    
    //Creates the "From Amount" label and the text field
    JPanel fromAndToAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel fromAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromAmountLabel = new JLabel("From Amount:");
    JTextField text = new JTextField();
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    text.setPreferredSize(new Dimension(110, 20));
    fromAmount.add(fromAmountLabel);
    fromAmount.add(text);
    fromAmount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
    
    //Creates the "To Amount" label
    JLabel toAmountLabel = new JLabel("To Amount: ______________");
    fromAndToAmount.add(fromAmount);
    fromAndToAmount.add(toAmountLabel);
    
    fromTo.add(fromAndToAmount, BorderLayout.CENTER);
    add(fromTo, BorderLayout.CENTER);
    
    //Creates the Calculate button and adds listener
    JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
    ImageIcon icon = createImageIcon("/img/calculate.png");
    Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton calculator = new JButton(new ImageIcon(img));
    calculator.setPreferredSize(new Dimension(50, 50));
    calculator.setToolTipText("Calculate");
    CalculatorListener listener = new CalculatorListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    calculator.addActionListener(listener);
    temp.add(calculator);

    //Creates the Reset Button and adds listener
    ImageIcon icon2 = createImageIcon("/img/reset.png");
    Image img2 = icon2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    JButton reset = new JButton(new ImageIcon(img2));
    reset.setPreferredSize(new Dimension(50, 50));
    reset.setToolTipText("Reset");
    ResetListener rlistener = new ResetListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    reset.addActionListener(rlistener);
    temp.add(reset);
    
    //Adds the listener to the From and To JComboBox's based on mass to vol or vol to mass conversions
    IngredientListener iListener = new IngredientListener(fromDrop, toDrop, inDrop);
    fromDrop.addActionListener(iListener);
    toDrop.addActionListener(iListener);
    
    //Adds everything to the gui
    add(temp, BorderLayout.NORTH);
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