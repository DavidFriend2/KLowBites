package UnitConversion;

import Information.Ingredient;

import Information.Ingredient;

public class CalorieConverter {
    private static final MassVolumeConverter.Unit[] weights = {
        MassVolumeConverter.Unit.DRAMS, 
        MassVolumeConverter.Unit.GRAMS, 
        MassVolumeConverter.Unit.OUNCES, 
        MassVolumeConverter.Unit.POUNDS
    };
  
    public static double convert(Ingredient ingredient, double amount, MassVolumeConverter.Unit unit) {
        for (MassVolumeConverter.Unit weight : weights) {
            if (weight == unit) {
                return MassUnitConverter.convert(amount, unit, MassVolumeConverter.Unit.GRAMS) * (ingredient.getCaloriesPer100g() / 100.0);
            }
        }
        double helper = MassVolumeConverter.convert(amount, unit, MassVolumeConverter.Unit.GRAMS, ingredient);
        double result = (ingredient.getCaloriesPer100g() / 100.0) * helper;
        return result;
    }
}
