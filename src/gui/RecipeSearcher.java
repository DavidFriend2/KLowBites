package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Information.*;

/*
 * Recipe Searcher Class
 * 
 * @author Ryan Mendez
 * 
 * Allows users to search for recipes from a directory that contain given ingredients
 * Clicking on the recipes opens a process viewer for the users.
 * 
 */
public class RecipeSearcher extends JFrame
{

  private static final long serialVersionUID = 1L;
  private ResourceBundle strings;

  private List<Ingredient> containedIngredients = new ArrayList<>();
  private JList<Ingredient> ingredientJList;
  private DefaultListModel<Ingredient> ingredientDml;
  private JTextField ingredientField;

  private List<Recipe> selectedRecipes = new ArrayList<>();
  private JComboBox<Recipe> recipeDropdown;

  public RecipeSearcher(final Locale locale) 
  {
    strings = ResourceBundle.getBundle("resources.Strings", locale); 
    // Load the resource bundle based on locale
    setTitle(strings.getString("recipe_searcher_title")); // Set window title
    setSize(350, 500);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Main Panel
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Directory chooser Panel
    JPanel chooserPanel = new JPanel();
    JLabel chooserLabel = new JLabel(strings.getString("choose_directory_label")); 
    // Internationalized label
    JButton chooseDirectoryButton = new JButton(strings.getString("choose_button")); 
    // Internationalized button
    chooseDirectoryButton.addActionListener(new OpenRecipesListener());
    chooserPanel.add(chooserLabel);
    chooserPanel.add(chooseDirectoryButton);

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(chooserPanel, BorderLayout.WEST);

    // Ingredients panel
    JPanel ingredientPanel = new JPanel();
    ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
    
    // Label for ingredients
    JLabel ingredientLabel = new JLabel(strings.getString("add_ingredients_label")); 
    // Internationalized label
    ingredientLabel.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(ingredientLabel);
    
    // Text field for ingredients
    ingredientField = new JTextField(15);
    ingredientField.setMaximumSize(ingredientField.getPreferredSize());
    ingredientPanel.add(ingredientField);
    
    // Add ingredient button
    JButton addIngredientButton = new JButton(strings.getString("add_button")); 
    // Internationalized button
    addIngredientButton.addActionListener(new AddIngredientListener());
    addIngredientButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(addIngredientButton);
    
    // Ingredient JList
    ingredientDml = new DefaultListModel<>();
    ingredientJList = new JList<>(ingredientDml);
    ingredientJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ingredientPanel.add(new JScrollPane(ingredientJList));
    
    // Delete button for ingredients
    JButton deleteIngredientButton = new JButton(strings.getString("delete_ingredient_button")); 
    // Internationalized button
    deleteIngredientButton.addActionListener(new DeleteIngredientListener());
    deleteIngredientButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(deleteIngredientButton);
    
    // Search button for recipes
    JButton searchButton = new JButton(strings.getString("search_recipes_button")); 
    // Internationalized button
    searchButton.addActionListener(new SearchRecipesListener());
    searchButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(searchButton);

    // Recipe Display List (Dropdown)
    recipeDropdown = new JComboBox<>();

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(ingredientPanel, BorderLayout.CENTER);
    mainPanel.add(recipeDropdown, BorderLayout.SOUTH);
    
    this.add(mainPanel);
  }

