package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

  private List<Ingredient> containedIngredients = new ArrayList<>();
  private JList<Ingredient> ingredientJList;
  private DefaultListModel<Ingredient> ingredientDml;
  private JTextField ingredientField;

  private List<Recipe> selectedRecipes = new ArrayList<>();
  private JComboBox<Recipe> recipeDropdown;

  public RecipeSearcher()
  {
    setTitle("KiLowBites Recipe Seacher");
    setSize(350, 500);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Main Panel
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Directory chooser Panel
    JPanel chooserPanel = new JPanel();
    JLabel chooserLabel = new JLabel("Choose Directory to Search Through");
    JButton chooseDirectoryButton = new JButton("Choose");
    chooseDirectoryButton.addActionListener(new OpenRecipesListener());
    chooserPanel.add(chooserLabel);
    chooserPanel.add(chooseDirectoryButton);

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(chooserPanel, BorderLayout.WEST);

    // Ingredients panel
    JPanel ingredientPanel = new JPanel();
    ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
    // label
    JLabel ingredientLabel = new JLabel("Add ingredient(s) to look for in recipes");
    ingredientLabel.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(ingredientLabel);
    // text field
    ingredientField = new JTextField(15);
    ingredientField.setMaximumSize(ingredientField.getPreferredSize());
    ingredientPanel.add(ingredientField);
    // add ingredient button
    JButton addIngredientButton = new JButton("Add");
    addIngredientButton.addActionListener(new AddIngredientListener());
    addIngredientButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(addIngredientButton);
    // ingredient jlist
    ingredientDml = new DefaultListModel<>();
    ingredientJList = new JList<>(ingredientDml);
    ingredientJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ingredientPanel.add(new JScrollPane(ingredientJList));
    // delete button
    JButton deleteIngredientButton = new JButton("Delete Ingredient");
    deleteIngredientButton.addActionListener(new deleteIngredientListener());
    deleteIngredientButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(deleteIngredientButton);
    // search button
    JButton searchButton = new JButton("Search Recipes");
    searchButton.addActionListener(new SearchRecipesListener());
    searchButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(searchButton);

    // Recipe Display List
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
    public void actionPerformed(ActionEvent e)
    {
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

  private void loadAllRecipes(File directory)
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
    public void actionPerformed(ActionEvent e)
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

  private class deleteIngredientListener implements ActionListener
  {

    @Override
    public void actionPerformed(ActionEvent e)
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
    public void actionPerformed(ActionEvent e)
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

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      new RecipeSearcher().setVisible(true);
    });
  }

}
