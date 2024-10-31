package UnitConversion;

import Information.Ingredient;

public class MassVolumeConverter
{
  private static final String[] weights = {"grams", "drams", "ounces", "pounds"};

  public static void main(String[] args) {
    Ingredient apple = Ingredient.getIngredientbyName("Apple");
    double dave = convert(120000, "Drams", "Cups", apple);
    System.out.println(dave);
  }
  
  public static double convert(double value, String from, String to, Ingredient ingredient) {
    for (int i = 0; i < weights.length; i++) {
      if (weights[i].equals(from.toLowerCase())) {
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
  
  public static String[] getUnits() {
    String[] temp = {"Cups", "Drams", "Fluid Ounces", "Gallons", "Grams", "Milliliters", 
        "Ounces", "Pinches", "Pints", "Pounds", "Quarts", "Tablespoons", "Teaspoons"};
    return temp;
  }
}
