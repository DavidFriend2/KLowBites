package UnitConversion;

import Information.Ingredient;

public class CalorieConverter
{
  private static final String[] weights = {"drams", "grams", "ounces", "pounds"};
  
  public static double convert(Ingredient ingredient, double amount, String unit) {
    for (int i = 0; i < weights.length; i++)  {
      if (weights[i] == unit) {
        System.out.println("woohoo");
        System.out.println(MassUnitConverter.convert(amount, unit, "grams"));
        System.out.println(ingredient.getCaloriesPer100g());
        System.out.println(ingredient.getCaloriesPer100g() / 100.0);
        System.out.println();
        return MassUnitConverter.convert(amount, unit, "grams") * (ingredient.getCaloriesPer100g() / 100.0);
      }
    }
    double helper = MassVolumeConverter.convert(amount, unit, "grams", ingredient);
    double result = (ingredient.getCaloriesPer100g() / 100.0) * helper;
    return result;
  }
}
