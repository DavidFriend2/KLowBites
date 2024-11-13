package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Creates the Basic Main Window for KILowBites
 * 
 * @author Jayden S
 * 
 * TODO Setup shortcuts TODO Add functionality to each menu header TODO DO NOT ADD THINGS
 * HAT ARE NOT GOING TO BE DONE IN THIS SPRINT, READ DOC FOR MORE DETAILS.
 */
public class Main extends JFrame
{
  private static final long serialVersionUID = 1293847254;

  // Path to our logo image
  private static final String LOGO_PATH = "/img/logo.png";
  private static final String HTML_PATH = "src/gui/index.html"; // this works but i dont know why

  // Some UI components we'll need later
  private ImageIcon logoIcon;
  private JLabel logoLabel;
  private static UnitConverterWindow converterWindow;
  private static CalorieCalculatorWindow calorieWindow;
  

  // Constructor no arg
  public Main()
  {
    initializeWindow();
    initializeLogo();
    createMenuBar();
    createMainPanel();
  }

  // Set up the basic properties of our main window
  private void initializeWindow()
  {
    setTitle("KiLowBites Main Window");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the window on the screen
  }

  // Load up our logo
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

  // Create the menu bar at the top of our window
  private void createMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    createFileMenu(menuBar);
    createEditMenu(menuBar);
    // createSearchMenu(menuBar);
    createViewMenu(menuBar);
    createToolsMenu(menuBar);
    // createConfigureMenu(menuBar);
     createHelpMenu(menuBar);
  }

  // Add the "File" menu to our menu bar
  private void createFileMenu(JMenuBar menuBar)
  {
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    // For now, we just have an exit option
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);
  }

  // Add the "Edit" menu to our menu bar
  private void createEditMenu(JMenuBar menuBar)
  {
    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    // Add a recipe editor option
    JMenuItem recipeEditor = new JMenuItem("Recipe");
    editMenu.add(recipeEditor);
    recipeEditor.addActionListener(e -> {
      RecipeEditor recipeViewer = new RecipeEditor();
      recipeViewer.setVisible(true);
    });

    // Meal editor commented out for now
    // JMenuItem mealEditor = new JMenuItem("Meal");
    // editMenu.add(mealEditor);
    // mealEditor.addActionListener(e -> {
    // MealEditor conv = new MealEditor();
    // conv.setVisible(true);
    // });
  }

  // Add the "View" menu to our menu bar
  private void createViewMenu(JMenuBar menuBar)
  {
    JMenu viewMenu = new JMenu("View");
    menuBar.add(viewMenu);

    // Shopping list view commented out for now
    // JMenuItem viewShoppingList = new JMenuItem("Shopping List");
    // viewMenu.add(viewShoppingList);

    // Add a process viewer option
    JMenuItem viewProcess = new JMenuItem("Process");
    viewMenu.add(viewProcess);
    viewProcess.addActionListener(e -> {
      RecipeProcessViewer processViewer = new RecipeProcessViewer();
      processViewer.setVisible(true);
    });
  }

  // Add the "Tools" menu to our menu bar
  private void createToolsMenu(JMenuBar menuBar)
  {
    JMenu toolsMenu = new JMenu("Tools");
    menuBar.add(toolsMenu);

    // Add calorie calculator and unit converter options
    JMenuItem caloriesCalculatorItem = new JMenuItem("Calories Calculator");
    toolsMenu.add(caloriesCalculatorItem);

    JMenuItem unitsConverterItem = new JMenuItem("Units Converter");
    toolsMenu.add(unitsConverterItem);

    // Open the unit converter window when clicked
    unitsConverterItem.addActionListener(e -> {
      if (converterWindow == null || !converterWindow.isDisplayable())
      {
        converterWindow = new UnitConverterWindow();
        converterWindow.setVisible(true);
      }
      else
      {
        converterWindow.toFront();
        converterWindow.requestFocus();
      }
    });

    // Open the calorie calculator window when clicked
    caloriesCalculatorItem.addActionListener(e -> {
      if (calorieWindow == null || !calorieWindow.isDisplayable())
      {
//        calorieWindow = new CalorieCalculatorWindow();
//        calorieWindow.setVisible(true);
      }
      else
      {
        calorieWindow.toFront();
        calorieWindow.requestFocus();
      }
    });
  }

  private void createHelpMenu(JMenuBar menuBar) {
	JMenu helpMenu = new JMenu("Help");
    menuBar.add(helpMenu);

    JMenuItem about = new JMenuItem("About");
    helpMenu.add(about);

    JMenuItem userGuide = new JMenuItem("User Guide");
    helpMenu.add(userGuide);
    
    userGuide.addActionListener(e -> {
    	try {
    		File htmlFile = new File(HTML_PATH);
    		Desktop d = Desktop.getDesktop();
    		d.browse(htmlFile.toURI());
    	} catch (IOException l) {
    		// TODO Auto-generated catch block
    		l.printStackTrace();
    	}
      });
    
  }
  
  // Create the main panel and add our logo to it
  private void createMainPanel()
  {
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setLayout(new BorderLayout());

    if (logoLabel != null)
    {
      mainPanel.add(logoLabel, BorderLayout.CENTER);
    }

    add(mainPanel);
  }

  // Helper method to load an image icon
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
