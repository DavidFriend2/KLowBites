package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

class RecipeTest
{

  @Test
  void testRecipeConstructorAndGetters()
  {
    
    String testName = "Test Recipe";
    int testServes = 4;

    List<RecipeIngredient> testIngredients = new ArrayList<>();
    testIngredients.add(new RecipeIngredient("Flour", "all-purpose", 2, "cups"));

    List<Utensil> testUtensils = new ArrayList<>();
    testUtensils.add(new Utensil("Bowl", "large"));

    List<Step> testSteps = new ArrayList<>();
    testSteps.add(new Step("Mix", "flour", "bowl", "until smooth", 5));

    Recipe recipe = new Recipe(testName, testServes, testIngredients, testUtensils, testSteps);

    assertEquals(testName, recipe.getName());
    assertEquals(testServes, recipe.getServes());
    assertEquals(testIngredients, recipe.getIngredients());
    assertEquals(testUtensils, recipe.getUtenils());
    assertEquals(testSteps, recipe.getSteps());
  }

  @Test
  void testGetFileName()
  {
    Recipe recipe = new Recipe("Test Recipe", 0, null, null, null);

    String expectedFileName = "recipes/Test_Recipe.rcp";
    assertEquals(expectedFileName, recipe.getFileName());
  }

  @Test
  void testToString()
  {
    Recipe recipe = new Recipe("Test Recipe", 2, null, null, null);
    assertEquals("Test Recipe", recipe.toString());
  }
  
  @Test
  void testAddandGetRecipes()
  {
    assertEquals(3, Recipe.getRecipes().size());
    
    Recipe.addRecipe(new Recipe("Test Recipe", 0, null, null, null));
    
    assertEquals(4, Recipe.getRecipes().size());
    
  }
}
