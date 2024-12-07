package Information;

import java.io.Serializable;

/**
 * Utensil Class
 * 
 * @author ryan mendez
 */
public class Utensil implements Serializable
{
  private static final long serialVersionUID = 1L;
  String name;
  String details;
  
  /**
   * Utensil constructor
   * 
   * @param name of utensil
   * @param details of utensil
   */
  public Utensil(String name, String details)
  {
    this.name = name;
    this.details = details;
  }
  
  /**
   * Get name method
   * 
   * @return name of the utensil
   */
  public String getName()
  {
    return this.name;
  }
  
  /**
   * Get details method
   * 
   * @return details of the utensil
   */
  public String getDetails()
  {
    return this.details;
  }
  
  /**
   * Set name method
   * 
   * @param name of the utensil
   */
  public void setName(String name)
  {
    this.name = name;
  }
  
  /**
   * Set details method
   * 
   * @param details of the utensil
   */
  public void setDetails(String details)
  {
    this.details = details;
  }
  
}
