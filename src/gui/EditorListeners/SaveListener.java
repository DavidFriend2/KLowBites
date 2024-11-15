package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextField;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

public class SaveListener implements ActionListener 
{
  
  OpenListener openListener;
  JTextField name;
  JTextField serves;
  List<RecipeIngredient> fullIngredientList;
  List<Step> fullStepList;
  List<Utensil> fullUtensilList;

  public SaveListener(final OpenListener openListener, final JTextField name, 
      final JTextField serves, final List<RecipeIngredient> fullIngredientList, 
      final List<Step> fullStepList, final List<Utensil> fullUtensilList)
  {
    this.openListener = openListener;
    this.name = name;
    this.serves = serves;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
  }
  
  @Override
  public void actionPerformed(final ActionEvent e) 
  {
    try 
    {
      String fileName = openListener.getCurrentFileName();
      Recipe updatedRecipe = new Recipe(name.getText(), Integer.parseInt(serves.
          getText()), fullIngredientList, fullUtensilList, fullStepList);
      
      // If new Recipe
      if (fileName == null)
      {
        fileName = updatedRecipe.getFileName();
      }
      
      // Save recipe to its file
      updatedRecipe.saveRecipeToFile(fileName);
      
    } catch(IOException ex) 
    {
      ex.printStackTrace();
    }
    
  }
  
  public Recipe getRecipe() 
  {
    return new Recipe(name.getText(), Integer.parseInt(serves.
        getText()), fullIngredientList, fullUtensilList, fullStepList);
  }
  
}
