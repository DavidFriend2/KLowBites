package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Information.Ingredient;
import UnitConversion.CalorieConverter;
import UnitConversion.MassVolumeConverter;

/**
 * @author David Friend (modified)
 * Tests the CalorieConverter
 */
class CalorieConverterTest {

    @Test
    void test() {
        Ingredient apple = Ingredient.getIngredientbyName("Apple");
        
        // Pounds to calories
        assertEquals("2394.97", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.POUNDS)));
        
        // Ounces to calories
        assertEquals("149.69", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.OUNCES)));
        
        // Grams to calories
        assertEquals("5.28", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.GRAMS)));
        
        // Drams to calories
        assertEquals("9.36", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.DRAMS)));
        
        // Cups to calories
        assertEquals("699.54", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.CUPS)));
        
        // Fluid Ounces to calories
        assertEquals("87.44", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.FLUID_OUNCES)));
        
        // Teaspoons to calories
        assertEquals("14.57", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.TEASPOONS)));
        
        // Tablespoons to calories
        assertEquals("43.72", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.TABLESPOONS)));
        
        // Gallons to calories
        assertEquals("11192.71", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.GALLONS)));
        
        // Pinches to calories
        assertEquals("0.91", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.PINCHES)));
        
        // Pints to calories
        assertEquals("1399.09", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.PINTS)));
        
        // Quarts to calories
        assertEquals("2798.18", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.QUARTS)));
        
        // Milliliters to calories
        assertEquals("2.96", String.format("%.2f", CalorieConverter.convert(apple, 12, MassVolumeConverter.Unit.MILLILITERS)));
    }
}