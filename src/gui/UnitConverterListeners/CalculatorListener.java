package gui.UnitConverterListeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Information.Ingredient;
import UnitConversion.MassUnitConverter;
import UnitConversion.MassVolumeConverter;
import UnitConversion.VolumeUnitConverter;

//Action Listener for the Calculator button
public class CalculatorListener implements ActionListener
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
    int count = 0;
    if(!(from.getSelectedItem().equals("")) && !(to.getSelectedItem().equals("")) && !(amount.getText().equals(""))) {
      for (int i = 0; i < amount.getText().length(); i++) {
        if (!Character.isDigit(amount.getText().charAt(i))) {
          if (!(amount.getText().charAt(i) == '.')) {
            isNumber = false;
          } 
          else if (count != 0)
          {
            isNumber = false;
          } 
          else 
          {
            count++;
          }
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
            toAmount.setText("To Amount: " + String.format("%.2f", MassVolumeConverter.convert(Double.parseDouble(amount.getText()), ((String) from.getSelectedItem()), 
                ((String) to.getSelectedItem()), Ingredient.getIngredientbyName((String) ingredient.getSelectedItem()))) + " " + to.getSelectedItem());
          }
        }
      } 
      else {
        toAmount.setText("To Amount: Not a number!");
      }
    }
  }
}
