package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Information.Meal;
import Information.Recipe;

class MealTest
{

  @Test
  void testConstructorAndGetters()
  {
    List<Recipe> recipes = new ArrayList<>();
    Meal meal = new Meal("Test", recipes);

    assertSame(recipes, meal.getRecipes());
    assertEquals("Test", meal.getName());
  }

  @Test
  void testAddRecipe()
  {
    List<Recipe> recipes = new ArrayList<>();
    Meal meal = new Meal("Test", recipes);

    assertEquals(0, meal.getRecipes().size());
    meal.addRecipe(new Recipe("Tacos", 0, null, null, null, null));

    assertEquals(1, meal.getRecipes().size());
    assertEquals("Tacos", meal.getRecipes().get(0).getName());
  }

  @Test
  void testGetFileName()
  {
    List<Recipe> recipes = new ArrayList<>();
    Meal meal = new Meal("Test", recipes);

    String fileName = meal.getFileName();

    assertEquals("meals/Test.mel", fileName);
  }

  @Test
  void testToString()
  {
    List<Recipe> recipes = new ArrayList<>();
    Meal meal = new Meal("Test", recipes);
    
    String toString = meal.toString();
    
    assertEquals("Test", toString);
  }
  

}
