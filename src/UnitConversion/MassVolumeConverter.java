package UnitConversion;

import Information.Ingredient;

public class MassVolumeConverter
{
  private static final String[] weights = {"grams", "drams", "ounces", "pounds"};


  public static double convert(double value, String from, String to, Ingredient ingredient) {
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] == from) {
        return massToVolume(value, from, to, ingredient);
      }
    }
    return volumeToMass(value, from, to, ingredient);
  }

  public static double massToVolume(double value, String from, String to, Ingredient ingredient) {
    double helper = MassUnitConverter.convert(value, from, "grams");
    helper = helper / ingredient.getGramsPerM(); // Uses density for conversion
    double result = VolumeUnitConverter.convert(helper, "milliliters", to);
    return result;
  }
  
  public static double volumeToMass(double value, String from, String to, Ingredient ingredient) {
    double helper = VolumeUnitConverter.convert(value, from, "milliliters");
    helper = helper * ingredient.getGramsPerM(); // Uses density for conversion
    double result = MassUnitConverter.convert(helper, "grams", to);
    return result;
  }
}
