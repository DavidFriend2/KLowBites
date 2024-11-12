package UnitConversion;

/**
 * This class provides methods for converting between different volume units.
 * It uses fluid ounces as an intermediate unit for all conversions.
 */
public class VolumeUnitConverter {

    /**
     * Converts a value from one volume unit to another.
     *
     * @param value The numeric value to convert
     * @param from The unit to convert from
     * @param to The unit to convert to
     * @return The converted value
     */
    public static double convert(double value, MassVolumeConverter.Unit from, MassVolumeConverter.Unit to) {
        double fluidOunces = toFluidOunce(value, from);
        return fromFluidOunce(fluidOunces, to);
    }
  
    /**
     * Converts a value from a given unit to fluid ounces.
     *
     * @param value The numeric value to convert
     * @param from The unit to convert from
     * @return The value in fluid ounces
     * @throws IllegalArgumentException if the unit is not supported
     */
    public static double toFluidOunce(double value, MassVolumeConverter.Unit from) {
        switch (from) {
            case PINCHES: return ((value / 16.0) / 3.0) / 2.0;
            case TEASPOONS: return (value / 3.0) / 2.0;
            case TABLESPOONS: return value / 2.0;
            case CUPS: return value * 8.0;
            case PINTS: return value * 16.0;
            case QUARTS: return value * 32.0;
            case GALLONS: return value * 128.0;
            case MILLILITERS: return value / 29.57353;
            case FLUID_OUNCES: return value;
            default: throw new IllegalArgumentException("Unsupported unit: " + from);
        }
    }
  
    /**
     * Converts a value from fluid ounces to a given unit.
     *
     * @param value The numeric value in fluid ounces
     * @param to The unit to convert to
     * @return The converted value
     * @throws IllegalArgumentException if the unit is not supported
     */
    public static double fromFluidOunce(double value, MassVolumeConverter.Unit to) {
        switch (to) {
            case PINCHES: return ((value * 16.0) * 3.0) * 2.0;
            case TEASPOONS: return (value * 3.0) * 2.0;
            case TABLESPOONS: return value * 2.0;
            case CUPS: return value / 8.0;
            case PINTS: return value / 16.0;
            case QUARTS: return value / 32.0;
            case GALLONS: return value / 128.0;
            case MILLILITERS: return value * 29.57353;
            case FLUID_OUNCES: return value;
            default: throw new IllegalArgumentException("Unsupported unit: " + to);
        }
    }
}