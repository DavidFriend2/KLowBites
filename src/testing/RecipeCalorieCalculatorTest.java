package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Information.Recipe;

import UnitConversion.RecipeCalorieConverter;

class RecipeCalorieCalculatorTest
{
  
  @Test
  void test()
  {
    Recipe recipeTest = Recipe.getRecipes().get(1); 
    assertEquals(2036.5126742426253, RecipeCalorieConverter.convertRecipe(recipeTest));
  }

}
