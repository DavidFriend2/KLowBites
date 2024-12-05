package Information;

import java.util.ArrayList;
import java.util.List;

import UnitConversion.*;

public class ShoppingList
{
  private List<RecipeIngredient> shoppingList;
  private int numofpeople;
  Recipe recipe;
  Meal meal;

  public ShoppingList(Recipe recipe, int numofpeople)
  {
    // Updated for amount of people
    this.numofpeople = numofpeople;
    this.recipe = changeIngredientAmount(recipe);
    this.shoppingList = this.recipe.getIngredients();
  }

  public ShoppingList(Meal meal, int numofpeople)
  {
    this.numofpeople = numofpeople;
    this.meal = meal;
    
    this.shoppingList = new ArrayList<>();
    // go through every meal
    for (Recipe recipe : meal.getRecipes())
    {
      // Update for amount of people
      Recipe converted = changeIngredientAmount(recipe);
      
      // add each ingredient from every meal
      for (RecipeIngredient ri : converted.getIngredients())
      {

        // check if ingredient already in shopping list
        boolean in = false;

        for (RecipeIngredient alreadyin : this.shoppingList)
        {
          // if ingredients already been added, increase amount
          if (ri.getName().equals(alreadyin.getName()))
          {
            //convert units before adding amount
            Ingredient riIngredient = Ingredient.getIngredientbyName(ri.getName());
            MassVolumeConverter.Unit fromUnit = getUnitFromString(ri.getUnit());
            MassVolumeConverter.Unit toUnit = getUnitFromString(alreadyin.getUnit());
            
            double newAmount = convert(ri.getAmount(), fromUnit, toUnit, riIngredient);
            
            alreadyin.setAmount(alreadyin.getAmount() + newAmount);
            in = true;
            break;
          }
          
        }
        
        //if ingredient isn't in list add it
        if (!in)
        {
          this.shoppingList.add(ri);
        }

      }

    }

  }

  public int getNumPeople()
  {
    return this.numofpeople;
  }

  public List<RecipeIngredient> getShoppingList()
  {
    return this.shoppingList;
  }

  private Recipe changeIngredientAmount(Recipe recipeToChange)
  {
    Recipe converted = new Recipe(recipeToChange.getName(), recipeToChange.getServes(), recipeToChange.getIngredients(), 
        recipeToChange.getUtenils(), recipeToChange.getSteps());

    double convert = (this.numofpeople) * 1.0 / converted.getServes();

    // change the amount of ingredients based on the number of people
    for (RecipeIngredient ri : converted.getIngredients())
    {
      ri.setAmount(ri.getAmount() * convert);
    }

    return converted;
  }

  /*
   * Converts amount based on unit
   */
  public static double convert(double value, MassVolumeConverter.Unit from, MassVolumeConverter.Unit to,
      Ingredient ingredient)
  {
    // Check if both from and to are mass units
    if (isMass(from) && isMass(to))
    {
      return MassUnitConverter.convert(value, from, to);
    }

    // Check if both from and to are volume units
    if (isVolume(from) && isVolume(to))
    {
      return VolumeUnitConverter.convert(value, from, to);
    }

    return MassVolumeConverter.convert(value, from, to, ingredient);
  }

  /*
   * Get unit from string method
   */
  public static MassVolumeConverter.Unit getUnitFromString(String unitString) {
    String unit = unitString.trim().toLowerCase();

    switch (unit) {
        case "cup":
        case "cups":
            return MassVolumeConverter.Unit.CUPS;
        case "gram":
        case "grams":
            return MassVolumeConverter.Unit.GRAMS;
        case "ounce":
        case "ounces":
            return MassVolumeConverter.Unit.OUNCES;
        case "pound":
        case "pounds":
            return MassVolumeConverter.Unit.POUNDS;
        case "milliliter":
        case "milliliters":
            return MassVolumeConverter.Unit.MILLILITERS; 
        case "teaspoon":
        case "teaspoons":
            return MassVolumeConverter.Unit.TEASPOONS;
        case "tablespoon":
        case "tablespoons":
            return MassVolumeConverter.Unit.TABLESPOONS;
        case "pinch":
        case "pinches":
            return MassVolumeConverter.Unit.PINCHES;
        case "pint":
        case "pints":
            return MassVolumeConverter.Unit.PINTS;
        case "quart":
        case "quarts":
            return MassVolumeConverter.Unit.QUARTS;
        case "gallon":
        case "gallons":
            return MassVolumeConverter.Unit.GALLONS;
        case "fluid ounce":
        case "fluid ounces":
            return MassVolumeConverter.Unit.FLUID_OUNCES;
        default:
            throw new IllegalArgumentException("Invalid unit string: " + unitString);
    }
}

  // Helper method to check if unit is mass
  private static boolean isMass(MassVolumeConverter.Unit unit)
  {
    for (MassVolumeConverter.Unit massUnit : MassVolumeConverter.getWeights())
    {
      if (massUnit == unit)
      {
        return true;
      }
    }
    return false;
  }

  // Helper method to check if unit is volume
  private static boolean isVolume(MassVolumeConverter.Unit unit)
  {
    // If it's not mass, it's volume
    return !isMass(unit);
  }

}
