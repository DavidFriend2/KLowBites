package Information;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ingredient class.
 * 
 * @author ryan mendez
 */
public class Ingredient implements Serializable, Comparable<Ingredient>
{

  private static final long serialVersionUID = 1L;
  private static List<Ingredient> ingredients = new ArrayList<>();
  private String name;
  private int caloriesPer100g;
  private double gramsPerMl;

  /**
   * Ingredient Constructor.
   * 
   * @param name
   *          of ingredient
   * @param caloriesPer100g
   *          of ingredient
   * @param gramsPerMl
   *          of ingredient
   */
  public Ingredient(final String name, final int caloriesPer100g, final double gramsPerMl)
  {
    this.name = name;
    this.caloriesPer100g = caloriesPer100g;
    this.gramsPerMl = gramsPerMl;

  }

  /**
   * Get name method.
   * 
   * @return name of ingredient
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get Calories per 100 grams method.
   * 
   * @return calories100g of ingredient
   */
  public int getCaloriesPer100g()
  {
    return caloriesPer100g;
  }

  /**
   * Get grams per Ml method.
   * 
   * @return grams per ml of ingredient
   */
  public double getGramsPerM()
  {
    return gramsPerMl;
  }

  /**
   * Get ingredient list method.
   * 
   * @return the saved list of ingredients
   */
  public static List<Ingredient> getIngredients()
  {
    ingredients.sort(null);
    return ingredients;
  }

  /**
   * Set Ingredients list method.
   * 
   * @param loaded
   *          list of ingredients to set as the saved list
   */
  public static void setIngredients(final List<Ingredient> loaded)
  {
    ingredients = loaded;
  }

  /**
   * Compare to method.
   * 
   * @param o
   *          ingredient to compare to
   * 
   * @return the result of the comparison
   */
  public int compareTo(final Ingredient o)
  {
    return 0;
  }

  /**
   * Get Ingredient by its name method.
   * 
   * @param name
   *          to search for
   * 
   * @return the Ingredient if found
   */
  public static Ingredient getIngredientbyName(final String name)
  {
    for (Ingredient ingredient : ingredients)
    {
      if (ingredient.name.equals(name))
      {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * To String method.
   * 
   * @return name
   */
  public String toString()
  {
    return this.name;
  }

  /**
   * Add Ingredient method.
   * 
   * @param ingredient
   *          to add to the saved list
   */
  public static void addIngredient(final Ingredient ingredient)
  {
    ingredients.add(ingredient);
  }

  /**
   * Load Ingredients list method.
   * 
   * @param path file
   *          of list of Ingredient objects to open
   * @return the loaded ingredients list
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @SuppressWarnings("unchecked")
  public static List<Ingredient> loadIngredients(final String path)
      throws IOException, ClassNotFoundException
  {
    File file = new File(path);
    if (!file.exists()) {
        // return  default ingredients list if the file doesn't exist
        return Ingredient.getIngredients();
    }
    
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
    {
      return (List<Ingredient>) input.readObject();
    }
  }

  /**
   * Save Ingredients list method.
   * 
   * @param path file
   *          to save the ingredients list to
   * @throws IOException
   */
  public static void saveIngredients(final String path) throws IOException
  {

    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path)))
    {
      output.writeObject(ingredients);
    }
  }

