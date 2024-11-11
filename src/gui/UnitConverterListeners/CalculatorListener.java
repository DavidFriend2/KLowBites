package gui.UnitConverterListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Information.Ingredient;
import UnitConversion.MassUnitConverter;
import UnitConversion.MassVolumeConverter;
import UnitConversion.VolumeUnitConverter;

// Action Listener for the Calculator button
public class CalculatorListener implements ActionListener {
  
  private JLabel toAmount;
  private JComboBox<String> from;
  private JComboBox<String> to;
  private JComboBox<String> ingredient;
  private JTextField amount;
  private Locale currentLocale;
  private ResourceBundle strings;

  public CalculatorListener(JLabel toAmount, JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient, JTextField amount, Locale locale) {
      this.toAmount = toAmount;
      this.from = from;
      this.to = to;
      this.ingredient = ingredient;
      this.amount = amount;
      this.currentLocale = locale;
      this.strings = ResourceBundle.getBundle("resources.Strings", locale);
  }

  // Displays the calculated unit conversion if the user inputs everything correctly
  public void actionPerformed(ActionEvent e) {
      if (!(from.getSelectedItem().equals("")) && !(to.getSelectedItem().equals("")) && !(amount.getText().isEmpty())) {
          try {
              NumberFormat numberFormat = NumberFormat.getInstance(currentLocale);
              Number parsedAmount = numberFormat.parse(amount.getText());
              double amountValue = parsedAmount.doubleValue();

              // Check for negative values
              if (amountValue < 0) {
                  toAmount.setText(strings.getString("to_amount_label") + ": " + strings.getString("not_a_positive_number"));
                  return; // Exit if negative
              }

              ArrayList<String> massUnits = new ArrayList<>();
              massUnits.add(strings.getString("unit_pounds"));
              massUnits.add(strings.getString("unit_grams"));
              massUnits.add(strings.getString("unit_drams"));
              massUnits.add(strings.getString("unit_ounces"));

              String result;

              if (massUnits.contains(from.getSelectedItem()) && massUnits.contains(to.getSelectedItem())) {
                  result = numberFormat.format(MassUnitConverter.convert(amountValue, (String) from.getSelectedItem(), (String) to.getSelectedItem()));
              } else if (!(massUnits.contains(from.getSelectedItem())) && !(massUnits.contains(to.getSelectedItem()))) {
                  result = numberFormat.format(VolumeUnitConverter.convert(amountValue, (String) from.getSelectedItem(), (String) to.getSelectedItem()));
              } else {
                  if (!(ingredient.getSelectedItem().equals(""))) {
                      result = numberFormat.format(MassVolumeConverter.convert(amountValue, (String) from.getSelectedItem(), 
                          (String) to.getSelectedItem(), Ingredient.getIngredientbyName((String) ingredient.getSelectedItem())));
                  } else {
                      toAmount.setText(strings.getString("to_amount_label") + ": " + strings.getString("ingredient_required"));
                      return; // Exit if no ingredient is selected
                  }
              }

              toAmount.setText(strings.getString("to_amount_label") + ": " + result + " " + to.getSelectedItem());
          } catch (ParseException ex) {
              toAmount.setText(strings.getString("to_amount_label") + ": " + strings.getString("not_a_number"));
          }
      }
  }

  public void updateLocale(Locale newLocale) {
      this.currentLocale = newLocale;
      this.strings = ResourceBundle.getBundle("resources.Strings", newLocale);
  }
}