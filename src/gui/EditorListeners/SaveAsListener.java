package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
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
  private String fileName;
  private List<JComponent> components;
  private JButton saveButton;
  private JButton closeButton;
  private JTextField pairing;
  
  public SaveAsListener(final JTextField nameText, final JTextField servesText, 
      final List<RecipeIngredient> fullIngredientList, 
      final List<Utensil> fullUtensilList, final List<Step> fullStepList, List<JComponent> components, 
      JButton saveButton, JButton closeButton, Locale locale, final JTextField pairText) 
  {
    this.name = nameText;
    this.serves = servesText;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
    this.components = components;
    this.saveButton = saveButton;
    this.closeButton = closeButton;
    this.strings = ResourceBundle.getBundle("resources.Strings", locale);
    this.pairing = pairText;
    
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
      fileName = fileChooser.getSelectedFile().getAbsolutePath();
      
      try 
      {
        // Create an updated recipe and save it to the chosen file name
        boolean check = true;
        for (char c : serves.getText().toCharArray()) {
          if (!Character.isDigit(c)) {
            check = false;
          }
        }
        if (serves.getText().isEmpty() || !check) {
          Recipe newRecipe = new Recipe(name.getText(), 0, 
              fullIngredientList, fullUtensilList, fullStepList, pairing.getText());
          newRecipe.saveRecipeToFile(fileName);
        } else {
          Recipe newRecipe = new Recipe(name.getText(), Integer.parseInt(serves.getText()), 
              fullIngredientList, fullUtensilList, fullStepList, pairing.getText());
          newRecipe.saveRecipeToFile(fileName);
        }
        new ChangeTracker(closeButton, components, saveButton);
        closeButton.setEnabled(true);
      } 
      catch (IOException ex) 
      {
        ex.printStackTrace();
      }
    }
  } 
  public String getFilename() {
    return fileName;
  }
}