package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Information.*;

/**
 * ShoppingList TEst
 * 
 * @author ryanmendez
 */
class ShoppingListTest
{

  private Recipe sampleRecipe;
  private Recipe sampleRecipe2;
  private Meal sampleMeal;

  @BeforeEach
  void setUp()
  {
    RecipeIngredient ingredient1 = new RecipeIngredient("Flour", null, 50, "grams");
    RecipeIngredient ingredient2 = new RecipeIngredient("Milk", null, 5.5, "cups");
    RecipeIngredient ingredient3 = new RecipeIngredient("Eggs", null, 20, "individual");

    List<RecipeIngredient> recipeIngredients = new ArrayList<>();
    recipeIngredients.add(ingredient1);
    recipeIngredients.add(ingredient2);
    recipeIngredients.add(ingredient3);

    sampleRecipe = new Recipe("Pancakes", 2, recipeIngredients, new ArrayList<>(),
        new ArrayList<>(), "");

    sampleRecipe2 = new Recipe("Scrambeled Eggs", 2, recipeIngredients, new ArrayList<>(),
        new ArrayList<>(), "");

    List<Recipe> recipes = new ArrayList<>();
    recipes.add(sampleRecipe);
    recipes.add(sampleRecipe2);

    sampleMeal = new Meal("Breakfast", recipes);
  }

  @Test
  void testConstructorForRecipe()
  {
    ShoppingList shoppingList = new ShoppingList(sampleRecipe, 4);

    assertEquals(4, shoppingList.getNumPeople());

    List<RecipeIngredient> ingredients = shoppingList.getShoppingList();
    assertEquals(3, ingredients.size());
    assertEquals(100, ingredients.get(0).getAmount(), 0.01);
    assertEquals(11, ingredients.get(1).getAmount(), 0.01);
    assertEquals(40, ingredients.get(2).getAmount(), 0.01);
  }

  @Test
  void testConstructorForMealSameUnits()
  {
    ShoppingList shoppingList = new ShoppingList(sampleMeal, 4);

    assertEquals(4, shoppingList.getNumPeople());

    List<RecipeIngredient> ingredients = shoppingList.getShoppingList();
    assertEquals(3, ingredients.size());
    assertEquals(400, ingredients.get(0).getAmount(), 0.01);
    assertEquals(44, ingredients.get(1).getAmount(), 0.01);
    assertEquals(160, ingredients.get(2).getAmount(), 0.01);
  }

  //also tests convert, change ingredient amount, and all the other helpers, as do most of them
  @Test
  void testConstructorForMealDifferentUnits()
  {
    //checks merging ingredients with different units
    RecipeIngredient i1 = new RecipeIngredient("Flour", null, 5, "ounce");
    RecipeIngredient i2 = new RecipeIngredient("Milk", null, 20, "tablespoon");
    RecipeIngredient i3 = new RecipeIngredient("Beef", null, 100, "pound");

    List<RecipeIngredient> recipeIngredients = new ArrayList<>();
    recipeIngredients.add(i1);
    recipeIngredients.add(i2);
    recipeIngredients.add(i3);

    Recipe recipe = new Recipe("Test Recipe", 1, recipeIngredients, new ArrayList<>(),
        new ArrayList<>(), "");
    
    List<Recipe> r = new ArrayList<>();
    r.add(sampleRecipe);
    r.add(sampleRecipe2);
    r.add(recipe);
    
    Meal meal = new Meal("Test", r);

    ShoppingList shoppingList = new ShoppingList(meal, 4);

    assertEquals(4, shoppingList.getNumPeople());

    List<RecipeIngredient> ingredients = shoppingList.getShoppingList();
    assertEquals(4, ingredients.size());
    // 5 Ounces converted to grams * 4 (566.992) then added to the previous ingredient amount of 400
    assertEquals(966.992, ingredients.get(0).getAmount(), 0.01);
    // 20 tablesppon converted to grams * 4 (5) then added to the previous ingredient amount of 44
    assertEquals(49, ingredients.get(1).getAmount(), 0.01);
    //should stay same, no merging this time
    assertEquals(160, ingredients.get(2).getAmount(), 0.01);
    //new ingredient should be alone
    assertEquals(400, ingredients.get(3).getAmount(), 0.01);
    
  }

}
