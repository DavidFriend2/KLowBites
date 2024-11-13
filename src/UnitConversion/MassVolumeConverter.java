package UnitConversion;

import Information.Ingredient;

/**
 * This class provides methods for converting between mass and volume units for ingredients.
 */
public class MassVolumeConverter {

    /**
     * Enum representing different units of measurement for mass and volume.
     */
    public enum Unit {
        CUPS, DRAMS, FLUID_OUNCES, GALLONS, GRAMS, MILLILITERS, 
        OUNCES, PINCHES, PINTS, POUNDS, QUARTS, TABLESPOONS, TEASPOONS
    }

    /**
     * Array of units considered as weights/mass units.
     */
    private static final Unit[] weights = {Unit.GRAMS, Unit.DRAMS, Unit.OUNCES, Unit.POUNDS};

    /**
     * Converts a value from one unit to another for a specific ingredient.
     * 
     * @param value The numeric value to convert
     * @param from The unit to convert from
     * @param to The unit to convert to
     * @param ingredient The ingredient for which the conversion is being performed
     * @return The converted value
     */
    public static double convert(double value, Unit from, Unit to, Ingredient ingredient) {
        for (Unit weight : weights) {
            if (weight == from) {
                return massToVolume(value, from, to, ingredient);
            }
        }
        return volumeToMass(value, from, to, ingredient);
    }
 
    /**
     * Converts a mass value to a volume value for a specific ingredient.
     * 
     * @param value The numeric value to convert
     * @param from The mass unit to convert from
     * @param to The volume unit to convert to
     * @param ingredient The ingredient for which the conversion is being performed
     * @return The converted value
     */
    public static double massToVolume(double value, Unit from, Unit to, Ingredient ingredient) {
        double helper = MassUnitConverter.convert(value, from, Unit.GRAMS);
        helper = helper / ingredient.getGramsPerM(); // Uses density for conversion
        return VolumeUnitConverter.convert(helper, Unit.MILLILITERS, to);
    }
  
    /**
     * Converts a volume value to a mass value for a specific ingredient.
     * 
     * @param value The numeric value to convert
     * @param from The volume unit to convert from
     * @param to The mass unit to convert to
     * @param ingredient The ingredient for which the conversion is being performed
     * @return The converted value
     */
    public static double volumeToMass(double value, Unit from, Unit to, Ingredient ingredient) {
        double helper = VolumeUnitConverter.convert(value, from, Unit.MILLILITERS);
        helper = helper * ingredient.getGramsPerM(); // Uses density for conversion
        return MassUnitConverter.convert(helper, Unit.GRAMS, to);
    }
  
    /**
     * Returns an array of all available units.
     * 
     * @return An array containing all units defined in the Unit enum
     */
    public static Unit[] getUnits() {
        return Unit.values();
    }

    /**
     * Main method for testing the converter.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Ingredient apple = Ingredient.getIngredientbyName("Apple");
        double result = convert(120000, Unit.DRAMS, Unit.CUPS, apple);
        System.out.println(result);
    }
}