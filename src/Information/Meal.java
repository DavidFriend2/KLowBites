package Information;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Meal implements Serializable
{
  private static final long serialVersionUID = 1L;
  String name;
  List<Recipe> recipes;
  
  public Meal(String name, List<Recipe> recipes)
  {
    this.name = name;
    this.recipes = recipes;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public List<Recipe> getRecipes(){
    return recipes;
  }
  
  public void addRecipe(Recipe recipe)
  {
    this.recipes.add(recipe);
  }
  
  public String getFileName()
  {
    return "meals/" +  this.name.replaceAll(" ", "_") + ".mel";
  }
  
  public String toString()
  {
    return this.name;
  }
  
  public void saveMealToFile(String file) throws IOException
  {
    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)))
    {
      output.writeObject(this);
    }
  }
  
  public static Meal loadMealFromFile(String file) throws IOException, ClassNotFoundException
  {
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
    {
      return (Meal) input.readObject();
    }
  }
  
}
