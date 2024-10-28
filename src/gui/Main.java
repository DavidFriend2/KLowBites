package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @Author Jayden S
 * Creates the Basic Main Window for KILowBites
 * 
 * TODO Add KIlowBites png to the main screen
 * TODO Setup shortcuts
 * TODO Add functionality to each menu header
 * TODO DO NOT ADD THINGS THAT ARE NOT GOING TO BE
 * DONE IN THIS SPRINT, READ DOC FOR MORE DETAILS
 */
public class Main extends JFrame
{
  private ImageIcon logoIcon;
  private JLabel logoLabel;

  public Main()
  {
    // Set up the frame
    setTitle("KiLowBites Main Window");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    // Load the company image
    buttonCreation buttonCreation = new buttonCreation();
    logoIcon = buttonCreation.createImageIcon("/img/logo.png");
    if (logoIcon != null) {
      logoLabel = new JLabel(logoIcon);
    } else {
      logoLabel = new JLabel("Logo not found");
    }

    // Create a menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    // Create File menu -------------------------------------------------------
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    
    // Add menu items to File menu
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);

    // Create Edit menu ------------------------------------------------------------
    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);
    
    //add drop downs for edit
    JMenuItem recipeEditor = new JMenuItem("Recipe");
    editMenu.add(recipeEditor);
    JMenuItem mealEditor = new JMenuItem("Meal");
    editMenu.add(mealEditor);

    // Action listener to Meal menu item
    mealEditor.addActionListener(e -> {
        MealEditor conv = new MealEditor();
        conv.setVisible(true);
    });
    
    // Create Search menu --------------------------------------------------
    JMenu searchMenu = new JMenu("Search");
    menuBar.add(searchMenu);
    
    // add drop downs to search
    JMenuItem searchRecipes = new JMenuItem("Recipes");
    searchMenu.add(searchRecipes);
    JMenuItem searchMeals = new JMenuItem("Meals");
    searchMenu.add(searchMeals);
    
    //ADD LISTENER 
    
    // Create View menu -------------------------------------------------
    JMenu viewMenu = new JMenu("View");
   	menuBar.add(viewMenu);
   	
   	// add drop downs to view
   	JMenuItem viewShoppingList = new JMenuItem("Shopping List");
    viewMenu.add(viewShoppingList);
    JMenuItem viewProcess = new JMenuItem("Process");
    viewMenu.add(viewProcess);
    
    // ADD LISTENER

    // Create Tools menu -----------------------------------------------
    JMenu toolsMenu = new JMenu("Tools");
    menuBar.add(toolsMenu);
    
    // Calories Calculator and Units Converter to Tools menu
    JMenuItem caloriesCalculatorItem = new JMenuItem("Calories Calculator");
    toolsMenu.add(caloriesCalculatorItem);
    JMenuItem unitsConverterItem = new JMenuItem("Units Converter");
    toolsMenu.add(unitsConverterItem);
   
    // Action listener to Units Converter menu item
    unitsConverterItem.addActionListener(e -> {
        UnitConverterWindow converterWindow = new UnitConverterWindow();
        converterWindow.setVisible(true);
    });

    // ADD CALORIES LISTENER

    // Create Configure menu ---------------------------------------------------
    JMenu configureMenu = new JMenu("Configure");
    menuBar.add(configureMenu);

    // drop downs
    JMenuItem preferences = new JMenuItem("Preferences");
    configureMenu.add(preferences);
    JMenuItem shortcuts = new JMenuItem("Shortcuts");
    configureMenu.add(shortcuts);
    
    // ADD LISTENER
    
    // Create Help menu ---------------------------------------------
    JMenu helpMenu = new JMenu("Help");
    menuBar.add(helpMenu);
    
    //drop downs
    JMenuItem about = new JMenuItem("About");
    helpMenu.add(about);
    JMenuItem userGuide = new JMenuItem("User Guide");
    helpMenu.add(userGuide);


    // Create main content panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    
    // Add the logo label to the center of the main panel
    mainPanel.add(logoLabel, BorderLayout.CENTER);
    add(mainPanel);
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      Main window = new Main();
      window.setVisible(true);
    });
  }
}
