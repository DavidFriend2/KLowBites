/**
 * 
 */
package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;
import gui.RecipeProcessViewer;

/**
 * @author joshuabrowning.
 */
class ProcessViewerTest 
{
	
	private Recipe testRecipe;
  private RecipeProcessViewer processViewer;

  @BeforeEach
  public void setUp() 
  {
    // Set up a sample Recipe with utensils and steps
    Utensil utensil1 = new Utensil("Pan", "Non-stick 10-inch");
    Utensil utensil2 = new Utensil("Spatula", "Heat-resistant");
    Step step1 = new Step("Melt", "butter", "pan", "Low heat", 2);
    Step step2 = new Step("Add", "bananas", "pan", "Cook until golden", 8);
    RecipeIngredient recipeIngredient1 = new RecipeIngredient("Butter", null, .33, "cup");
    RecipeIngredient recipeIngredient2 = new RecipeIngredient("Brown sugar", null, .33, "cup");
    
    testRecipe = new Recipe("Bananas Foster", 3, Arrays.asList(recipeIngredient1,
        recipeIngredient2), Arrays.asList(utensil1, utensil2), Arrays.asList(step1, step2), null);
    processViewer = new RecipeProcessViewer(testRecipe);
  }

}
