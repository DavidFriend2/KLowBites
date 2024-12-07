package Information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Step class
 * 
 * @author ryan mendez
 */
public class Step implements Serializable
{
  private static final List<String> ACTIONS = new ArrayList<>(); 
  private static final long serialVersionUID = 1L;
  private String action;
  private String on;
  private String destinationUtensil;
  private String details;
  private double timeEstimate;
    
  /**
   * Step constructor
   * 
   * @param action for step, boil, bake etc
   * @param on what to perform the action on
   * @param destinationUtensil the  destination to put the result in
   * @param details for step
   * @param timeEstimate of step
   */
  public Step(final String action, final String on, final String destinationUtensil, 
      final String details, final double timeEstimate)
  {
    this.action = action;
    this.on = on;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    this.timeEstimate = timeEstimate;
  }
  
  /**
   * Get action method
   * 
   * @return action of step
   */
  public String getAction()
  {
    return this.action;
  }

  /**
   * Get on (Source utensil or ingredient) method
   * 
   * @return on of step
   */
  public String getSourceUtensilOrIngredient()
  {
    return this.on;
  }
  
  /**
   * Get destination utensil method
   * 
   * @return the destination utensil of step
   */
  public String getDestinationUtensil()
  {
    return this.destinationUtensil;
  }
  
  /**
   * Get details method
   * 
   * @return details of step
   */
  public String getDetails()
  {
    return this.details;
  }
  
  /**
   * Get time estimate method
   * 
   * @return time estimate of step
   */
  public double getTime() 
  {
    return this.timeEstimate;
  }
  
  /**
   * Get actions list method
   * 
   * @return list of preset actions
   */
  public static List<String> getActions()
  {
    return new ArrayList<>(ACTIONS);
  }
  
  /**
   * Set action method
   * 
   * @param action of step
   */
  public void setAction(final String action)
  {
    this.action = action;
  }

  /**
   * Set on (Source utensil or ingredient) method
   * 
   * @param on of step
   */
  public void setSourceUtensilOrIngredient(final String on)
  {
    this.on = on;
  }
  
  /**
   * Set destination utensil method
   * 
   * @param the destination utensil of step
   */
  public void setDestinationUtensil(final String destUtensil)
  {
    this.destinationUtensil = destUtensil;
  }
  
  /**
   * Set details method
   * 
   * @param details of step
   */
  public void setDetails(final String details)
  {
    this.details = details;
  }
  
  /**
   * Predefined actions list
   */
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
