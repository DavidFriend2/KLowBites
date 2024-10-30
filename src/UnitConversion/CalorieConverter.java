package UnitConversion;

import Information.Ingredient;

public class CalorieConverter
{
  private static final String[] weights = {"drams", "grams", "ounces", "pounds"};
  
  public static double convert(Ingredient ingredient, double amount, String unit) {
    double result = 0.0;
    for (String weight : weights) {
      if (weight == unit) {
        double helper = MassUnitConverter.convert(amount, unit, "grams");
        result = (ingredient.getCaloriesPer100g() / 100) * helper;
        return result;
      }
    }
    double helper = MassVolumeConverter.convert(amount, unit, "grams", ingredient);
    result = (ingredient.getCaloriesPer100g() / 100) * helper;
    return result;
  }
  
}
