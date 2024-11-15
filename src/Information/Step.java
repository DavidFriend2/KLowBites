package Information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Step implements Serializable
{
  private static final List<String> ACTIONS = new ArrayList<>(); 
  private static final long serialVersionUID = 1L;
  private String action;
  private String on;
  private String destinationUtensil;
  private String details;
  private double timeEstimate;
    
  public Step(final String action, final String on, final String destinationUtensil, 
      final String details, final double timeEstimate)
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
    return new ArrayList<>(ACTIONS);
  }
  
  //Setters for editing
  public void setAction(final String action)
  {
    this.action = action;
  }

  public void setSourceUtensilOrIngredient(final String on)
  {
    this.on = on;
  }
  
  public void setDestinationUtensil(final String destUtensil)
  {
    this.destinationUtensil = destUtensil;
  }
  
  public void setDetails(final String details)
  {
    this.details = details;
  }
  
  static 
  {
    ACTIONS.add("Bake");
    ACTIONS.add("Boil");
    ACTIONS.add("Cook");
    ACTIONS.add("Dip");
    ACTIONS.add("Drain");
    ACTIONS.add("Heat");
    ACTIONS.add("Ignite");
    ACTIONS.add("Melt");
    ACTIONS.add("Put");
    ACTIONS.add("Saute");
    ACTIONS.add("Simmer");
    
  }
  
}
