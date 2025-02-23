package Information;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import UnitConversion.*;

/**
 * Shopping List class.
 * 
 * @author ryan mendez
 */
public class ShoppingList
{
  private List<RecipeIngredient> shoppingList;
  private int numofpeople;
  Recipe recipe;
  Meal meal;

  /**
   * Shopping List Constructor for a Recipe.
   * 
   * @param recipe
   *          to shop for
   * @param numofpeople
   *          to shop for
   */
  public ShoppingList(final Recipe recipe, final int numofpeople)
  {
    // Updated for amount of people
    this.numofpeople = numofpeople;
    Recipe converted = changeIngredientAmount(recipe);
    this.recipe = converted;
    this.shoppingList = this.recipe.getIngredients();
  }

  /**
   * Shopping List Constructor for a Meal.
   * 
   * @param meal
   *          to shop for
   * @param numofpeople
   *          to shop for
   */
  public ShoppingList(final Meal meal, final int numofpeople)
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
          double newAmount = ri.getAmount();
          if (ri.getName().equals(alreadyin.getName()))
          {
            // convert units before adding amount
            if (!(ri.getUnit().equals("individual")))
            {

              if (!(ri.getUnit().equals(alreadyin.getUnit())))
              {
                try
                {
                  Ingredient.setIngredients(Ingredient.loadIngredients("ingredients.ntr"));
                }
                catch (ClassNotFoundException | IOException e)
                {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                
                Ingredient riIngredient = Ingredient.getIngredientbyName(ri.getName());
                MassVolumeConverter.Unit fromUnit = getUnitFromString(ri.getUnit());
                MassVolumeConverter.Unit toUnit = getUnitFromString(alreadyin.getUnit());

                newAmount = convert(ri.getAmount(), fromUnit, toUnit, riIngredient);
              }
            }

            alreadyin.setAmount(alreadyin.getAmount() + newAmount);
            in = true;
            break;
          }

        }

        // if ingredient isn't in list add it
        if (!in)
        {
          this.shoppingList.add(ri);
        }

      }

    }

  }

  /**
   * Get number of people method.
   * 
   * @return the amount of people to shop for
   */
  public int getNumPeople()
  {
    return this.numofpeople;
  }

  /**
   * Get Shopping List method.
   * 
   * @return list of ingredients to shop for
   */
  public List<RecipeIngredient> getShoppingList()
  {
    return this.shoppingList;
  }

  /**
   * Change Ingredient amount method.
   * 
   * Updates the amount for each ingredient based on the amount of people to shop for
   * 
   * @param recipeToChange
   *          to update
   * @return updated recipe
   */
  private Recipe changeIngredientAmount(final Recipe recipeToChange)
  {
    Recipe converted = new Recipe(recipeToChange.getName(), recipeToChange.getServes(),
        recipeToChange.getIngredients(), recipeToChange.getUtenils(), recipeToChange.getSteps(), 
        recipeToChange.getPairing());

    double convert = (this.numofpeople) * 1.0 / converted.getServes();

    // change the amount of ingredients based on the number of people
    for (RecipeIngredient ri : converted.getIngredients())
    {
      ri.setAmount(ri.getAmount() * convert);
    }

    return converted;
  }

  /**
   * Convert method.
   * 
   * Converts amount of an ingredient from one unit to another
   * 
   * @param value
   *          to convert
   * @param from
   *          unit to convert from
   * @param to
   *          unit to convert to
   * @param ingredient
   *          to use in case density info is required
   * @return the converted amount
   */
  public static double convert(final double value, final MassVolumeConverter.Unit from,
      final MassVolumeConverter.Unit to, final Ingredient ingredient)
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

  /**
   * Get Unit from its string rep.
   * 
   * @param unitString
   *          string rep of unit
   * @return the actual unit
   */
  public static MassVolumeConverter.Unit getUnitFromString(final String unitString)
  {
    String unit = unitString.trim().toLowerCase();

    switch (unit)
    {
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

  /**
   * Check if unit is mass.
   * 
   * @param unit
   *          to check
   * 
   * @return true if mass unit, false if not
   */
  private static boolean isMass(final MassVolumeConverter.Unit unit)
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

  /**
   * Check if unit is volume.
   * 
   * @param unit
   *          to check
   * 
   * @return true if volume unit, false if not
   */
  private static boolean isVolume(final MassVolumeConverter.Unit unit)
  {
    return !isMass(unit);
  }

}
