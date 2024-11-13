package Information;

import java.io.IOException;
import java.util.List;

public class RecipeFileCreator
{

  public static void main(String[] args)
  {
    
    
//    // Retrieve the recipes list from the Recipe class
//
//    // Meal testMeal = new Meal("Good Eats", recipes);
//    //Recipe recipe = recipes.get(2);
//    try
//    {
//      Ingredient.saveIngredients("IngredientsNutrition/ingredients.ntr");
//    }
//    catch (IOException e)
//    {
//      e.printStackTrace();
//    }
//
//    // Save each recipe to a separate file
//    // for (Recipe recipe : recipes)
//    // {
//    // try
//    // {
//    // recipe.saveRecipeToFile();
//    // System.out.println("Saved recipe: " + recipe.getName() + " to " + recipe.getFileName());
//    // }
//    // catch (IOException e)
//    // {
//    // System.out.println("Failed to save recipe: " + recipe.getName());
//    // e.printStackTrace();
//    // }
//    // }
//    // }
    
    try
    {
      List<Ingredient> ingredients = Ingredient.loadIngredients("IngredientsNutrition/ingredients.ntr");
      
      for(Ingredient i: ingredients)
      {
        System.out.println("\n" + i.getName());
      }
    }
    catch (ClassNotFoundException | IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
