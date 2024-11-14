package UnitConversion;

import Information.Ingredient;
import Information.RecipeIngredient;
import Information.Meal;
import Information.Recipe;

public class MealCalorieConverter
{
  @SuppressWarnings("unlikely-arg-type")
  public static double convertMeal(Meal meal) 
  {
    double totalCalories = 0.0;
    
    for (Recipe recipe : meal.getRecipes())
    {
        for (RecipeIngredient getIngredient : recipe.getIngredients()) { // go through each ingredient
          
          Ingredient currIngredient = Ingredient.getIngredientbyName(getIngredient.getName());
          
          MassVolumeConverter.Unit currUnit = null; // turn a String into a Unit for the CalorieConverter convert method
          for (MassVolumeConverter.Unit unit : MassVolumeConverter.getUnits())
          {
            if (unit.equals(getIngredient.getUnit()))
            {
              currUnit = unit;
              break;
            }
          }
          
          totalCalories += CalorieConverter.convert(currIngredient, getIngredient.getAmount(), currUnit);
        }
    }
    return totalCalories;
  }
}
