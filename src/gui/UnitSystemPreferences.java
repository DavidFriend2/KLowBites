package gui;

import java.util.ResourceBundle;

/**
 * Unit System Preferences.
 * 
 * @author Jayden Smith
 */
public class UnitSystemPreferences 
{
  private static UnitSystem currentUnitSystem = UnitSystem.METRIC;

  /**
   * Unit System enum.
   */
  public enum UnitSystem 
  {
      METRIC, 
      IMPERIAL
  }

  /**
   * Gets current unit system.
   * 
   * @return the unit system
   */
  public static UnitSystem getCurrentUnitSystem() 
  {
    return currentUnitSystem;
  }
  
  /**
   * Sets the current unit system.
   * 
   * @param unitSystem the unit system to set
   */
  public static void setCurrentUnitSystem(final UnitSystem unitSystem) 
  {
    currentUnitSystem = unitSystem;
  }

  /**
   * Checks if a unit is metric.
   * 
   * @return true or false
   */
  public static boolean isMetric() 
  {
    return currentUnitSystem == UnitSystem.METRIC;
  }

  /**
   * Checks if a unit is imperial.
   * 
   * @return true or false
   */
  public static boolean isImperial() 
  {
    return currentUnitSystem == UnitSystem.IMPERIAL;
  }

  /**
   * Gets units for the current unit system.
   * 
   * @param strings the strings
   * @return String
   */
  public static String[] getUnitsForCurrentSystem(final ResourceBundle strings) 
  {
    if (isMetric()) 
    {
      return new String[]{"", 
        strings.getString("unit_milliliters"),
        strings.getString("unit_liters"),
        strings.getString("unit_kilograms"),
        strings.getString("unit_grams"),
        strings.getString("unit_pinches"),
        strings.getString("unit_teaspoons"),
        strings.getString("unit_tablespoons")
        // other metric units can slide here (:
      };
    } else 
    {
      return new String[]{"", 
      strings.getString("unit_cups"),
      strings.getString("unit_ounces"),
      strings.getString("unit_fluid_ounces"),
      strings.getString("unit_pints"),
      strings.getString("unit_quarts"),
      strings.getString("unit_gallons"),
      strings.getString("unit_pounds"),
      strings.getString("unit_drams")
      // other imperial units can slide here (:
      };
    }
  }
}
