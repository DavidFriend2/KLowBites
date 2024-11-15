package UnitConversion;

import Information.Ingredient;

import Information.Ingredient;

public class CalorieConverter {
  private static final MassVolumeConverter.Unit[] WEIGHTS  = {
      MassVolumeConverter.Unit.DRAMS, 
      MassVolumeConverter.Unit.GRAMS, 
      MassVolumeConverter.Unit.OUNCES, 
      MassVolumeConverter.Unit.POUNDS
  };

  public static double convert(final Ingredient ingredient, 
      final double amount, final MassVolumeConverter.Unit unit) 
  {
    for (MassVolumeConverter.Unit weight : WEIGHTS) 
    {
      if (weight == unit) 
      {
        return MassUnitConverter.convert(amount, unit, 
              MassVolumeConverter.Unit.GRAMS) * (ingredient.getCaloriesPer100g() / 100.0);
      }
    }
    double helper = MassVolumeConverter.convert(amount, unit, 
        MassVolumeConverter.Unit.GRAMS, ingredient);
    double result = (ingredient.getCaloriesPer100g() / 100.0) * helper;
    return result;
  }
}
