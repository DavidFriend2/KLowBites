package Information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe
{
  private String name;
  private int serves;
  private List<RecipeIngredient> ingredients;
  private Map<String, String> utensils;
  private List<Step> steps;
  
  private static List<Recipe> recipes = new ArrayList<>();;
  
 public Recipe(String name, int serves, List<RecipeIngredient> ingredients, Map<String, String> utensils, List<Step> steps)
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
 
 public Map<String, String> getUtenils()
 {
   return this.utensils;
 }
 
 public List<Step> getSteps()
 {
   return this.steps;
 }
 
 static {
   //Bananas Foster
   String name = "Bananas Foster";
   
   int serves = 3;
   
   List<RecipeIngredient> ingredients = new ArrayList<>();
   ingredients.add(new RecipeIngredient("Butter", null, .33, "cup"));
   ingredients.add(new RecipeIngredient("Brown sugar", null, .33, "cup"));
   ingredients.add(new RecipeIngredient("Banana", "sliced rupe", 3, "indivdual"));
   ingredients.add(new RecipeIngredient("Creme de cacao", null, 2, "tablespoon"));
   ingredients.add(new RecipeIngredient("Rum", null, .25, "cup"));
   ingredients.add(new RecipeIngredient("Ice cream", "vanilla", 2, "cup"));
   ingredients.add(new RecipeIngredient("Cinnamon", "ground", .25, "teaspoon"));
   
   Map<String, String> utensils = new HashMap<>();
   utensils.put("Skillet", "large");
   utensils.put("Saucepan", null);
   utensils.put("Plate", null);
   
   List<Step> steps = new ArrayList<>();
   steps.add(new Step("put", "Butter","Skillet", null));
   steps.add(new Step("melt", "Skillet","Skillet", null));
   steps.add(new Step("put", "Brown sugar","Skillet", null));
   steps.add(new Step("put", "Bananas","Skillet", null));
   steps.add(new Step("simmer", "Skillet","Skillet", "for 2 minutes"));
   steps.add(new Step("put", "Cinnamon","Skillet", null));
   steps.add(new Step("put", "Creme de cacao","Skillet", null));
   steps.add(new Step("put", "Skillet","Plate", null));
   steps.add(new Step("heat", "Rum","Saucepan", "until it almost simmers"));
   steps.add(new Step("ignite", "Rum","Saucepan", null));
   steps.add(new Step("put", "Saucepan","plate", null));
   steps.add(new Step("put", "Ice cream","plate", null));
   
   Recipe bananasFoster = new Recipe(name, serves, ingredients, utensils, steps);
   recipes.add(bananasFoster);
   
   
 }
 
 
}
