package Information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Step implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String action;
  private String on;
  private String destinationUtensil;
  private String details;
  
  private static final List<String> actions = new ArrayList<>(); 
  
  public Step(String action, String on, String destinationUtensil, String details)
  {
    this.action = action;
    this.on = on;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    
    
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
  
  public static List<String> getActions()
  {
    return new ArrayList<>(actions);
  }
  
  static 
  {
    actions.add("Bake");
    actions.add("Boil");
    actions.add("Cook");
    actions.add("Dip");
    actions.add("Drain");
    actions.add("Heat");
    actions.add("Ignite");
    actions.add("Melt");
    actions.add("Put");
    actions.add("Saute");
    actions.add("Simmer");
    
  }
  
}
