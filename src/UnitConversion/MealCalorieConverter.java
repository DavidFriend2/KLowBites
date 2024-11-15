package UnitConversion;

import Information.Ingredient;
import Information.RecipeIngredient;
import Information.Meal;
import Information.Recipe;

public class MealCalorieConverter
{
  public static double convertMeal(Meal meal) 
  {
    double totalCalories = 0.0;
    
    for (Recipe recipe : meal.getRecipes())
    {
      for (RecipeIngredient getIngredient : recipe.getIngredients()) 
      {
        // go through each ingredient
        
        Ingredient currIngredient = Ingredient.getIngredientbyName(getIngredient.getName());
        
        MassVolumeConverter.Unit currUnit = null; 
        // turn a String into a Unit for the CalorieConverter convert method
        for (MassVolumeConverter.Unit unit : MassVolumeConverter.getUnits())
        {
          String unitTest = unit.name();
          if ((unitTest.toLowerCase().equals(getIngredient.getUnit().toLowerCase())))
          {
            currUnit = unit;
            break;
          } else if (unitTest.toLowerCase().substring(0, unitTest.length() 
              - 1).equals(getIngredient.getUnit().toLowerCase())) 
          {
            currUnit = unit;
            break;
          } else if (unitTest.toLowerCase().substring(0, unitTest.length() 
              - 2).equals(getIngredient.getUnit().toLowerCase())) 
          {
            currUnit = unit;
            break;
          } 
        }
        if (currUnit == null)  {
          currUnit = MassVolumeConverter.Unit.OUNCES;
        }
        totalCalories += CalorieConverter.convert(currIngredient,
            getIngredient.getAmount(), currUnit);
      }
    }
    return totalCalories;
  }
}
