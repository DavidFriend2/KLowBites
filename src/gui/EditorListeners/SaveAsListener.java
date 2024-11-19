package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

public class SaveAsListener implements ActionListener 
{
  private JTextField name;
  private JTextField serves;
  private List<RecipeIngredient> fullIngredientList;
  private List<Step> fullStepList;
  private List<Utensil> fullUtensilList;
  private ResourceBundle strings;
  
  public SaveAsListener(final JTextField nameText, final JTextField servesText, 
      final List<RecipeIngredient> fullIngredientList, 
      final List<Utensil> fullUtensilList, final List<Step> fullStepList, Locale locale) 
  {
    this.name = nameText;
    this.serves = servesText;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
    this.strings = ResourceBundle.getBundle("resources.Strings", locale);
  }
  
  @Override
  public void actionPerformed(final ActionEvent e) 
  {
    // Set localized strings for JFileChooser
    UIManager.put("FileChooser.saveInLabelText", strings.getString("file_chooser_save_in"));
    UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
    UIManager.put("FileChooser.folderNameLabelText", strings.getString("file_chooser_folder_name"));
    UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
    UIManager.put("FileChooser.cancelButtonText", strings.getString("file_chooser_cancel_button"));
    UIManager.put("FileChooser.filesOfTypeLabelText", strings.getString("file_chooser_files_of_type"));
    UIManager.put("FileChooser.lookInLabelText", strings.getString("file_chooser_look_in"));
    UIManager.put("FileChooser.saveDialogTitleText", strings.getString("file_chooser_save_dialog_title"));

    // Allow user to type in filename
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle(strings.getString("save_recipe_as_dialog_title"));
    int userSelection = fileChooser.showSaveDialog(null);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) 
    {
      String fileName = fileChooser.getSelectedFile().getAbsolutePath();
      
      try 
      {
        // Create an updated recipe and save it to the chosen file name
        Recipe newRecipe = new Recipe(name.getText(), Integer.parseInt(serves.getText()), 
            fullIngredientList, fullUtensilList, fullStepList);
        newRecipe.saveRecipeToFile(fileName);
      } 
      catch (IOException ex) 
      {
        ex.printStackTrace();
      }
    }
  }  
}