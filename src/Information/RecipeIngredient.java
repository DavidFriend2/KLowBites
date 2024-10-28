package Information;

public class RecipeIngredient
{
  private Ingredient ingredient;
  private String name;
  private String details;
  private int amount;
  private String unit;
  
  public RecipeIngredient(String name, String details, int amount, String unit)
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

  public int getAmount()
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
