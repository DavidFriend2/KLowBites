package Information;

import java.util.List;

public class Step
{
  private String action;
  private String on;
  private String destinationUtensil;
  private String details;
  private List<String> actions;
  
  public Step(String action, String on, String destinationUtensil, String details)
  {
    this.action = action;
    this.on = on;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    
    actions.add("put");
    actions.add("melt");
    actions.add("simmer");
    actions.add("heat");
    actions.add("ignite");
    
  }
  
  public String getAction()
  {
    return this.action;
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
