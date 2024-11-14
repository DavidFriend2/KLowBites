package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main extends JFrame {
    private static final long serialVersionUID = 1293847254;

    // Load the resource bundle for internationalization
    private static ResourceBundle strings;
    private static Locale currentLocale;
    private JPanel mainPanel;
    
    // Path to our logo image
    private String logoPath;
    private String htmlPath = "src/gui/index.html";
    private Color backgroundColor = Color.WHITE; // Default to white

    // Some UI components we'll need later
    private ImageIcon logoIcon;
    private JLabel logoLabel;

    // Other windows
    private static UnitConverterWindow converterWindow;
    private static CalorieCalculatorWindow calorieWindow;
//    private static RecipeEditor recipeEditor;
//    private static ProcessViewer processViewer;

    // Constructor with locale parameter
    public Main(Locale locale) {
      loadStrings(locale);
      System.out.println("ResourceBundle loaded: " + (strings != null));
      if (strings != null) {
          initializeWindow();
          loadConfig(); // Load configuration including background color
          initializeLogo();
          createMenuBar();
          createMainPanel(); // Now this will use the loaded background color
      } else {
          System.err.println("Cannot initialize window due to missing ResourceBundle");
      }
  }

    // Method to load configuration properties
    private void loadConfig() {
      try {
          Properties config = new Properties();
          InputStream input = getClass().getResourceAsStream("/resources/config.properties");
          if (input != null) {
              config.load(input);
              logoPath = config.getProperty("logo_path", "/img/logo.png");
              
              // Load background color
              String colorName = config.getProperty("background_color", "WHITE");
              backgroundColor = getColorFromString(colorName);
          } else {
              System.err.println("Could not find config.properties");
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
    
    
    private Color getColorFromString(String colorName) {
      switch (colorName.toUpperCase()) {
          case "BLACK": return Color.BLACK;
          case "BLUE": return Color.BLUE;
          case "CYAN": return Color.CYAN;
          case "DARK_GRAY": return Color.DARK_GRAY;
          case "GRAY": return Color.GRAY;
          case "GREEN": return Color.GREEN;
          case "LIGHT_GRAY": return Color.LIGHT_GRAY;
          case "MAGENTA": return Color.MAGENTA;
          case "ORANGE": return Color.ORANGE;
          case "PINK": return Color.PINK;
          case "RED": return Color.RED;
          case "WHITE": return Color.WHITE;
          case "YELLOW": return Color.YELLOW;
          default: return Color.WHITE; // Default
      }
  }

    // Load up our strings for internationalization
    private static void loadStrings(Locale locale) {
        currentLocale = locale;
        try {
            strings = ResourceBundle.getBundle("resources.Strings", currentLocale);
            System.out.println("Loaded language: " + currentLocale.getLanguage());
        } catch (MissingResourceException e) {
            System.err.println("Could not find resources.Strings for locale " + locale + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Set up the basic properties of our main window
    private void initializeWindow() {
        setTitle(strings.getString("main_window_title"));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    // Load up our logo
    private void initializeLogo() {
        loadConfig(); // Load the configuration to get the logo path
        logoIcon = createImageIcon(logoPath); // path to create the ImageIcon

        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon); 
            add(logoLabel, BorderLayout.NORTH); 
        } else {
            logoLabel = new JLabel(strings.getString("error_logo_not_found"));
            add(logoLabel, BorderLayout.NORTH);
        }
    }
    
    private ImageIcon createImageIcon(String path) {
      java.net.URL imgURL = getClass().getResource(path);
      if (imgURL != null) {
          return new ImageIcon(imgURL);
      } else {
          System.err.println("Couldn't find file: " + path);
          return null;
      }
  }

    // Create the menu bar at the top of our window
    private void createMenuBar() {
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      createFileMenu(menuBar);
      createEditMenu(menuBar);
      createViewMenu(menuBar);
      createToolsMenu(menuBar);
      createSearchMenu(menuBar);
      createHelpMenu(menuBar);
  }

    // Add the "File" menu to our menu bar
    private void createFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu(strings.getString("menu_file"));
        menuBar.add(fileMenu);

        JMenuItem exitItem = new JMenuItem(strings.getString("menu_item_exit"));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
    }

    // Add the "Edit" menu to our menu bar
    private void createEditMenu(JMenuBar menuBar) {
        JMenu editMenu = new JMenu(strings.getString("menu_edit"));
        menuBar.add(editMenu);

        JMenuItem recipeEditorItem = new JMenuItem(strings.getString("menu_item_recipe"));
        editMenu.add(recipeEditorItem);
        
        recipeEditorItem.addActionListener(e -> {
            RecipeEditor recipeViewer = new RecipeEditor(currentLocale);
            recipeViewer.setVisible(true);
        });
        
        JMenuItem mealEditorItem = new JMenuItem(strings.getString("menu_item_meal"));
        editMenu.add(mealEditorItem);
        
        mealEditorItem.addActionListener(e -> {
            MealEditor mealViewer = new MealEditor();
            mealViewer.setVisible(true);
        });
    }

    // Add the "View" menu to our menu bar
    private void createViewMenu(JMenuBar menuBar) {
        JMenu viewMenu = new JMenu(strings.getString("menu_view"));
        menuBar.add(viewMenu);

        JMenuItem viewProcessItem = new JMenuItem(strings.getString("menu_item_process"));
        viewMenu.add(viewProcessItem);

        viewProcessItem.addActionListener(e -> {
            RecipeProcessViewer processViewer = new RecipeProcessViewer();
            processViewer.setVisible(true);
        });
    }

    // Add the "Tools" menu to our menu bar
    private void createToolsMenu(JMenuBar menuBar) {
        JMenu toolsMenu = new JMenu(strings.getString("menu_tools"));
        menuBar.add(toolsMenu);

        JMenuItem caloriesCalculatorItem = new JMenuItem(strings.getString("menu_item_calories_calculator"));
        toolsMenu.add(caloriesCalculatorItem);

        JMenuItem unitsConverterItem = new JMenuItem(strings.getString("menu_item_units_converter"));
        toolsMenu.add(unitsConverterItem);

        unitsConverterItem.addActionListener(e -> {
            if (converterWindow == null || !converterWindow.isDisplayable()) {
                converterWindow = new UnitConverterWindow(currentLocale);
                converterWindow.setVisible(true);
            } else {
                converterWindow.toFront();
                converterWindow.requestFocus();
            }
        });
        

        caloriesCalculatorItem.addActionListener(e -> {
            if (calorieWindow == null || !calorieWindow.isDisplayable()) {
                calorieWindow = new CalorieCalculatorWindow(currentLocale);
                calorieWindow.setVisible(true);
            } else {
                calorieWindow.toFront();
                calorieWindow.requestFocus();
            }
        });
    }
    
    private void createSearchMenu(JMenuBar menuBar) {
      JMenu searchMenu = new JMenu(strings.getString("menu_search"));
      menuBar.add(searchMenu);

      JMenuItem recipeSearcher = new JMenuItem(strings.getString("search_recipe_searcher"));
      searchMenu.add(recipeSearcher);
      recipeSearcher.addActionListener(e -> {
        RecipeSearcher rSearcher = new RecipeSearcher();
        rSearcher.setVisible(true);
      });

      JMenuItem mealSearcher = new JMenuItem(strings.getString("search_meal_searcher"));
      searchMenu.add(mealSearcher);
      mealSearcher.addActionListener(e -> {
        MealSearcher mSearcher = new MealSearcher();
        mSearcher.setVisible(true);
      });
  }

 // Create the main panel and add our logo to it
    private void createMainPanel() {
      mainPanel = new JPanel();
      mainPanel.setBackground(backgroundColor); // Use the loaded background color
      mainPanel.setLayout(new BorderLayout());

      if (logoLabel != null) {
          mainPanel.add(logoLabel, BorderLayout.CENTER);
      }

      add(mainPanel, BorderLayout.CENTER);
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
        		File htmlFile = new File(htmlPath);
        		Desktop d = Desktop.getDesktop();
        		d.browse(htmlFile.toURI());
        	} catch (IOException l) {
        		// TODO Auto-generated catch block
        		l.printStackTrace();
        	}
          });
        
      }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Locale desiredLocale = Locale.getDefault(); // CHANGE THIS FOR LANGUAGE (: EXAMPLE: Locale.ITALIAN
            Main window = new Main(desiredLocale);
            if (strings != null) {
                window.setVisible(true);
            } else {
                System.err.println("Cannot display window due to missing ResourceBundle");
            }
        });
    }
}