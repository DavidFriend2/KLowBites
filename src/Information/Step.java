package Information;

public class Step
{
  private String action;
  private Ingredient ingredient;
  private String on;
  private String destinationUtensil;
  private String details;
  
  public Step(String action, Ingredient ingredient, String sourceUtensil, String destinationUtensil, String details)
  {
    this.action = action;
    this.ingredient = ingredient;
    this.on = sourceUtensil;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    
  }
  
  public String getAction()
  {
    return this.action;
  }

  public Ingredient getIngredient()
  {
    return this.ingredient;
  }

  public String getSourceUtensilOrIngredient()
  {
    return this.on;
  }
  
  public String getDestinationUtensil()
  {
    return this.destinationUtensil;
  }
  public String getDetails()
  {
    return this.details;
  }
  
}