  /**
   * Predefined ingredients list
   */
  static
  {
    ingredients.add(new Ingredient("Alcohol", 275, 0.79));
    ingredients.add(new Ingredient("Almond", 601, .46));
    ingredients.add(new Ingredient("American cheese", 440, .34));
    ingredients.add(new Ingredient("Apple", 44, .56));
    ingredients.add(new Ingredient("Apple juice", 48, 1.04));

    ingredients.add(new Ingredient("Banana", 65, .56));
    ingredients.add(new Ingredient("Bean", 130, .77));
    ingredients.add(new Ingredient("Beef", 280, 1.05));
    ingredients.add(new Ingredient("Blackberry", 25, .53));
    ingredients.add(new Ingredient("Black pepper", 255, 1.01));
    ingredients.add(new Ingredient("Bread", 240, .42));
    ingredients.add(new Ingredient("Broccoli", 32, .37));
    ingredients.add(new Ingredient("Brown sugar", 380, 1.5));
    ingredients.add(new Ingredient("Butter", 750, .91));

    ingredients.add(new Ingredient("Cabbage", 28, 0.36));
    ingredients.add(new Ingredient("Carrot", 41, .64));
    ingredients.add(new Ingredient("Cashew", 553, .5));
    ingredients.add(new Ingredient("Cauliflower", 25, .27));
    ingredients.add(new Ingredient("Celery", 14, .61));
    ingredients.add(new Ingredient("Cheddar cheese", 440, .34));
    ingredients.add(new Ingredient("Cherry", 50, 1.02));
    ingredients.add(new Ingredient("Chicken", 200, 1.04));
    ingredients.add(new Ingredient("Chocolate", 500, 1.33));
    ingredients.add(new Ingredient("Cinnamon", 261, .45));
    ingredients.add(new Ingredient("Cod", 100, .58));
    ingredients.add(new Ingredient("Corn", 130, .72));
    ingredients.add(new Ingredient("Cornflake", 370, .12));
    ingredients.add(new Ingredient("Cottage cheese", 98, .96));
    ingredients.add(new Ingredient("Crab", 110, .61));
    ingredients.add(new Ingredient("Creme de cacao", 275, .79));
    ingredients.add(new Ingredient("Cucumber", 10, .67));

    ingredients.add(new Ingredient("Egg", 150, 0.6));

    ingredients.add(new Ingredient("Flour", 364, .45));

    ingredients.add(new Ingredient("Garlic", 111, .32));
    ingredients.add(new Ingredient("Grapefruit", 32, .33));
    ingredients.add(new Ingredient("Grape", 62, .37));
    ingredients.add(new Ingredient("Grape juice", 60, 1.04));
    ingredients.add(new Ingredient("Green bean", 31, .53));

    ingredients.add(new Ingredient("Haddock", 110, .58));
    ingredients.add(new Ingredient("Ham", 240, 1.4));
    ingredients.add(new Ingredient("Honey", 280, 1.5));

    ingredients.add(new Ingredient("Ice cream", 180, .55));

    ingredients.add(new Ingredient("Kidney bean", 333, .79));

    ingredients.add(new Ingredient("Lamb", 200, 1.3));
    ingredients.add(new Ingredient("lemon", 29, .77));
    ingredients.add(new Ingredient("Lentil", 116, .85));
    ingredients.add(new Ingredient("Lettuce", 15, .06));

    ingredients.add(new Ingredient("Macaroni", 371, 1.31));
    ingredients.add(new Ingredient("Milk", 70, 1.04));
    ingredients.add(new Ingredient("Mushroom", 15, 1.17));

    ingredients.add(new Ingredient("Oil", 900, .88));
    ingredients.add(new Ingredient("Olive", 80, .65));
    ingredients.add(new Ingredient("Onion", 22, .74));
    ingredients.add(new Ingredient("Orange", 30, .77));

    ingredients.add(new Ingredient("Paprika", 282, .46));
    ingredients.add(new Ingredient("Pasta", 371, 1.31));
    ingredients.add(new Ingredient("Peach", 30, .61));
    ingredients.add(new Ingredient("Peanut", 567, .53));
    ingredients.add(new Ingredient("Pear", 16, .61));
    ingredients.add(new Ingredient("Peas", 148, .73));
    ingredients.add(new Ingredient("Pepper", 20, .51));
    ingredients.add(new Ingredient("Pineapple", 40, .54));
    ingredients.add(new Ingredient("Plum", 39, .58));
    ingredients.add(new Ingredient("Pork", 290, .7));

    ingredients.add(new Ingredient("Rum", 275, .79));

    ingredients.add(new Ingredient("Salmon", 180, .58));
    ingredients.add(new Ingredient("Salt", 0, 1.38));
    ingredients.add(new Ingredient("Saltine crackers", 421, .43));
    ingredients.add(new Ingredient("Spaghetti", 371, 1.31));
    ingredients.add(new Ingredient("Spinach", 8, .08));
    ingredients.add(new Ingredient("Strawberries", 30, .58));
    ingredients.add(new Ingredient("Sugar", 400, .95));
    ingredients.add(new Ingredient("Sweet potato", 86, .65));
    ingredients.add(new Ingredient("Syrup", 260, 1.38));

    ingredients.add(new Ingredient("Thyme", 101, .46));
    ingredients.add(new Ingredient("Tomato", 20, .67));

    ingredients.add(new Ingredient("Wine", 83, .99));

  }

}
