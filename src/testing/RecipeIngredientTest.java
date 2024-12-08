package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Information.RecipeIngredient;

/**
 * RecipeIngredient test
 * 
 * @author ryan mendez
 */
class RecipeIngredientTest
{

  @Test
  void testConstrcutorAndGetters()
  {
    RecipeIngredient ingredient = new RecipeIngredient("American cheese", "shredded", 1, "cup");

    assertEquals("American cheese", ingredient.getName());
    assertEquals("shredded", ingredient.getDetails());
    assertEquals(1, ingredient.getAmount());
    assertEquals("cup", ingredient.getUnit());
  }
  
  @Test
  void testSetters()
  {
    RecipeIngredient ingredient = new RecipeIngredient("American cheese", "shredded", 1, "cup");

    ingredient.setName("Colby Jack");
    ingredient.setDetails("melted");
    ingredient.setAmount(2);
    ingredient.setUnit("pound");
    
    assertEquals("Colby Jack", ingredient.getName());
    assertEquals("melted", ingredient.getDetails());
    assertEquals(2, ingredient.getAmount());
    assertEquals("pound", ingredient.getUnit());
  }
  
  @Test
  void testToString()
  {
    RecipeIngredient ingredient = new RecipeIngredient("American cheese", "shredded", 1, "cup");
    
    String expected = "1.000 cup american cheese";
    
    assertEquals(expected, ingredient.toString());
    
  }
  

}
