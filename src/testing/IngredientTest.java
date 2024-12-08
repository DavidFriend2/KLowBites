package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Information.Ingredient;

class IngredientTest
{

  @Test
  void testConstructorAndGetters() 
  {
      
      Ingredient ingredient = new Ingredient("Apple", 55, 0.55);

      assertEquals("Apple", ingredient.getName());
      assertEquals(55, ingredient.getCaloriesPer100g());
      assertEquals(0.55, ingredient.getGramsPerM());
  }
  
  @Test
  void testToString()
  {
    Ingredient ingredient = new Ingredient("Apple", 55, 0.55);
    assertEquals("Apple", ingredient.toString());
  }
  
  @Test
  void testIngredientsStaticList()
  {
    //get ingredients
    assertNotNull(Ingredient.getIngredients());
    
    assertEquals(77, Ingredient.getIngredients().size());
    
    //adding new ingredients
    Ingredient newIngredient = new Ingredient("Test", 123, 1.23);
    Ingredient.addIngredient(newIngredient);
    
    assertEquals(78, Ingredient.getIngredients().size());
    
    //getting ingredient by name
    assertEquals(newIngredient, Ingredient.getIngredientbyName("Test"));
  }

}
