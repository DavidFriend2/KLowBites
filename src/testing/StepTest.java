package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import Information.Step;

class StepTest
{

  @Test
  void testConstrcutorAndGetters()
  {
    Step step = new Step("Boil", "water", "Pot", "to a boil", 1.5);
    
    assertEquals("Boil", step.getAction());
    assertEquals("water", step.getSourceUtensilOrIngredient());
    assertEquals("Pot", step.getDestinationUtensil());
    assertEquals("to a boil", step.getDetails());
    assertEquals(1.5, step.getTime());
  }
  
  @Test 
  void testSetters()
  {
    Step step = new Step("Boil", "water", "Pot", "to a boil", 1.5);
    
    step.setAction("action");
    step.setSourceUtensilOrIngredient("on");
    step.setDestinationUtensil("dest");
    step.setDetails("details");
    
    assertEquals("action", step.getAction());
    assertEquals("on", step.getSourceUtensilOrIngredient());
    assertEquals("dest", step.getDestinationUtensil());
    assertEquals("details", step.getDetails());
  }

  @Test
  void testGetActions() 
  {
    List<String> actions = Step.getActions();
    assertTrue(actions.contains("Bake"));
    assertTrue(actions.contains("Boil"));
    assertTrue(actions.contains("Cook"));
    assertTrue(actions.contains("Dip"));
    assertTrue(actions.contains("Drain"));
    assertTrue(actions.contains("Heat"));
    assertTrue(actions.contains("Ignite"));
    assertTrue(actions.contains("Melt"));
    assertTrue(actions.contains("Put"));
    assertTrue(actions.contains("Simmer"));
    assertTrue(actions.contains("Saute"));
    
  }
}
