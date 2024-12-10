package gui.UnitConverterListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

//ActionListener for the Ingredient JComboBox since it starts disabled
public class IngredientListener implements ActionListener 
{
  JComboBox<String> from;
  JComboBox<String> to;
  JComboBox<String> ingredient;
  
  //Initializes the attributes needed to change ingredient combo box
  public IngredientListener(final JComboBox<String> from, 
      final JComboBox<String> to, final JComboBox<String> ingredient) 
  {
    this.from = from;
    this.to = to;
    this.ingredient = ingredient;
  }
  
  //Determines if the Ingredients drop down menu should be enabled or disabled
  public void actionPerformed(final ActionEvent e)
  {
    ArrayList<String> massUnits = new ArrayList<>();
    massUnits.add("Pounds");
    massUnits.add("Grams");
    massUnits.add("Drams");
    massUnits.add("Ounces");
    massUnits.add("Kilograms");
    if ((massUnits.contains(from.getSelectedItem()) && !(from.getSelectedItem().
        equals(""))) && !(massUnits.contains(to.
            getSelectedItem())) && !(to.getSelectedItem().equals(""))) 
    {
      ingredient.setEnabled(true);
    }
    else if (massUnits.contains(to.getSelectedItem()) && !(
        to.getSelectedItem().equals("")) && !(massUnits.contains(from.getSelectedItem())) && !(
            from.getSelectedItem().equals(""))) 
    {
      ingredient.setEnabled(true);
    } 
    else 
    {
      ingredient.setEnabled(false);
    }
  }
  
}
