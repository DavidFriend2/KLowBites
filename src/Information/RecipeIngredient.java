package Information;

import java.io.Serializable;

public class RecipeIngredient implements Serializable
{
  private static final long serialVersionUID = 1L;
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
  }
  
  //Getters
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
    return Ingredient.getIngredientbyName(name);
  }
  
  @Override
  public String toString()
  {
    return String.format("%.3f %s %s", this.amount, this.unit, this.name.toLowerCase());
  }
  
  //Setters
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setDetails(String details)
  {
    this.details = details;
  }

  public void setAmount(double amount)
  {
    this.amount = amount;
  }
  
  public void setUnit(String unit)
  {
    this.unit = unit;
  }
  
}
