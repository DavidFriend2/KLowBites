package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import Information.*;

public class RecipeSearcher extends JFrame
{

  private static final long serialVersionUID = 1L;

  private List<Ingredient> containedIngredients = new ArrayList<>();
  private JList<Ingredient> ingredientJList;
  private DefaultListModel<Ingredient> ingredientDml;
  private JTextField ingredientField;

  private List<Recipe> selectedRecipes = new ArrayList<>();
  private JList<Recipe> recipeJList;
  private DefaultListModel<Recipe> recipeDml;

  private JTextArea status;

  public RecipeSearcher()
  {
    setTitle("KiLowBites Recipe Seacher");
    setSize(600, 700);

    // Main Panel
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Directory chooser Panel
    JPanel chooserPanel = new JPanel();
    JLabel chooserLabel = new JLabel("Choose Directory to Search Through");
    JButton chooseDirectoryButton = new JButton("Choose");
    chooseDirectoryButton.addActionListener(new OpenRecipesListener());
    chooserPanel.add(chooserLabel);
    chooserPanel.add(chooseDirectoryButton);

    // Status text box
    status = new JTextArea(10, 30);
    status.setEditable(false);

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(chooserPanel, BorderLayout.WEST);
    topPanel.add(new JScrollPane(status), BorderLayout.CENTER);

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
    // search button
    JButton searchButton = new JButton("Search Recipes");
    searchButton.addActionListener(new SearchRecipesListener());
    searchButton.setAlignmentX(CENTER_ALIGNMENT);
    ingredientPanel.add(searchButton);

    // Recipe Display List
    recipeDml = new DefaultListModel<>();
    recipeJList = new JList<>(recipeDml);
    recipeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(ingredientPanel, BorderLayout.CENTER);
    mainPanel.add(new JScrollPane(recipeJList), BorderLayout.SOUTH);
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
    status.setText("");

    if (files.length > 0)
    {
      selectedRecipes.clear();
      recipeDml.clear();

      for (File file : files)
      {
        try
        {
          Recipe recipe = Recipe.loadRecipeFromFile(file.getAbsolutePath());
          selectedRecipes.add(recipe);
          recipeDml.addElement(recipe);
          status.append("Loaded recipe: " + recipe.getName() + "\n");

        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      status.append(
          "Loaded " + selectedRecipes.size() + " recipes from directory: " + directory.getName());

    }
    else
    {
      status.setText("No recipe files found");
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
        status.append("\nAdded ingredient: " + ingredientName);
        ingredientField.setText("");
      }
    }
  }

  private class SearchRecipesListener implements ActionListener
  {

    @Override
    public void actionPerformed(ActionEvent e)
    {
      List<Recipe> searchedRecipes = new ArrayList<>();

      //search every recipe
      for (Recipe recipe : selectedRecipes)
      {
        List<RecipeIngredient> recipeIngredients = recipe.getIngredients();
        boolean containsIngredients = true;
        
        //Check if each given ingredient is in a recipe
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
          
          //If one ingredient isnt found recipe wont be given, break out 
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

      status.append("\nFound " + searchedRecipes.size() + " recipes");
      selectedRecipes = searchedRecipes;
      recipeDml.clear();
      for (Recipe recipe : searchedRecipes)
      {
        recipeDml.addElement(recipe);
      }

    }

  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      new RecipeSearcher().setVisible(true);
    });
  }

}
