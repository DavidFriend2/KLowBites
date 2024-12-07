package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Information.Utensil;

/**
 * Utensil class test
 * 
 * @author ryan mendez
 */
class UtensilTest
{

  @Test
  void testConstructorAndGetters()
  {
    Utensil utensil = new Utensil("Pot", "Big");
    
    assertEquals("Pot", utensil.getName());
    assertEquals("Big", utensil.getDetails());
  }
  
  @Test
  void testSetName() 
  {
    Utensil utensil = new Utensil("Pan", "Big");
    
    utensil.setName("test");
    
    assertEquals("test", utensil.getName());
  }
  
  @Test
  void testSetDetails() 
  {
    Utensil utensil = new Utensil("Pan", "Big");
    
    utensil.setDetails("test");
    
    assertEquals("test", utensil.getDetails());
  }

}
