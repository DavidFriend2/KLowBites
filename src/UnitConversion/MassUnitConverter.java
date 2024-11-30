package UnitConversion;

/**
 * This class provides methods for converting between different mass units.
 */
public class MassUnitConverter 
{

  /**
   * Converts a value from one mass unit to another.
   *
   * @param value The numeric value to convert
   * @param from The unit to convert from
   * @param to The unit to convert to
   * @return The converted value in the target unit
   */
  public static double convert(final double value, 
      final MassVolumeConverter.Unit from, final MassVolumeConverter.Unit to) 
  {
    double ounces = toOunces(value, from);
    return fromOunces(ounces, to);
  }

  /**
   * Converts a value from a specified mass unit to ounces.
   *
   * @param value The numeric value to convert
   * @param from The unit to convert from
   * @return The equivalent value in ounces
   * @throws IllegalArgumentException if the unit is not supported
   */
  private static double toOunces(final double value, final MassVolumeConverter.Unit from) 
  {
    switch (from) 
    {
      case OUNCES:
        return value;
      case GRAMS:
        return value / 28.3495231; // Convert grams to ounces
      case DRAMS:
        return value / 16; // Convert drams to ounces
      case POUNDS:
        return value * 16; // Convert pounds to ounces
      // case KILOGRAMs:
        // return value * 35.273962;
      default:
        throw new IllegalArgumentException("Unsupported mass unit: " + from);
    }
  }

  /**
   * Converts a value from ounces to a specified mass unit.
   *
   * @param value The numeric value in ounces
   * @param to The unit to convert to
   * @return The equivalent value in the target unit
   * @throws IllegalArgumentException if the unit is not supported
   */
  private static double fromOunces(final double value, final MassVolumeConverter.Unit to) 
  {
    switch (to) 
    {
      case OUNCES:
        return value;
      case GRAMS:
        return value * 28.3495231; // Convert ounces to grams
      case DRAMS:
        return value * 16; // Convert ounces to drams
      case POUNDS:
        return value / 16; // Convert ounces to pounds
      // case KILOGRAMS:
        // return value / 35.273962;
      default:
        throw new IllegalArgumentException("Unsupported mass unit: " + to);
    }
  }
}
