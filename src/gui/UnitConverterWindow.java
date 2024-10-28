package gui;

import javax.swing.*;

import Information.Ingredient;
import UnitConversion.MassUnitConverter;
import UnitConversion.VolumeUnitConverter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jayden S
 * Creates the UnitConverterWindow
 * TODO implement the nec. data
 */
public class UnitConverterWindow extends JFrame {
    
    /*public UnitConverterWindow() {
        setTitle("KiLowBites Unit Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        // Add image buttons to the image panel
        addImageButton(imagePanel, "/img/calculate.png", "Calculate");
        addImageButton(imagePanel, "/img/reset.png", "Rest");

        // Add image panel to the top (NORTH) of the main panel
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        // Add a label to the center of the panel
        JLabel label = new JLabel("TODO", SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addImageButton(JPanel panel, String imagePath, String buttonName) {
        ImageIcon icon = createImageIcon(imagePath);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JButton button = new JButton(new ImageIcon(img));
            button.setPreferredSize(new Dimension(50, 50));
            button.setToolTipText(buttonName);
            panel.add(button);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnitConverterWindow().setVisible(true);
        });
    }*/
  private static final long serialVersionUID = 1L;
  private static final String[] UNITS = 
    {"", "Cups", "Drams", "Fluid Ounces", "Gallons", "Grams", "Milliliters", 
        "Ounces", "Pinches", "Pints", "Pounds", "Quarts", "Tablespoons", "Teaspoons"};
    
  private class CalculatorListener implements ActionListener
  {
    JLabel toAmount;
    JComboBox<String> from;
    JComboBox<String> to;
    JComboBox<String> ingredient;
    JTextField amount;
    
    //Initializes the label
    public CalculatorListener(JLabel toAmount, JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient, JTextField amount) 
    {
      this.toAmount = toAmount;
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
      this.amount = amount;
    }
    
    public void actionPerformed(ActionEvent e) {
      Boolean isNumber = true;
      if(from.getSelectedItem() != "" && to.getSelectedItem() != "" && !(amount.getText().equals(""))) {
        for (int i = 0; i < amount.getText().length(); i++) {
          if (!Character.isDigit(amount.getText().charAt(i))) {
            isNumber = false;
          }
        }
        if (isNumber) {
          ArrayList<String> massUnits = new ArrayList<>();
          massUnits.add("Pounds");
          massUnits.add("Grams");
          massUnits.add("Drams");
          massUnits.add("Ounces");
          if (massUnits.contains(from.getSelectedItem()) && massUnits.contains(to.getSelectedItem())) {
            toAmount.setText("To Amount: " + String.format("%.2f" , MassUnitConverter.convert(Double.parseDouble(amount.getText()), (String) from.getSelectedItem(), 
                (String) to.getSelectedItem())));
          }
          else if (!(massUnits.contains(from.getSelectedItem())) && massUnits.contains(to.getSelectedItem())) {
            toAmount.setText("To Amount: " + String.format("%.2f"));
          }
          else if (massUnits.contains(from.getSelectedItem()) && !(massUnits.contains(to.getSelectedItem()))) {
            toAmount.setText("To Amount: " + String.format("%.2f"));
          }
          else {
            toAmount.setText("To Amount: " + String.format("%.2f", VolumeUnitConverter.convert(Double.parseDouble(amount.getText()), (String) from.getSelectedItem(), 
               (String) to.getSelectedItem())));
          }
        } 
        else {
          toAmount.setText("To Amount: Not a number!");
        }
      }
    }
  }
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
  
  private class IngredientListener implements ActionListener 
  {
    JComboBox<String> from;
    JComboBox<String> to;
    JComboBox<String> ingredient;
    
    public IngredientListener(JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient) {
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
    }
    public void actionPerformed(ActionEvent e)
    {
      ArrayList<String> massUnits = new ArrayList<>();
      massUnits.add("Pounds");
      massUnits.add("Grams");
      massUnits.add("Drams");
      massUnits.add("Ounces");
      if ((massUnits.contains(from.getSelectedItem()) && !(from.getSelectedItem().equals(""))) && !(massUnits.contains(to.getSelectedItem()))) {
        ingredient.setEnabled(true);
      }
      else if (massUnits.contains(to.getSelectedItem()) && !(massUnits.contains(from.getSelectedItem()))) {
        ingredient.setEnabled(true);
      } 
      else {
        ingredient.setEnabled(false);
      }
    }
    
  }
  public UnitConverterWindow() 
  {
    setTitle("KiLowBites Unit Converter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(750, 250);
    
    setLayout(new BorderLayout());
    
    JPanel fromTo = new JPanel(new BorderLayout());
    JPanel from = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromLabel = new JLabel("From Units:");
    from.add(fromLabel);
    
    JComboBox<String> fromDrop = new JComboBox<>(UNITS);
    
    from.add(fromDrop);
    
    
    JPanel to = new JPanel();
    JLabel toLabel = new JLabel("To Units:");
    to.add(toLabel);
    
    JComboBox<String> toDrop = new JComboBox<>(UNITS);
    to.add(toDrop);
    
    from.add(to);
    
    JPanel ingredient = new JPanel();
    JLabel inLabel = new JLabel("Ingredient:");
    ingredient.add(inLabel);

    List<Ingredient> ingred = Ingredient.getIngredients();
    String[] ingredAr = new String[ingred.size() + 1];
    ingredAr[0] = "";
    int count = 1;
    for (Ingredient ingre : ingred) {
      ingredAr[count] = ingre.getName();
      count++;
    }
    JComboBox<String> inDrop = new JComboBox<>(ingredAr);
    inDrop.setEnabled(false);
    ingredient.add(inDrop);
    
    from.add(ingredient);
    from.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
    fromTo.add(from, BorderLayout.NORTH);
    
    JPanel fromAndToAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel fromAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromAmountLabel = new JLabel("From Amount:");
    JTextField text = new JTextField();
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    text.setPreferredSize(new Dimension(110, 20));
    fromAmount.add(fromAmountLabel);
    fromAmount.add(text);
    fromAmount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
    
    JLabel toAmountLabel = new JLabel("To Amount: ______________");
    fromAndToAmount.add(fromAmount);
    fromAndToAmount.add(toAmountLabel);
    
    fromTo.add(fromAndToAmount, BorderLayout.CENTER);
    add(fromTo, BorderLayout.CENTER);
    
    JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton calculator = new JButton("Calculate");
    CalculatorListener listener = new CalculatorListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    calculator.addActionListener(listener);
    temp.add(calculator);

    JButton reset = new JButton("Reset");
    ResetListener rlistener = new ResetListener(toAmountLabel, fromDrop, toDrop, inDrop, text);
    reset.addActionListener(rlistener);
    temp.add(reset);
    
    IngredientListener iListener = new IngredientListener(fromDrop, toDrop, inDrop);
    fromDrop.addActionListener(iListener);
    toDrop.addActionListener(iListener);
    
    add(temp, BorderLayout.NORTH);
  }
  
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      UnitConverter window = new UnitConverter();
      window.setVisible(true);
    });
  }
}