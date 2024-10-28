package Information;

import java.util.List;
import java.util.Map;

public class Recipe
{
  private String name;
  private int serves;
  private List<RecipeIngredient> ingredients;
  private Map<String, String> utensils;
  private List<Step> steps;
  
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
 
 
}
