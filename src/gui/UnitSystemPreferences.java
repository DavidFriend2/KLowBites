package gui;

import java.util.ResourceBundle;

public class UnitSystemPreferences {
    private static UnitSystem currentUnitSystem = UnitSystem.METRIC;

    public enum UnitSystem {
        METRIC, 
        IMPERIAL
    }

    public static UnitSystem getCurrentUnitSystem() {
        return currentUnitSystem;
    }

    public static void setCurrentUnitSystem(UnitSystem unitSystem) {
        currentUnitSystem = unitSystem;
    }

    public static boolean isMetric() {
        return currentUnitSystem == UnitSystem.METRIC;
    }

    public static boolean isImperial() {
        return currentUnitSystem == UnitSystem.IMPERIAL;
    }

    public static String[] getUnitsForCurrentSystem(ResourceBundle strings) {
        if (isMetric()) {
            return new String[]{"", 
                strings.getString("unit_milliliters"),
                strings.getString("unit_grams"),
                strings.getString("unit_pinches"),
                strings.getString("unit_teaspoons"),
                strings.getString("unit_tablespoons")
                // Add other metric units here
            };
        } else {
            return new String[]{"", 
                strings.getString("unit_cups"),
                strings.getString("unit_ounces"),
                strings.getString("unit_fluid_ounces"),
                strings.getString("unit_pints"),
                strings.getString("unit_quarts"),
                strings.getString("unit_gallons"),
                strings.getString("unit_pounds"),
                strings.getString("unit_drams")
                // Add other imperial units here
            };
        }
    }
}