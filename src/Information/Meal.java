package Information;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Meal class
 * 
 * @author ryan mendez
 */
public class Meal implements Serializable
{
  private static final long serialVersionUID = 1L;
  String name;
  List<Recipe> recipes;
  
  /**
   * Meal constructor
   * 
   * @param name
   * @param recipes
   */
  public Meal(final String name, final List<Recipe> recipes)
  {
    this.name = name;
    this.recipes = recipes;
  }
  
  /**
   * Get name method
   * 
   * @return name of meal
   */
  public String getName()
  {
    return this.name;
  }
  
  /**
   * Get recipes in meal method
   * 
   * @return the recipes in the meal
   */
  public List<Recipe> getRecipes(){
    return recipes;
  }
  
  /**
   * Add recipe to meal method
   * 
   * @param recipe to add
   */
  public void addRecipe(final Recipe recipe)
  {
    this.recipes.add(recipe);
  }
  
  /**
   * Get filename method
   * 
   * @return filename for meal
   */
  public String getFileName()
  {
    return "meals/" +  this.name.replaceAll(" ", "_") + ".mel";
  }
  
  /**
   * To string method
   * 
   * @return String rep of meal
   */
  public String toString()
  {
    return this.name;
  }
  
  /**
   * Save meal to file method
   * 
   * @param file
   * @throws IOException
   */
  public void saveMealToFile(final String file) throws IOException
  {
    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)))
    {
      output.writeObject(this);
    }
  }
  
  /**
   * Load meal from file method
   * 
   * @param file
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Meal loadMealFromFile(final String file) throws IOException, ClassNotFoundException
  {
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
    {
      return (Meal) input.readObject();
    }
  }
  
}
