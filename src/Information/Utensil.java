package Information;

import java.io.Serializable;

public class Utensil implements Serializable
{
  private static final long serialVersionUID = 1L;
  String name;
  String details;
  
  public Utensil(String name, String details)
  {
    this.name = name;
    this.details = details;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getDetails()
  {
    return this.details;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setDetails(String details)
  {
    this.details = details;
  }
  
}
