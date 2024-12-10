package gui.UnitConverterListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Information.Ingredient;
import UnitConversion.MassUnitConverter;
import UnitConversion.MassVolumeConverter;
import UnitConversion.VolumeUnitConverter;

public class CalculatorListener implements ActionListener 
{
  
  private JLabel toAmount;
  private JComboBox<String> from;
  private JComboBox<String> to;
  private JComboBox<String> ingredient;
  private JTextField amount;
  private Locale currentLocale;
  private ResourceBundle strings;

  public CalculatorListener(final JLabel toAmount, final JComboBox<String> from, 
      final JComboBox<String> to, final JComboBox<String> ingredient, 
      final JTextField amount, final Locale locale) 
  {
    this.toAmount = toAmount;
    this.from = from;
    this.to = to;
    this.ingredient = ingredient;
    this.amount = amount;
    this.currentLocale = locale;
    this.strings = ResourceBundle.getBundle("resources.Strings", locale);
  }

  public void actionPerformed(final ActionEvent e) 
  {
    if (!(from.getSelectedItem().equals("")) && !(to.getSelectedItem()
        .equals("")) && !(amount.getText().isEmpty())) 
    {
      try 
      {
        NumberFormat numberFormat = NumberFormat.getInstance(currentLocale);
        Number parsedAmount = numberFormat.parse(amount.getText());
        double amountValue = parsedAmount.doubleValue();

        if (amountValue < 0) 
        {
          toAmount.setText(strings.getString("to_amount_label") + ": " 
              + strings.getString("not_a_positive_number"));
          return;
        }

        MassVolumeConverter.Unit fromUnit = stringToUnit((String) from.getSelectedItem());
        MassVolumeConverter.Unit toUnit = stringToUnit((String) to.getSelectedItem());

        String result;

        if (isMassUnit(fromUnit) && isMassUnit(toUnit)) 
        {
          result = numberFormat.format(MassUnitConverter.convert(amountValue, fromUnit, toUnit));
        } else if (!isMassUnit(fromUnit) && !isMassUnit(toUnit)) 
        {
          result = numberFormat.format(VolumeUnitConverter.
                convert(amountValue, fromUnit, toUnit));
        } else 
        {
          if (!(ingredient.getSelectedItem().equals(""))) 
          {
            Ingredient selectedIngredient = Ingredient.getIngredientbyName((
                String) ingredient.getSelectedItem());
            result = numberFormat.format(MassVolumeConverter.convert(amountValue, 
                fromUnit, toUnit, selectedIngredient));
          } else 
          {
            toAmount.setText(strings.getString("to_amount_label") 
                + ": " + strings.getString("ingredient_required"));
            return;
          }
        }

        toAmount.setText(strings.getString("to_amount_label") 
            + ": " + result + " " + to.getSelectedItem());
      } 
      catch (ParseException ex) 
      {
        toAmount.setText(strings.getString("to_amount_label") 
            + ": " + strings.getString("not_a_number"));
      }
    }
  }

  private MassVolumeConverter.Unit stringToUnit(final String unitStr) 
  {
    if (unitStr.equals(strings.getString("unit_pinches"))) return MassVolumeConverter.Unit.PINCHES;
    if (unitStr.equals(strings.getString("unit_teaspoons"))) 
      return MassVolumeConverter.Unit.TEASPOONS;
    if (unitStr.equals(strings.getString("unit_tablespoons"))) 
      return MassVolumeConverter.Unit.TABLESPOONS;
    if (unitStr.equals(strings.getString("unit_fluid_ounces"))) 
      return MassVolumeConverter.Unit.FLUID_OUNCES;
    if (unitStr.equals(strings.getString("unit_cups"))) return MassVolumeConverter.Unit.CUPS;
    if (unitStr.equals(strings.getString("unit_pints"))) return MassVolumeConverter.Unit.PINTS;
    if (unitStr.equals(strings.getString("unit_quarts"))) return MassVolumeConverter.Unit.QUARTS;
    if (unitStr.equals(strings.getString("unit_gallons"))) return MassVolumeConverter.Unit.GALLONS;
    if (unitStr.equals(strings.getString("unit_milliliters"))) 
      return MassVolumeConverter.Unit.MILLILITERS;
    if (unitStr.equals(strings.getString("unit_liters"))) 
      return MassVolumeConverter.Unit.LITERS;
    if (unitStr.equals(strings.getString("unit_kilograms"))) 
      return MassVolumeConverter.Unit.KILOGRAMS;
    if (unitStr.equals(strings.getString("unit_drams"))) return MassVolumeConverter.Unit.DRAMS;
    if (unitStr.equals(strings.getString("unit_grams"))) return MassVolumeConverter.Unit.GRAMS;
    if (unitStr.equals(strings.getString("unit_ounces"))) return MassVolumeConverter.Unit.OUNCES;
    if (unitStr.equals(strings.getString("unit_pounds"))) return MassVolumeConverter.Unit.POUNDS;
    throw new IllegalArgumentException("Unknown unit: " + unitStr);
  }

  private boolean isMassUnit(final MassVolumeConverter.Unit unit) 
  {
    List<MassVolumeConverter.Unit> massUnits = Arrays.asList(
        MassVolumeConverter.Unit.POUNDS,
        MassVolumeConverter.Unit.GRAMS,
        MassVolumeConverter.Unit.DRAMS,
        MassVolumeConverter.Unit.OUNCES,
        MassVolumeConverter.Unit.KILOGRAMS
    );
    return massUnits.contains(unit);
  }

  public void updateLocale(final Locale newLocale) 
  {
    this.currentLocale = newLocale;
    this.strings = ResourceBundle.getBundle("resources.Strings", newLocale);
  }
}
