package Information;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private String name;
  private int serves;
  private List<RecipeIngredient> ingredients;
  private List<Utensil> utensils;
  private List<Step> steps;
  
  private static List<Recipe> recipes = new ArrayList<>();
  
 public Recipe(String name, int serves, List<RecipeIngredient> ingredients, List<Utensil> utensils, List<Step> steps)
 {
   this.name = name;
   this.serves = serves;
   this.ingredients = ingredients;
   this.utensils = utensils;
   this.steps = steps;
 }
 
 public String getName()
 {
   return this.name;
 }


 public int getServes()
 {
   return this.serves;
 }
 
 public List<RecipeIngredient> getIngredients()
 {
   return this.ingredients;
 }
 
 public List<Utensil> getUtenils()
 {
   return this.utensils;
 }
 
 public static List<Recipe> getRecipes()
 {
   return recipes;
 }
 
 public List<Step> getSteps()
 {
   return this.steps;
 }
 
 public static void addRecipe(Recipe recipe)
 {
   recipes.add(recipe);
 }
 
 @Override
 public String toString()
 {
   return this.name;
 }
 
 @SuppressWarnings("unchecked")
 public static void loadRecipes(String file) throws IOException, ClassNotFoundException
 {
   try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
   {
     recipes = (List<Recipe>) input.readObject();
   }
 }
 
 public static void saveRecipes(String file) throws IOException
 {
   try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)))
   {
     output.writeObject(recipes);
   }
 }
 
 public String getFileName()
 {
   return "recipes/" +  this.name.replaceAll(" ", "_") + ".rcp";
 }
 
 public void saveRecipeToFile(String file) throws IOException
 {
   try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)))
   {
     output.writeObject(this);
   }
 }
 
 public static Recipe loadRecipeFromFile(String file) throws IOException, ClassNotFoundException
 {
   try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
   {
     return (Recipe) input.readObject();
   }
 }
 
 
 
 static {
   //Bananas Foster
   String name = "Bananas Foster";
   
   int serves = 3;
   
   List<RecipeIngredient> ingredients = new ArrayList<>();
   ingredients.add(new RecipeIngredient("Butter", null, .33, "cup"));
   ingredients.add(new RecipeIngredient("Brown sugar", null, .33, "cup"));
   ingredients.add(new RecipeIngredient("Banana", "sliced ripe", 3, "indivdual"));
   ingredients.add(new RecipeIngredient("Creme de cacao", null, 2, "tablespoon"));
   ingredients.add(new RecipeIngredient("Rum", null, .25, "cup"));
   ingredients.add(new RecipeIngredient("Ice cream", "vanilla", 2, "cup"));
   ingredients.add(new RecipeIngredient("Cinnamon", "ground", .25, "teaspoon"));
   
   List<Utensil> utensils = new ArrayList<>();
   utensils.add(new Utensil("Skillet", "large"));
   utensils.add(new Utensil("Saucepan", null));
   utensils.add(new Utensil("Plate", null));
   
   List<Step> steps = new ArrayList<>();
   steps.add(new Step("Put", "butter","skillet", null, 2));
   steps.add(new Step("Melt", "skillet","skillet", null, 2));
   steps.add(new Step("Put", "brown sugar","skillet", null, 1));
   steps.add(new Step("Put", "bananas","skillet", null, 1));
   steps.add(new Step("Simmer", "skillet","skillet", "for 2 minutes", 2));
   steps.add(new Step("Put", "cinnamon","skillet", null, 1));
   steps.add(new Step("Put", "creme de cacao","skillet", null, 1));
   steps.add(new Step("Put", "skillet","plate", null, 1));
   steps.add(new Step("Heat", "rum","saucepan", "until it almost simmers", 4));
   steps.add(new Step("Ignite", "rum","saucepan", null, 4));
   steps.add(new Step("Put", "saucepan","plate", null, 1));
   steps.add(new Step("Put", "ice cream","plate", null, 1));
   
   Recipe bananasFoster = new Recipe(name, serves, ingredients, utensils, steps);
   recipes.add(bananasFoster);
   
   
   //Macaroni and Cheese
   String name2 = "Macaroni and Cheese";
   
   int serves2 = 2;
   
   List<RecipeIngredient> ingredients2 = new ArrayList<>();
   ingredients2.add(new RecipeIngredient("Macaroni", "dried elbow", 1, "cup"));
   ingredients2.add(new RecipeIngredient("Butter", null, 1, "tablespoon"));
   ingredients2.add(new RecipeIngredient("Onion", "chopped", .25, "cup"));
   ingredients2.add(new RecipeIngredient("Flour", "all-purpose", 1, "tablespoon"));
   ingredients2.add(new RecipeIngredient("Pepper", "black", 1, "pinch"));
   ingredients2.add(new RecipeIngredient("Milk", null, 1.25, "cup"));
   ingredients2.add(new RecipeIngredient("American cheese", "shredded", 1.5, "cup"));
   ingredients2.add(new RecipeIngredient("Tomato", "sliced medium", 1, "indivudal"));
   
   List<Utensil> utensils2 = new ArrayList<>();
   utensils2.add(new Utensil("Pot", "large"));
   utensils2.add(new Utensil("Saucepan", "medium"));
   utensils2.add(new Utensil("Casserole", "1-quart"));
   utensils2.add(new Utensil("strainer", null));
   
   List<Step> steps2 = new ArrayList<>();
   steps2.add(new Step("Boil", "macaroni","pot", "for 10 minutes", 10));
   steps2.add(new Step("Drain", "pot","strainer", null, 1));
   steps2.add(new Step("Put", "butter","saucepan", null, 1));
   steps2.add(new Step("Put", "onion","saucepan", null, 1));
   steps2.add(new Step("Saute", "saucepan","saucepan", "until tender but not brown", 1));
   steps2.add(new Step("Put", "flour","saucepan", null, 1));
   steps2.add(new Step("Put", "pepper","saucepan", null, 1));
   steps2.add(new Step("Put", "milk","saucepan", null, 1));
   steps2.add(new Step("Simmer", "saucepan","saucepan", "until slightly thickened", 1));
   steps2.add(new Step("Put", "cheese","saucepan", null, 1));
   steps2.add(new Step("Cook", "saucepan","saucepan", "until melted", 1));
   steps2.add(new Step("Put", "strainer","casserole", null, 1));
   steps2.add(new Step("Put", "saucepan","casserole", null, 1));
   steps2.add(new Step("Bake", "casserole","casserole", "at 350 degrees for 25 minutes", 1));
   steps2.add(new Step("Put", "tomato","casserole", null, 1));
   
   Recipe macaroniCheese = new Recipe(name2, serves2, ingredients2, utensils2, steps2);
   recipes.add(macaroniCheese);
   
   // Oven Fried Chicken
   String name3 = "Oven Fried Chicken";
   
   int serves3 = 8;
   
   List<RecipeIngredient> ingredients3 = new ArrayList<>();
   ingredients3.add(new RecipeIngredient("Egg", "beaten", 1, "individual"));
   ingredients3.add(new RecipeIngredient("Milk", null, 3, "tablespoon"));
   ingredients3.add(new RecipeIngredient("Saltine crackers", "finely crushed", 1, "cup"));
   ingredients3.add(new RecipeIngredient("Thyme", "dried crushed", 1, "teaspoon"));
   ingredients3.add(new RecipeIngredient("Paprika", null, .5, "teaspoon"));
   ingredients3.add(new RecipeIngredient("Pepper", "black", .1, "teaspoon"));
   ingredients3.add(new RecipeIngredient("Chicken", "pieces; skinned, rinsed and dried", 2, "pounds"));
   
   List<Utensil> utensils3 = new ArrayList<>();
   utensils3.add(new Utensil("Dish", "shallow"));
   utensils3.add(new Utensil("Bowl", "small"));
   utensils3.add(new Utensil("Baking pan", "15x10x1 greased"));
   
   List<Step> steps3 = new ArrayList<>();
   steps3.add(new Step("Put", "egg","bowl", null, 1));
   steps3.add(new Step("Put", "milk","bowl", null, 1));
   steps3.add(new Step("Put", "crackers","dish", null, 1));
   steps3.add(new Step("Put", "thyme","dish", null, 1));
   steps3.add(new Step("Put", "paprika","dish", null, 1));
   steps3.add(new Step("Put", "pepper","dish", null, 1));
   steps3.add(new Step("Dip", "chicken","bowl", null, 1));
   steps3.add(new Step("Dip", "chicken","dish", null, 1));
   steps3.add(new Step("Put", "chicken","baking pan", null, 1));
   steps3.add(new Step("Bake", "chicken","baking pan", "375 degrees for 55 minutes", 1));
   
   Recipe ovenFriedChicken = new Recipe(name3, serves3, ingredients3, utensils3, steps3);
   recipes.add(ovenFriedChicken);
   
   
   
   
 }
 
 
}
