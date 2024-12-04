package gui;

import javax.swing.*;

import Information.Recipe;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

// Main class that extends JFrame to create the primary window of the application
public class Main extends JFrame
{
  private static final long serialVersionUID = 1293847254;
  private static UnitConverterWindow converterWindow;
  private static CalorieCalculatorWindow calorieWindow;
  private static ResourceBundle strings;
  private static Locale currentLocale;
  private JPanel mainPanel;
  private String logoPath;
  private Color backgroundColor = Color.WHITE;
  private ImageIcon logoIcon;
  private JLabel logoLabel;
  public static String htmlPath;

  // Constructor that takes a Locale parameter for internationalization
  public Main(Locale locale) {
    loadStrings(locale);
    setHtmlPath(locale);
    System.out.println("ResourceBundle loaded: " + (strings != null));
    if (strings != null) {
        loadConfig();
        initializeWindow();
        initializeLogo();
        createMenuBar();
        createMainPanel();
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveConfig));
    } else {
        System.err.println("Cannot initialize window due to missing ResourceBundle");
    }
}

  // Method to load configuration properties from a file
  private void loadConfig() {
    try {
        Properties config = new Properties();
        InputStream input = getClass().getResourceAsStream("/resources/config.properties");
        if (input != null) {
            config.load(input);
            logoPath = config.getProperty("logo_path", "/img/logo.png");
            String colorName = config.getProperty("background_color", "WHITE");
            backgroundColor = getColorFromString(colorName);
            
            // Load unit system preference
            String unitSystem = config.getProperty("unit_system", "METRIC");
            UnitSystemPreferences.setCurrentUnitSystem(UnitSystemPreferences.UnitSystem.valueOf(unitSystem));
        } else {
            System.err.println("Could not find config.properties");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
  private void saveConfig() {
    try {
        Properties config = new Properties();
        config.setProperty("logo_path", logoPath);
        config.setProperty("background_color", getColorName(backgroundColor));
        config.setProperty("unit_system", UnitSystemPreferences.getCurrentUnitSystem().name());

        File configFile = new File(getClass().getResource("/resources/config.properties").toURI());
        try (OutputStream output = Files.newOutputStream(configFile.toPath())) {
            config.store(output, "Application Configuration");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private String getColorName(Color color) {
    if (color.equals(Color.BLACK)) return "BLACK";
    if (color.equals(Color.BLUE)) return "BLUE";
    if (color.equals(Color.CYAN)) return "CYAN";
    if (color.equals(Color.DARK_GRAY)) return "DARK_GRAY";
    if (color.equals(Color.GRAY)) return "GRAY";
    if (color.equals(Color.GREEN)) return "GREEN";
    if (color.equals(Color.LIGHT_GRAY)) return "LIGHT_GRAY";
    if (color.equals(Color.MAGENTA)) return "MAGENTA";
    if (color.equals(Color.ORANGE)) return "ORANGE";
    if (color.equals(Color.PINK)) return "PINK";
    if (color.equals(Color.RED)) return "RED";
    if (color.equals(Color.WHITE)) return "WHITE";
    if (color.equals(Color.YELLOW)) return "YELLOW";
    return "WHITE";
}
  
  

  // Helper method to convert a color name string to a Color object
  private Color getColorFromString(final String colorName)
  {
    switch (colorName.toUpperCase())
    {
      case "BLACK":
        return Color.BLACK;
      case "BLUE":
        return Color.BLUE;
      case "CYAN":
        return Color.CYAN;
      case "DARK_GRAY":
        return Color.DARK_GRAY;
      case "GRAY":
        return Color.GRAY;
      case "GREEN":
        return Color.GREEN;
      case "LIGHT_GRAY":
        return Color.LIGHT_GRAY;
      case "MAGENTA":
        return Color.MAGENTA;
      case "ORANGE":
        return Color.ORANGE;
      case "PINK":
        return Color.PINK;
      case "RED":
        return Color.RED;
      case "WHITE":
        return Color.WHITE;
      case "YELLOW":
        return Color.YELLOW;
      default:
        return Color.WHITE; // Default to white
    }
  }

  // Method to load internationalized strings based on the given locale
  private void loadStrings(final Locale locale)
  {
    currentLocale = locale;
    try
    {
      // Attempt to load the appropriate resource bundle for the given locale
      strings = ResourceBundle.getBundle("resources.Strings", currentLocale);
      System.out.println("Loaded language: " + currentLocale.getLanguage());
    }
    catch (MissingResourceException e)
    {
      // Handle the case where the resource bundle for the locale is not found
      System.err
          .println("Could not find resources.Strings for locale " + locale + ": " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Set up the basic properties of our main window
  private void initializeWindow()
  {
    // Set the title of the window using the internationalized string
    setTitle(strings.getString("main_window_title"));

    // Set the size of the window (width: 600 pixels, height: 400 pixels)
    setSize(600, 400);

    // Specify what happens when the close button is clicked (exit the application)
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Center the window on the screen
    setLocationRelativeTo(null);
  }

  // Load and display the application logo
  private void initializeLogo()
  {
    // Reload the configuration to ensure we have the latest logo path
    loadConfig();

    // Create an ImageIcon object from the logo file
    logoIcon = createImageIcon(logoPath);

    if (logoIcon != null)
    {
      // If the logo was successfully loaded, create a label with the logo
      logoLabel = new JLabel(logoIcon);
      add(logoLabel, BorderLayout.NORTH);
    }
    else
    {
      // If the logo couldn't be loaded, display an error message instead
      logoLabel = new JLabel(strings.getString("error_logo_not_found"));
      add(logoLabel, BorderLayout.NORTH);
    }
  }

  // Helper method to create an ImageIcon from a file path
  private ImageIcon createImageIcon(final String path)
  {
    // Get the URL of the image file from the class resources
    java.net.URL imgURL = getClass().getResource(path);

    if (imgURL != null)
    {
      // If the file was found, create and return an ImageIcon
      return new ImageIcon(imgURL);
    }
    else
    {
      // If the file wasn't found, print an error message and return null
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  // Create the menu bar for the application
  private void createMenuBar()
  {
    // Create a new JMenuBar object
    JMenuBar menuBar = new JMenuBar();

    // Set this JMenuBar as the menu bar for our window
    setJMenuBar(menuBar);

    // Call methods to create each menu and add them to the menu bar
    createFileMenu(menuBar);
    createEditMenu(menuBar);
    createToolsMenu(menuBar);
    createSearchMenu(menuBar);
    createHelpMenu(menuBar);
  }

  // Create the "File" menu
  private void createFileMenu(JMenuBar menuBar) {
    JMenu fileMenu = new JMenu(strings.getString("menu_file"));
    menuBar.add(fileMenu);

    JMenuItem exitItem = new JMenuItem(strings.getString("menu_item_exit"));
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);

    // Add preferences submenu for unit selection
    JMenu preferencesMenu = new JMenu(strings.getString("menu_preferences"));
    fileMenu.add(preferencesMenu);

    JRadioButtonMenuItem metricItem = new JRadioButtonMenuItem(strings.getString("menu_metric_units"));
    JRadioButtonMenuItem imperialItem = new JRadioButtonMenuItem(strings.getString("menu_imperial_units"));

    ButtonGroup unitGroup = new ButtonGroup();
    unitGroup.add(metricItem);
    unitGroup.add(imperialItem);

    preferencesMenu.add(metricItem);
    preferencesMenu.add(imperialItem);

    metricItem.setSelected(UnitSystemPreferences.isMetric());
    imperialItem.setSelected(UnitSystemPreferences.isImperial());

    metricItem.addActionListener(e -> setUnitSystem(UnitSystemPreferences.UnitSystem.METRIC));
    imperialItem.addActionListener(e -> setUnitSystem(UnitSystemPreferences.UnitSystem.IMPERIAL));
}
  
  private void setUnitSystem(UnitSystemPreferences.UnitSystem unitSystem) {
    UnitSystemPreferences.setCurrentUnitSystem(unitSystem);
    if (converterWindow != null && converterWindow.isDisplayable()) {
        converterWindow.updateUnits(unitSystem);
    }
    if (calorieWindow != null && calorieWindow.isDisplayable()) {
        calorieWindow.updateUnits();
    }
    SwingUtilities.updateComponentTreeUI(this);
    saveConfig();
}

  // Create the "Edit" menu
  private void createEditMenu(final JMenuBar menuBar)
  {
    // Create a new JMenu for the Edit menu, with an internationalized label
    JMenu editMenu = new JMenu(strings.getString("menu_edit"));

    // Add the Edit menu to the menu bar
    menuBar.add(editMenu);

    // Create a menu item for the recipe editor
    JMenuItem recipeEditorItem = new JMenuItem(strings.getString("menu_item_recipe"));
    editMenu.add(recipeEditorItem);

    // Add an action listener to open the recipe editor when this item is clicked
    recipeEditorItem.addActionListener(e -> 
    {
      RecipeEditor recipeViewer = new RecipeEditor(currentLocale);
      recipeViewer.setVisible(true);
    });

    // Create a menu item for the meal editor
    JMenuItem mealEditorItem = new JMenuItem(strings.getString("menu_item_meal"));
    editMenu.add(mealEditorItem);

    // Add an action listener to open the meal editor when this item is clicked
    mealEditorItem.addActionListener(e -> 
    {
      MealEditor mealViewer = new MealEditor(currentLocale);
      mealViewer.setVisible(true);
    });
  }


  // Add the "Tools" menu to our menu bar
  private void createToolsMenu(final JMenuBar menuBar)
  {
    // Create a new menu for Tools using the internationalized string
    JMenu toolsMenu = new JMenu(strings.getString("menu_tools"));
    menuBar.add(toolsMenu);

    // Create menu items for Calories Calculator and Units Converter
    JMenuItem caloriesCalculatorItem = new JMenuItem(
        strings.getString("menu_item_calories_calculator"));
    JMenuItem unitsConverterItem = new JMenuItem(strings.getString("menu_item_units_converter"));

    // Add these items to the Tools menu
    toolsMenu.add(caloriesCalculatorItem);
    toolsMenu.add(unitsConverterItem);

    // Add action listener for the Units Converter item
    unitsConverterItem.addActionListener(e -> 
    {
      // Check if the converter window doesn't exist or is not visible
      if (converterWindow == null || !converterWindow.isDisplayable())
      {
        // Create a new converter window and make it visible
        converterWindow = new UnitConverterWindow(currentLocale, UnitSystemPreferences.getCurrentUnitSystem());
        converterWindow.setVisible(true);
      }
      else
      {
        // If the window already exists, bring it to the front and focus on it
        converterWindow.toFront();
        converterWindow.requestFocus();
      }
    });

    // Add action listener for the Calories Calculator item
    caloriesCalculatorItem.addActionListener(e -> 
    {
      // Check if the calorie window doesn't exist or is not visible
      if (calorieWindow == null || !calorieWindow.isDisplayable())
      {
        // Create a new calorie calculator window and make it visible
        calorieWindow = new CalorieCalculatorWindow(currentLocale, UnitSystemPreferences.getCurrentUnitSystem());
        calorieWindow.setVisible(true);
      }
      else
      {
        // If the window already exists, bring it to the front and focus on it
        calorieWindow.toFront();
        calorieWindow.requestFocus();
      }
    });
  }

  // Create the Search menu
  private void createSearchMenu(final JMenuBar menuBar)
  {
    // Create a new menu for Search using the internationalized string
    JMenu searchMenu = new JMenu(strings.getString("menu_search"));
    menuBar.add(searchMenu);

    // Create a menu item for Recipe Searcher
    JMenuItem recipeSearcher = new JMenuItem(strings.getString("search_recipe_searcher"));
    searchMenu.add(recipeSearcher);
    // Add action listener for Recipe Searcher
    recipeSearcher.addActionListener(e -> 
    {
      // Create and display a new Recipe Searcher window
      RecipeSearcher rSearcher = new RecipeSearcher(currentLocale);
      rSearcher.setVisible(true);
    });

    // Create a menu item for Meal Searcher
    JMenuItem mealSearcher = new JMenuItem(strings.getString("search_meal_searcher"));
    searchMenu.add(mealSearcher);
    // Add action listener for Meal Searcher
    mealSearcher.addActionListener(e -> 
    {
      // Create and display a new Meal Searcher window
      MealSearcher mSearcher = new MealSearcher(currentLocale);
      mSearcher.setVisible(true);
    });
  }

  // Create the main panel of the application
  private void createMainPanel()
  {
    mainPanel = new JPanel();
    mainPanel.setBackground(backgroundColor); // Set the background color
    mainPanel.setLayout(new BorderLayout());

    // Add the logo to the center of the main panel if it exists
    if (logoLabel != null)
    {
      mainPanel.add(logoLabel, BorderLayout.CENTER);
    }

    // Add the main panel to the center of the application window
    add(mainPanel, BorderLayout.CENTER);
  }

  // Create the Help menu
  private void createHelpMenu(final JMenuBar menuBar)
  {
    // Create a new menu item for Help using internationalized string
    JMenu helpMenu = new JMenu(strings.getString("menu_help"));
    menuBar.add(helpMenu);

    // Create and add an About menu item
    JMenuItem about = new JMenuItem(strings.getString("menu_item_about"));
//    helpMenu.add(about);

    // Create and add a User Guide menu item
    JMenuItem userGuide = new JMenuItem(strings.getString("menu_item_user_guide"));
    helpMenu.add(userGuide);

    // Add an action listener to open the HTML guide when User Guide is clicked
    userGuide.addActionListener(e -> openHtmlGuide());
  }

  // Set the appropriate HTML path based on the current locale
  private void setHtmlPath(final Locale locale)
  {
    String language = locale.getLanguage();
    switch (language)
    {
      case "it":
        htmlPath = "/gui/indexIt.html"; // Italian guide
        break;
      case "es":
        htmlPath = "/gui/indexSp.html"; // Spanish guide
        break;
      default:
        htmlPath = "/gui/indexEn.html"; // Default to English guide
        break;
    }
  }

  // Open the HTML user guide in the default browser
  private void openHtmlGuide() 
  {
    try (InputStream inputStream = Main.class.getResourceAsStream(htmlPath)) 
    {
      if (inputStream == null) 
      {
        System.err.println("Resource not found: " + htmlPath);
        return;
      }

        // Create a temporary directory for the HTML and images
      File tempDir = Files.createTempDirectory("tempHtml").toFile();

      // Copy the HTML file to the temporary directory
      File tempHtmlFile = new File(tempDir, "index.html");
      Files.copy(inputStream, tempHtmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

      // Copy all images from the resources to the temporary directory
      // Get the list of images in the 'gui' folder inside the JAR
      copyImagesFromGuiFolder(tempDir);

        // Update HTML content if needed to ensure image paths are correct
      String htmlContent = new String(Files.readAllBytes(tempHtmlFile.toPath()), 
          StandardCharsets.UTF_8);
      htmlContent = htmlContent.replaceAll("src=\"../gui/", "src=\"gui/");  // Correct paths in HTML
      Files.write(tempHtmlFile.toPath(), htmlContent.getBytes(StandardCharsets.UTF_8));

      // Open the HTML file in the default browser
      if (Desktop.isDesktopSupported()) 
      {
        Desktop.getDesktop().browse(tempHtmlFile.toURI());
      }

      tempDir.deleteOnExit();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }
	}

	private void copyImagesFromGuiFolder(final File destinationDir) throws IOException 
	{
	    // Assuming images are in the "gui" folder inside the JAR
    String[] imageFiles = {
        "gui/mainEn.png",
        "gui/recipeButtonsEn.png",
        "gui/recipeUtensilEn.png",
        "gui/recipeStepEn.png",
        "gui/recipeIngredientEn.png",	        
        "gui/buttonsEn.png",
        "gui/mealSelectEn.png",
        "gui/mealNameEn.png",
        "gui/unitResultEn.png",
        "gui/unitSelectEn.png",
        "gui/buttonsLessEn.png",
        "gui/buttonsMoreEn.png",
        "gui/calorieResultEn.png",
        "gui/calorieSelectEn.png",
        "gui/searchRTopEn.png",
        "gui/searchRBottomEn.png",
        "gui/processStepEn.png",
        "gui/processUtensil.png",
        "gui/processButtonEn.png"
        
    };

    for (String imagePath : imageFiles) 
    {
      copyResource(imagePath, destinationDir);
    }
	}

	private void copyResource(final String resourcePath, 
	    final File destinationDir) throws IOException 
	{
    // Copy resource from JAR to the temporary directory
    try (InputStream resourceStream = Main.class.getResourceAsStream("/" + resourcePath)) 
    {
      if (resourceStream == null) 
      {
        System.err.println("Resource not found: " + resourcePath);
        return;
      }

      // Ensure the parent directories for the destination file exist
      File destFile = new File(destinationDir, resourcePath);
      destFile.getParentFile().mkdirs();
      Files.copy(resourceStream, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
	}

  // Main method to start the application
  public static void main(final String[] args)
  {
    // Use SwingUtilities.invokeLater to ensure thread safety in Swing applications
    SwingUtilities.invokeLater(() -> 
    {
      // Get the default locale of the system (or set to any other desired locale)
      Locale desiredLocale = Locale.getDefault();

      // Create the main window with the chosen locale
      Main window = new Main(desiredLocale);

      // Only make the window visible if the string resources were loaded successfully
      if (strings != null)
      {
        window.setVisible(true);
      }
      else
      {
        System.err.println("Cannot display window due to missing ResourceBundle");
      }
    });
  }
}
