package testing;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import Information.Ingredient;
import UnitConversion.CalorieConverter;

/**
 * @author David Friend
 * Tests the CalorieConverter
 */
class CalorieConverterTest
{

  @Test
  void test()
  {
    Ingredient apple = Ingredient.getIngredientbyName("Apple");
    
    //Pounds to calories
    assertEquals("2394.97", String.format("%.2f", CalorieConverter.convert(apple, 12, "pounds")));
    
    //Ounces to calories
    assertEquals("149.69", String.format("%.2f", CalorieConverter.convert(apple, 12, "ounces")));
    
    //Grams to calories
    assertEquals("5.28", String.format("%.2f", CalorieConverter.convert(apple, 12, "grams")));
    
    //Drams to calories
    assertEquals("9.36", String.format("%.2f", CalorieConverter.convert(apple, 12, "drams")));
    
    //Cups to calories
    assertEquals("699.53", String.format("%.2f", CalorieConverter.convert(apple, 12, "cups")));
    
    //Fluid Ounces to calories
    assertEquals("87.44", String.format("%.2f", CalorieConverter.convert(apple, 12, "fluid ounces")));
    
    //Teaspoons to calories
    assertEquals("14.57", String.format("%.2f", CalorieConverter.convert(apple, 12, "teaspoons")));
    
    //Tablespoons to calories
    assertEquals("43.72", String.format("%.2f", CalorieConverter.convert(apple, 12, "tablespoons")));
    
    //Gallons to calories
    assertEquals("11192.52", String.format("%.2f", CalorieConverter.convert(apple, 12, "gallons")));
    
    //Pinches to calories
    assertEquals("0.91", String.format("%.2f", CalorieConverter.convert(apple, 12, "pinches")));
    
    //Pints to calories
    assertEquals("1399.06", String.format("%.2f", CalorieConverter.convert(apple, 12, "pints")));
    
    //Quarts to calories
    assertEquals("2798.13", String.format("%.2f", CalorieConverter.convert(apple, 12, "quarts")));
    
    //Mililiters to calories
    assertEquals("2.96", String.format("%.2f", CalorieConverter.convert(apple, 12, "milliliters")));
  }
}
