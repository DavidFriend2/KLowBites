package UnitConversion;

/**
 * @author David Friend
 * Mass Unit Converter
 */
public class MassUnitConverter {
  private static final String GRAMS = "grams";
  private static final String OUNCES = "ounces";
  private static final String POUNDS = "pounds";
  private static final String DRAMS = "drams";
  
  //Converts a value from a unit to another
  public static double convert(double value, String from, String to) {
    double result = toOunces(value, from.toLowerCase());
    return fromOunces(result, to.toLowerCase());
  }
  
  //Turns the value into ounces
  private static double toOunces(double value, String from) {
    if (from.equals(OUNCES)) {
      return value;
    }
    if (from.equals(GRAMS)) {
      return value / 28.35;
    }
    if (from.equals(DRAMS)) {
      return value / 16;
    }
    return value * 16;

  }
  
  //Turns the ounces into the unit wanted
  private static double fromOunces(double value, String to) {
    if (to.equals(OUNCES)) {
      return value;
    }
    if (to.equals(GRAMS)) {
      return value * 28.3495231;
    }
    if (to.equals(DRAMS)) {
      return value * 16;
    }
    return value / 16;
  }

}
