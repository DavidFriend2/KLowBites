package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the Basic Main Window for KILowBites
 * 
 * @author Jayden S
 * 
 * TODO Setup shortcuts 
 * TODO Add functionality to each menu header 
 * TODO DO NOT ADD THINGS THAT ARE NOT GOING TO BE DONE IN THIS SPRINT,
 * READ DOC FOR MORE DETAILS. 
 */
public class Main extends JFrame
{
  private static final long serialVersionUID = 1293847254;

  private static final String LOGO_PATH = "/img/logo.png";

  private ImageIcon logoIcon;
  private JLabel logoLabel;

  public Main()
  {
    initializeWindow();
    initializeLogo();
    createMenuBar();
    createMainPanel();
  }

  private void initializeWindow()
  {
    setTitle("KiLowBites Main Window");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  private void initializeLogo()
  {
    logoIcon = createImageIcon(LOGO_PATH);
    if (logoIcon != null)
    {
      logoLabel = new JLabel(logoIcon);
    }
    else
    {
      logoLabel = new JLabel("Logo not found");
    }
  }

  private void createMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    createFileMenu(menuBar);
    createEditMenu(menuBar);
//    createSearchMenu(menuBar);
    createViewMenu(menuBar);
    createToolsMenu(menuBar);
//    createConfigureMenu(menuBar);
//    createHelpMenu(menuBar);
  }

  private void createFileMenu(JMenuBar menuBar)
  {
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);
  }

  private void createEditMenu(JMenuBar menuBar)
  {
    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    JMenuItem recipeEditor = new JMenuItem("Recipe");
    editMenu.add(recipeEditor);
    recipeEditor.addActionListener(e -> {
      RecipeEditor recipeViewer = new RecipeEditor();
      recipeViewer.setVisible(true);
    });
//    JMenuItem mealEditor = new JMenuItem("Meal");
//    editMenu.add(mealEditor);

//    mealEditor.addActionListener(e -> {
//      MealEditor conv = new MealEditor();
//      conv.setVisible(true);
//    });
    
    
  }

//  private void createSearchMenu(JMenuBar menuBar)
//  {
//    JMenu searchMenu = new JMenu("Search");
//    menuBar.add(searchMenu);
//
//    JMenuItem searchRecipes = new JMenuItem("Recipes");
//    searchMenu.add(searchRecipes);
//    JMenuItem searchMeals = new JMenuItem("Meals");
//    searchMenu.add(searchMeals);
//  }

  private void createViewMenu(JMenuBar menuBar)
  {
    JMenu viewMenu = new JMenu("View");
    menuBar.add(viewMenu);

//    JMenuItem viewShoppingList = new JMenuItem("Shopping List");
//    viewMenu.add(viewShoppingList);
    JMenuItem viewProcess = new JMenuItem("Process");
    viewMenu.add(viewProcess);
    viewProcess.addActionListener(e -> {
      ProcessViewer processViewer = new ProcessViewer();
      processViewer.setVisible(true);
    });
  }

  private void createToolsMenu(JMenuBar menuBar)
  {
    JMenu toolsMenu = new JMenu("Tools");
    menuBar.add(toolsMenu);

    JMenuItem caloriesCalculatorItem = new JMenuItem("Calories Calculator");
    toolsMenu.add(caloriesCalculatorItem);
    
    JMenuItem unitsConverterItem = new JMenuItem("Units Converter");
    toolsMenu.add(unitsConverterItem);

    unitsConverterItem.addActionListener(e -> {
      UnitConverterWindow converterWindow = new UnitConverterWindow();
      converterWindow.setVisible(true);
    });
    
    caloriesCalculatorItem.addActionListener(e -> {
      CalorieCalculatorWindow calorieWindow = new CalorieCalculatorWindow();
      calorieWindow.setVisible(true);
    });
  }

//THIS IS FOR A LATER SPRINT
//  private void createConfigureMenu(JMenuBar menuBar)
//  {
//    JMenu configureMenu = new JMenu("Configure");
//    menuBar.add(configureMenu);
//
//    JMenuItem preferences = new JMenuItem("Preferences");
//    configureMenu.add(preferences);
//    JMenuItem shortcuts = new JMenuItem("Shortcuts");
//    configureMenu.add(shortcuts);
//  }

//  THIS IS FOR A LATER SPRINT
//  private void createHelpMenu(JMenuBar menuBar)
//  {
//    JMenu helpMenu = new JMenu("Help");
//    menuBar.add(helpMenu);
//
//    JMenuItem about = new JMenuItem("About");
//    helpMenu.add(about);
//    JMenuItem userGuide = new JMenuItem("User Guide");
//    helpMenu.add(userGuide);
//  }

  private void createMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setLayout(new BorderLayout());
    
    if (logoLabel != null) {
        mainPanel.add(logoLabel, BorderLayout.CENTER);
    }
    
    add(mainPanel);
}

  private ImageIcon createImageIcon(String path)
  {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null)
    {
      return new ImageIcon(imgURL);
    }
    else
    {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      Main window = new Main();
      window.setVisible(true);
    });
  }
}