  /*
   * Open Recipes Listener
   * 
   * Opens all recipe files and loads them into the lists
   * 
   */
  private class OpenRecipesListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      // Set localized strings for JFileChooser
      UIManager.put("FileChooser.folderNameLabelText", strings.getString("file_chooser_folder_name")); // Add this line
      UIManager.put("FileChooser.saveInLabelText", strings.getString("file_chooser_save_in"));
      UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
      UIManager.put("FileChooser.filesOfTypeLabelText", strings.getString("file_chooser_files_of_type"));
      UIManager.put("FileChooser.upFolderToolTipText", strings.getString("file_chooser_up_folder"));
      UIManager.put("FileChooser.homeFolderToolTipText", strings.getString("file_chooser_home_folder"));
      UIManager.put("FileChooser.newFolderToolTipText", strings.getString("file_chooser_new_folder"));
      UIManager.put("FileChooser.listViewButtonToolTipText", strings.getString("file_chooser_list_view"));
      UIManager.put("FileChooser.detailsViewButtonToolTipText", strings.getString("file_chooser_details_view"));
      UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
      UIManager.put("FileChooser.openButtonText", strings.getString("file_chooser_open_button"));
      UIManager.put("FileChooser.cancelButtonText", strings.getString("file_chooser_cancel_button"));
      UIManager.put("FileChooser.acceptAllFileFilterText", strings.getString("file_chooser_all_files"));
      // Allow user to select a directory to search through
      JFileChooser directoryChooser = new JFileChooser();
      directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      directoryChooser.setDialogTitle("Chose Directory");

      int userSelection = directoryChooser.showSaveDialog(null);

      if (userSelection == JFileChooser.APPROVE_OPTION)
      {
        File directory = directoryChooser.getSelectedFile();
        loadAllRecipes(directory);
      }

    }

  }

  private void loadAllRecipes(final File directory)
  {
    File[] files = directory.listFiles((dir, name) -> name.endsWith(".rcp"));

    if (files.length > 0)
    {
      selectedRecipes.clear();

      for (File file : files)
      {
        try
        {
          Recipe recipe = Recipe.loadRecipeFromFile(file.getAbsolutePath());
          selectedRecipes.add(recipe);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    else
    {
      // status.setText("No recipe files found");
    }

  }

  /*
   * Add Ingredient Listener
   */
  private class AddIngredientListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent e)
    {
      String ingredientName = ingredientField.getText();
      if (!ingredientName.isEmpty())
      {
        Ingredient ingredient = Ingredient.getIngredientbyName(ingredientName);
        containedIngredients.add(ingredient);
        ingredientDml.addElement(ingredient);
        ingredientField.setText("");
      }
    }
  }

  private class DeleteIngredientListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      int selectedIngredient = ingredientJList.getSelectedIndex();

      if (selectedIngredient != -1)
      {
        Ingredient ingredientToRemove = ingredientDml.get(selectedIngredient);
        containedIngredients.remove(ingredientToRemove);
        ingredientDml.remove(selectedIngredient);
      }

    }

  }

  private class SearchRecipesListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      List<Recipe> searchedRecipes = new ArrayList<>();

      // search every recipe
      for (Recipe recipe : selectedRecipes)
      {
        List<RecipeIngredient> recipeIngredients = recipe.getIngredients();
        boolean containsIngredients = true;

        // Check if each given ingredient is in a recipe
        for (Ingredient ingredient : containedIngredients)
        {
          boolean ingredientFound = false;

          // loop thru every ingredient in recipe to see if the give ingredient matches one
          for (RecipeIngredient ri : recipeIngredients)
          {
            if (ri.getName().equals(ingredient.getName()))
            {
              ingredientFound = true;
              break;

            }

          }

          // If one ingredient isnt found recipe wont be given, break out
          if (!ingredientFound)
          {
            containsIngredients = false;
            break;
          }

        }

        if (containsIngredients)
        {
          searchedRecipes.add(recipe);
        }

      }

      recipeDropdown.removeActionListener(recipeDropdownListener);
      recipeDropdown.removeAllItems();

      for (Recipe recipe : searchedRecipes)
      {
        recipeDropdown.addItem(recipe);
      }
      recipeDropdown.addActionListener(recipeDropdownListener);
      recipeDropdown.setSelectedIndex(-1);

    }

  }

  private ActionListener recipeDropdownListener = e -> {
    Recipe selectedRecipe = (Recipe) recipeDropdown.getSelectedItem();
    if (selectedRecipe != null)
    {
      new RecipeProcessViewer(selectedRecipe).setVisible(true);
    }
  };

  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> 
    {
      Locale locale = Locale.ITALIAN; // Or any other Locale
      RecipeSearcher recipeSearcher = new RecipeSearcher(locale);
      recipeSearcher.setVisible(true);
    });
  }

}
