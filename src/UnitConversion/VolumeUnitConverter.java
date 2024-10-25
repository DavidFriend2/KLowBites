package UnitConversion;

public class VolumeUnitConverter
{
  private static final String P = "pinches";
  private static final String TSP = "teaspoon";
  private static final String TBS = "tablespoon";
  private static final String FLOZ = "fluid ounces";
  private static final String CUP = "cup";
  private static final String PT = "pint";
  private static final String QT = "quart";
  private static final String GAL = "gallon";
  private static final String ML = "milliliters";
  
  
  public static double convert(double value, String from, String to) {
    double result = toFluidOunce(value, from.toLowerCase());
    return fromFluidOunce(result, to.toLowerCase());
  }
  
  public static double toFluidOunce(double value, String from) {
    double result = 0.0;
    if (from.equals(P)) {
      result = ((value / 16) / 3) / 2;
    }
    if (from.equals(TSP)) {
      result = (value / 3) / 2;
    }
    if (from.equals(TBS)) {
      result = value / 2;
    }
    if (from.equals(CUP)) {
      result = value * 8;
    }
    if (from.equals(PT)) {
      result = value * 16;
    }
    if (from.equals(QT)) {
      result = value * 32;
    }
    if (from.equals(GAL)) {
      result = value * 128;
    }
    if (from.equals(ML)) {
      result = value / 29.57353;
    }
    if (from.equals(FLOZ)) {
      result = value;
    }
    return result;
  }
  
  
  public static final double fromFluidOunce(double value, String to) {
    double result = 0.0;
    if (to.equals(P)) {
      result = ((value * 16) * 3) * 2;
    }
    if (to.equals(TSP)) {
      result = (value * 3) * 2;
    }
    if (to.equals(TBS)) {
      result = value * 2;
    }
    if (to.equals(CUP)) {
      result = value / 8;
    }
    if (to.equals(PT)) {
      result = value / 16;
    }
    if (to.equals(QT)) {
      result = value / 32;
    }
    if (to.equals(GAL)) {
      result = value / 128;
    }
    if (to.equals(ML)) {
      result = value * 29.57353;
    }
    if (to.equals(FLOZ)) {
      result = value;
    }
    return result;
  }
}
