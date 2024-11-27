package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import Information.*;

public class ShoppingListWindow extends JFrame
{

  private static final long serialVersionUID = 1L;
  private JList<RecipeIngredient> shoppingJlist;
  private JTextField people;
  private Meal loadedMeal;
  private Recipe loadedRecipe;

  public ShoppingListWindow()
  {
    // strings = ResourceBundle.getBundle("resources.Strings", locale); // Load the resource bundle
    // based on locale
    setTitle("Shopping List");
    setSize(500, 750);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Main Panel
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Directory chooser Panel
    JPanel chooserPanel = new JPanel();
    // JLabel chooserLabel = new JLabel(strings.getString("choose_directory_label"));
    JLabel chooserLabel = new JLabel("choose meal or recipe");
    // Internationalized label
    // JButton chooseDirectoryButton = new JButton(strings.getString("choose_button"));
    JButton chooseDirectoryButton = new JButton(("choose"));
    // Internationalized button
    chooseDirectoryButton.addActionListener(openMealorRecipe);
    chooserPanel.add(chooserLabel);
    chooserPanel.add(chooseDirectoryButton);
    
    // people panel
    JPanel peoplePanel = new JPanel();
    people = new JTextField(4);
    peoplePanel.add(new JLabel("Number of People: "));
    peoplePanel.add(people);
    
    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(peoplePanel, BorderLayout.NORTH);
    topPanel.add(chooserPanel, BorderLayout.CENTER);

    
//    JButton updateButton = new JButton("Update Shopping List");
//    updateButton.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.print("Button clicked");
//            peopleUpdated();
//        }
//    });
    
    //peoplePanel.add(updateButton);
    
    // Shopping list panel
    // display ingredients **** needs to be done;
    JPanel shoppingListPanel = new JPanel(new BorderLayout());
    shoppingListPanel.add(new JLabel("Shopping List:"), BorderLayout.NORTH);
    shoppingJlist = new JList<>(new DefaultListModel<>());
    shoppingJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shoppingJlist.setFont(new Font("Arial", Font.PLAIN, 18));
    shoppingListPanel.add(new JScrollPane(shoppingJlist), BorderLayout.CENTER);

    // main panel
    //mainPanel.add(peoplePanel, BorderLayout.NORTH);
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(shoppingListPanel, BorderLayout.CENTER);

    add(mainPanel);
  }

  // Opens a meal or recipe
  private ActionListener openMealorRecipe = e -> {

    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Recip or Meal files", "rcp",
        "mel");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
      try
      {
        // check if its a meal or recipe
        String file = chooser.getSelectedFile().getAbsolutePath();
        if (file.endsWith(".rcp"))
        {
          loadedRecipe = Recipe.loadRecipeFromFile(file);
          updateShoppingList(new ShoppingList(loadedRecipe, getPeopleCount()));
          //peopleUpdated();
        }
        else if (file.endsWith(".mel"))
        {
          loadedMeal = Meal.loadMealFromFile(file);
          updateShoppingList(new ShoppingList(loadedMeal, getPeopleCount()));
          //peopleUpdated();
        }

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  };

//  private void peopleUpdated()
//  {
//    int peopleCount = getPeopleCount();
//    
//    if (loadedRecipe != null)
//    {
//      updateShoppingList(new ShoppingList(loadedRecipe, peopleCount));
//    }
//    else if (loadedMeal != null)
//    {
//      updateShoppingList(new ShoppingList(loadedMeal, peopleCount));
//    }
//  }

  private void updateShoppingList(ShoppingList shoppingList)
  {
    DefaultListModel<RecipeIngredient> model = (DefaultListModel<RecipeIngredient>) shoppingJlist
        .getModel();
    model.clear();
    for (RecipeIngredient ingredient : shoppingList.getShoppingList())
    {
      model.addElement(ingredient);
    }
  }

  private int getPeopleCount()
  {
    try
    {
      return Integer.parseInt(people.getText().trim());
    }
    catch (NumberFormatException e)
    {
      return 1; // Default to 1 if the input is invalid
    }
  }

  // Main to display the searcher
  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      // You can change this to the desired default locale
      // Locale desiredLocale = Locale.getDefault();

      // Create a new MealSearcher instance with the desired locale
      // ShoppingListWindow mealSearcher = new ShoppingListWindow(desiredLocale);
      ShoppingListWindow shoppinglist = new ShoppingListWindow();
      shoppinglist.setVisible(true);
    });
  }

}
