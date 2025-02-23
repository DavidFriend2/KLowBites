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

/**
 * Meal Searcher Class.
 * 
 * @author Ryan Mendez
 * 
 * Allows users to search for meals from a directory that contain given ingredients
 * Clicking on the meals opens a meal process viewer for the users.
 * 
 */
public class MealSearcher extends JFrame 
{

  private static final long serialVersionUID = 1L;
  private ResourceBundle strings; // Resource bundle for internationalization

  private List<Ingredient> containedIngredients = new ArrayList<>();
  private JList<Ingredient> ingredientJList;
  private DefaultListModel<Ingredient> ingredientDml;
  private JTextField ingredientField;

  private List<Meal> selectedMeals = new ArrayList<>();
  private JComboBox<Meal> mealDropdown;

  /**
   * Meal Searcher Constructor.
   * 
   * @param locale
   */
  public MealSearcher(final Locale locale) 
  {
    strings = ResourceBundle.getBundle("resources.Strings", 
        locale); // Load the resource bundle based on locale
    setTitle(strings.getString("meal_searcher_title")); // Set window title
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
    chooseDirectoryButton.addActionListener(new OpenMealsListener());
    chooserPanel.add(chooserLabel);
    chooserPanel.add(chooseDirectoryButton);

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(chooserPanel, BorderLayout.WEST);

    // Ingredients panel
    JPanel ingredientPanel = new JPanel();
    ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
    
    // Label for ingredients
    JLabel ingredientLabel = new JLabel(strings.getString("add_ingredients_meal_label")); 
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
    
    // Search button for meals
    JButton searchButton = new JButton(strings.getString("search_meals_button")); 
    // Internationalized button
    searchButton.addActionListener(new SearchMealsListener());
    searchButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(searchButton);

    // Meal Display List (Dropdown)
    mealDropdown = new JComboBox<>();

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(ingredientPanel, BorderLayout.CENTER);
    mainPanel.add(mealDropdown, BorderLayout.SOUTH);
    
    this.add(mainPanel);
  }

  /*
   * Open Recipes Listener
   * 
   * Opens all recipe files and loads them into the lists
   * 
   */
  private class OpenMealsListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent e)
    {
      // Set localized strings for JFileChooser
      UIManager.put("FileChooser.folderNameLabelText", 
          strings.getString("file_chooser_folder_name")); // Add this line
      UIManager.put("FileChooser.lookInLabelText", strings.getString("file_chooser_look_in"));
      UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
      UIManager.put("FileChooser.filesOfTypeLabelText", 
          strings.getString("file_chooser_files_of_type"));
      UIManager.put("FileChooser.upFolderToolTipText", strings.getString("file_chooser_up_folder"));
      UIManager.put("FileChooser.homeFolderToolTipText", 
          strings.getString("file_chooser_home_folder"));
      UIManager.put("FileChooser.newFolderToolTipText", 
          strings.getString("file_chooser_new_folder"));
      UIManager.put("FileChooser.listViewButtonToolTipText", 
          strings.getString("file_chooser_list_view"));
      UIManager.put("FileChooser.detailsViewButtonToolTipText", 
          strings.getString("file_chooser_details_view"));
      UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
      UIManager.put("FileChooser.openButtonText", strings.getString("file_chooser_open_button"));
      UIManager.put("FileChooser.cancelButtonText", 
          strings.getString("file_chooser_cancel_button"));
      UIManager.put("FileChooser.acceptAllFileFilterText", 
          strings.getString("file_chooser_all_files"));

      // Allow user to select a directory to search through
      JFileChooser directoryChooser = new JFileChooser();
      directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      directoryChooser.setDialogTitle(strings.getString("choose_directory_dialog_title"));

      int userSelection = directoryChooser.showDialog(null, 
          strings.getString("file_chooser_select_button"));

      if (userSelection == JFileChooser.APPROVE_OPTION)
      {
        File directory = directoryChooser.getSelectedFile();
        loadAllMeals(directory);
      }
    }
  }
  
  /**
   * Load all meals from a directory method.
   * 
   * @param directory to load
   */
  private void loadAllMeals(final File directory)
  {
    File[] files = directory.listFiles((dir, name) -> name.endsWith(".mel"));

    if (files.length > 0)
    {
      selectedMeals.clear();

      for (File file : files)
      {
        try
        {
          Meal meal = Meal.loadMealFromFile(file.getAbsolutePath());
          selectedMeals.add(meal);

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

  /**
   * Add Ingredient Listener.
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

  /**
   * Delete Ingredient Listener.
   */
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
  
  /**
   * Search for Meals Listener.
   */
  private class SearchMealsListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      List<Meal> searchedMeals = new ArrayList<>();

      // search every recipe
      for (Meal meal : selectedMeals)
      {
        boolean mealContainsAllIngredients = true;

        // every recipe in each meal
        for (Recipe recipe : meal.getRecipes())
        {
          boolean containsIngredients = true;

          // Check if each given ingredient is in a recipe
          for (Ingredient ingredient : containedIngredients)
          {
            boolean ingredientFound = false;

            // loop thru every ingredient in recipe to see if the give ingredient matches one
            for (RecipeIngredient ri : recipe.getIngredients())
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
          
          //If one recipe contains the ingredients were done looking
          if (containsIngredients)
          {
            mealContainsAllIngredients = true;
            break;
          }
        }

        if (mealContainsAllIngredients)
        {
          searchedMeals.add(meal);
        }

      }

      // status.append("\nFound " + searchedRecipes.size() + " recipes");
      mealDropdown.removeActionListener(mealDropdownListener);
      mealDropdown.removeAllItems();

      for (Meal meal : searchedMeals)
      {
        mealDropdown.addItem(meal);
      }
      mealDropdown.addActionListener(mealDropdownListener);
      mealDropdown.setSelectedIndex(-1);

    }

  }

  /**
   * Drop down listener-- not needed after removing process viewer.
   */
  private ActionListener mealDropdownListener = e -> {
    Meal selectedMeal = (Meal) mealDropdown.getSelectedItem();
    if (selectedMeal != null)
    {
      // new MealProcessViewer(selectedMeal).setVisible(true);
    }
  };
  
  
  /**
   * Main program.
   * @param args
   */
  public static void main(final String[] args) 
  {
    SwingUtilities.invokeLater(() -> 
    {
       // You can change this to the desired default locale
      Locale desiredLocale = Locale.getDefault();
       
       // Create a new MealSearcher instance with the desired locale
      MealSearcher mealSearcher = new MealSearcher(desiredLocale);
      mealSearcher.setVisible(true);
    });
  }

}
