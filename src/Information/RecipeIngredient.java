package Information;

import java.io.Serializable;

/**
 * Recipe Ingredient Class.
 * 
 * @author ryan mendez
 */
public class RecipeIngredient implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String name;
  private String details;
  private double amount;
  private String unit;
  
  /**
   * Recipe Ingredient Constructor.
   * 
   * @param name of ingredient
   * @param details for ingredient
   * @param amount of ingredient
   * @param unit for ingredient
   */
  public RecipeIngredient(final String name, final String details, 
      final double amount, final String unit)
  {
    this.name = name;
    this.details = details;
    this.amount = amount;
    this.unit = unit;
  }
  
  /**
   * Get name method.
   * 
   * @return name of recipe ingredient
   */
  public String getName()
  {
    return this.name;
  }
  
  /**
   * Get details method.
   * 
   * @return details for the recipe ingredient
   */
  public String getDetails()
  {
    return this.details;
  }

  /**
   * Get amount method.
   * 
   * @return amount of the recipe ingredient
   */
  public double getAmount()
  {
    return this.amount;
  }
  
  /**
   * Get unit method.
   * 
   * @return unit for the recipe ingredient
   */
  public String getUnit()
  {
    return this.unit;
  }
  
  /**
   * Get Ingredient object method.
   * 
   * Returns the associated ingredient object based off its name for calorie and grams per ml info
   * 
   * @return The ingredient object
   */
  public Ingredient getIngredient()
  {
    return Ingredient.getIngredientbyName(name);
  }
  
  /**
   * To string method.
   */
  @Override
  public String toString()
  {
    return String.format("%.3f %s %s", this.amount, this.unit, this.name.toLowerCase());
  }
  
  /**
   * Set name method.
   * 
   * @param name to set the recipe ingredient
   */
  public void setName(final String name)
  {
    this.name = name;
  }
  
  /**
   * Set details method.
   * 
   * @param details to set the recipe ingredient
   */
  public void setDetails(final String details)
  {
    this.details = details;
  }

  /**
   * Set amount method.
   * 
   * @param amount to set the recipe ingredient
   */
  public void setAmount(final double amount)
  {
    this.amount = amount;
  }
  
  /**
   * Set unit method.
   * 
   * @param unit to set the recipe ingredient
   */
  public void setUnit(final String unit)
  {
    this.unit = unit;
  }
  
}
