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
  private double timeEstimate;
  
  private static final List<String> actions = new ArrayList<>(); 
  
  public Step(String action, String on, String destinationUtensil, String details, double timeEstimate)
  {
    this.action = action;
    this.on = on;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    this.timeEstimate = timeEstimate;
  }
  
  // Getters to display and access
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
  
  public double getTime() 
  {
    return this.timeEstimate;
  }
  
  public static List<String> getActions()
  {
    return new ArrayList<>(actions);
  }
  
  //Setters for editing
  public void setAction(String action)
  {
    this.action = action;
  }

  public void setSourceUtensilOrIngredient(String on)
  {
    this.on = on;
  }
  
  public void setDestinationUtensil(String destUtensil)
  {
    this.destinationUtensil = destUtensil;
  }
  
  public void setDetails(String details)
  {
    this.details = details;
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
