package UnitConversion;

public class VolumeUnitConverter
{
  private static final String P = "pinches";
  private static final String TSP = "teaspoons";
  private static final String TBS = "tablespoons";
  private static final String FLOZ = "fluid ounces";
  private static final String CUP = "cups";
  private static final String PT = "pints";
  private static final String QT = "quarts";
  private static final String GAL = "gallons";
  private static final String ML = "milliliters";
  
  
  public static double convert(double value, String from, String to) {
    double result = toFluidOunce(value, from.toLowerCase());
    return fromFluidOunce(result, to.toLowerCase());
  }
  
  public static double toFluidOunce(double value, String from) {
    if (from.equals(P)) {
      return ((value / 16.0) / 3.0) / 2.0;
    }
    if (from.equals(TSP)) {
      return (value / 3.0) / 2.0;
    }
    if (from.equals(TBS)) {
      return value / 2.0;
    }
    if (from.equals(CUP)) {
      return value * 8.0;
    }
    if (from.equals(PT)) {
      return value * 16.0;
    }
    if (from.equals(QT)) {
      return value * 32.0;
    }
    if (from.equals(GAL)) {
      return value * 128.0;
    }
    if (from.equals(ML)) {
      return value / 29.57353;
    }
    return value;
  }
  
  
  public static final double fromFluidOunce(double value, String to) {
    if (to.equals(P)) {
      return ((value * 16.0) * 3.0) * 2.0;
    }
    if (to.equals(TSP)) {
      return (value * 3.0) * 2.0;
    }
    if (to.equals(TBS)) {
      return value * 2.0;
    }
    if (to.equals(CUP)) {
      return value / 8.0;
    }
    if (to.equals(PT)) {
      return value / 16.0;
    }
    if (to.equals(QT)) {
      return value / 32.0;
    }
    if (to.equals(GAL)) {
      return value / 128.0;
    }
    if (to.equals(ML)) {
      return value * 29.57353;
    }
    return value;
  }
}
