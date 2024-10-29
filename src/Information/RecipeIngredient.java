package Information;

import java.io.Serializable;

public class RecipeIngredient implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Ingredient ingredient;
  private String name;
  private String details;
  private double amount;
  private String unit;
  
  public RecipeIngredient(String name, String details, double amount, String unit)
  {
    this.name = name;
    this.details = details;
    this.amount = amount;
    this.unit = unit;
    this.ingredient = Ingredient.getIngredientbyName(name);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getDetails()
  {
    return this.details;
  }

  public double getAmount()
  {
    return this.amount;
  }
  
  public String getUnit()
  {
    return this.unit;
  }
  
  public Ingredient getIngredient()
  {
    return this.ingredient;
  }
  
}
