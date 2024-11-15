package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

public class SaveAsListener implements ActionListener 
{
  JTextField name;
  JTextField serves;
  List<RecipeIngredient> fullIngredientList;
  List<Step> fullStepList;
  List<Utensil> fullUtensilList;
  
  public SaveAsListener(final JTextField nameText, final JTextField servesText, 
      final List<RecipeIngredient> fullIngredientList, 
      final List<Utensil> fullUtensilList, final List<Step> fullStepList) 
  {
    this.name = nameText;
    this.serves = servesText;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
  }
  
  @Override
  public void actionPerformed(final ActionEvent e) 
  {
    //Allow user to type in filename
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Recipe As");
    int userSelection = fileChooser.showSaveDialog(null);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) 
    {
      String fileName = fileChooser.getSelectedFile().getAbsolutePath();
      
      try 
      {
          // Create an updated recipe and save it to the chosen file name
        Recipe newRecipe = new Recipe(name.getText(), Integer.parseInt(serves.
            getText()), fullIngredientList, fullUtensilList, fullStepList);
        newRecipe.saveRecipeToFile(fileName);
      } 
      catch (IOException ex) 
      {
        ex.printStackTrace();
      }
    }
    
  }  
  
}
