package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;
import gui.RecipeEditor;

public class SaveListener implements ActionListener 
{
  
  OpenListener openListener;
  SaveAsListener saveAsListener;
  JTextField name;
  JTextField serves;
  List<RecipeIngredient> fullIngredientList;
  List<Step> fullStepList;
  List<Utensil> fullUtensilList;
  JButton saveButton;
  JButton closeButton;
  JButton newButton;
  List<JComponent> components;
  JTextField pairing;

  public SaveListener(final OpenListener openListener, SaveAsListener saveAsListener, final JTextField name, 
      final JTextField serves, final List<RecipeIngredient> fullIngredientList, 
      final List<Step> fullStepList, final List<Utensil> fullUtensilList, 
      JButton saveButton, JButton closeButton, JButton newButton, List<JComponent> components, 
      final JTextField pairing)
  {
    this.openListener = openListener;
    this.saveAsListener = saveAsListener;
    this.name = name;
    this.serves = serves;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
    this.saveButton = saveButton;
    this.closeButton = closeButton;
    this.newButton = newButton;
    this.components = components;
    this.pairing = pairing;
  }
  
  @Override
  public void actionPerformed(final ActionEvent e) 
  {
    try 
    {
      String fileName;
      if (openListener.getCurrentFileName() == null) {
        fileName = saveAsListener.getFilename();
      } else {
        fileName = openListener.getCurrentFileName();
      }
      
      boolean check = true;
      for (char c : serves.getText().toCharArray()) {
        if (!Character.isDigit(c)) {
          check = false;
        }
      }
      Recipe updatedRecipe;
      if (serves.getText().isEmpty() || !check) {
        updatedRecipe = new Recipe(name.getText(), 0, fullIngredientList, fullUtensilList, fullStepList, 
            pairing.getText());
      } else {
        updatedRecipe = new Recipe(name.getText(), Integer.parseInt(serves.
            getText()), fullIngredientList, fullUtensilList, fullStepList, pairing.getText());
      }
      
      // If new Recipe
      if (fileName == null)
      {
        fileName = updatedRecipe.getFileName();
      }
      
      // Save recipe to its file
      updatedRecipe.saveRecipeToFile(fileName);
      saveButton.setEnabled(false);
      closeButton.setEnabled(true);
      new ChangeTracker(closeButton, newButton, components, saveButton);
    } catch(IOException ex) 
    {
      ex.printStackTrace();
    }
    
  }
  
  public Recipe getRecipe() 
  {
    return new Recipe(name.getText(), Integer.parseInt(serves.
        getText()), fullIngredientList, fullUtensilList, fullStepList, pairing.getText());
  }
  
}
