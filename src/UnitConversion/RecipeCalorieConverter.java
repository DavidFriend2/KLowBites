package UnitConversion;

import Information.Ingredient;
import Information.RecipeIngredient;
import Information.Recipe;

public class RecipeCalorieConverter
{
  @SuppressWarnings("unlikely-arg-type")
  public static double convertRecipe(Recipe recipe) 
  {
    double totalCalories = 0.0;
    
    for (RecipeIngredient getIngredient : recipe.getIngredients()) {
          
      Ingredient currIngredient = null; // turn a RecipeIngredient into an ingredient for the CalorieConverter convert method
      for (Ingredient ingredient : Ingredient.getIngredients()) 
      {
        if (getIngredient.equals(ingredient))
        {
          currIngredient = ingredient;
          break;
        }
      }
          
      MassVolumeConverter.Unit currUnit = null; // turn a String into a Unit for the CalorieConverter convert method
      for (MassVolumeConverter.Unit unit : MassVolumeConverter.getUnits())
      {
        if (unit.equals(getIngredient.getUnit()))
        {
          currUnit = unit;
          break;
        }
      }
          
      totalCalories = CalorieConverter.convert(currIngredient, getIngredient.getAmount(), currUnit);
    }  
    return totalCalories;
  }
}
