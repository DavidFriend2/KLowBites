package UnitConversion;

import java.util.ArrayList;

import Information.Ingredient;

/**
 * @author David Friend
 * Calorie Converter
 */
public class CalorieConverter
{
  //Converts an amount of an ingredient to its calories
  public static double convert(Ingredient ingredient, double amount, String units) {
    return toGrams(ingredient, amount, units) * ingredient.getCaloriesPer100g() / 100;
  }
  
  //Converts the amount to grams so calories are easy to get
  private static double toGrams(Ingredient ingredient, double amount, String units) {
    ArrayList<String> massUnits = new ArrayList<>();
    massUnits.add("ounces");
    massUnits.add("drams");
    massUnits.add("grams");
    massUnits.add("pounds");
    if (massUnits.contains(units)) {
      return MassUnitConverter.convert(amount, units, "grams");
    }
    return MassVolumeConverter.convert(amount, units, "grams", ingredient);
  }
}
