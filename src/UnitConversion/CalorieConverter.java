package UnitConversion;

import Information.Ingredient;

public class CalorieConverter
{
  private static final String[] weights = {"drams", "grams", "ounces", "pounds"};
  
  public static double convert(Ingredient ingredient, double amount, String unit) {
    for (int i = 0; i < weights.length; i++)  {
      if (weights[i] == unit.toLowerCase()) {
        return MassUnitConverter.convert(amount, unit, "grams") * (ingredient.getCaloriesPer100g() / 100.0);
      }
    }
    double helper = MassVolumeConverter.convert(amount, unit, "grams", ingredient);
    double result = (ingredient.getCaloriesPer100g() / 100.0) * helper;
    return result;
  }
}
