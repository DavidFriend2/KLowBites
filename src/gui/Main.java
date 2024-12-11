package gui;

import javax.swing.*;

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

/**
 * Main Window that contains all project functionality.
 */
public class Main extends JFrame
{
  public static String htmlPath;
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

  /**
   * Constructor for the Main class. Initializes the application window with internationalization.
   * support
   * 
   * @param locale
   *          is the language used
   */
  public Main(final Locale locale)
  {
    loadStrings(locale);
    setHtmlPath(locale);
    System.out.println("ResourceBundle loaded: " + (strings != null));
    if (strings != null)
    {
      loadConfig();
      initializeWindow();
      initializeLogo();
      createMenuBar();
      createMainPanel();

      // Add shutdown hook
      Runtime.getRuntime().addShutdownHook(new Thread(this::saveConfig));
    }
    else
    {
      System.err.println("Cannot initialize window due to missing ResourceBundle");
    }
  }

  /**
   * Loads the correct configs like logo, color, and units.
   */
  private void loadConfig()
  {
    try
    {
      Properties config = new Properties();
      InputStream input = getClass().getResourceAsStream("/resources/config.properties");
      if (input != null)
      {
        config.load(input);
        logoPath = config.getProperty("logo_path", "/img/logo.png");
        String colorName = config.getProperty("background_color", "WHITE");
        backgroundColor = getColorFromString(colorName);

        // Load unit system preference
        String unitSystem = config.getProperty("unit_system", "METRIC");
        UnitSystemPreferences
            .setCurrentUnitSystem(UnitSystemPreferences.UnitSystem.valueOf(unitSystem));
      }
      else
      {
        System.err.println("Could not find config.properties");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Saves user configs like logo, color, and units.
   */
  private void saveConfig() 
  {
    try 
    {
      Properties config = new Properties();
      config.setProperty("logo_path", logoPath);
      config.setProperty("background_color", getColorName(backgroundColor));
      config.setProperty("unit_system", UnitSystemPreferences.getCurrentUnitSystem().name());

      // Create a temporary file for the configuration
      File tempFile = File.createTempFile("config", ".properties");
      
      try (OutputStream output = Files.newOutputStream(tempFile.toPath())) 
      {
        config.store(output, "Application Configuration");
        System.out.println("Config saved to: " + tempFile.getAbsolutePath());
      }
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
}

  /**
   * Returns the correct user specified color.
   * 
   * @param color
   *          of the background
   * @return the correct color
   */
  private String getColorName(final Color color)
  {
    if (color.equals(Color.BLACK))
      return "BLACK";
    if (color.equals(Color.BLUE))
      return "BLUE";
    if (color.equals(Color.CYAN))
      return "CYAN";
    if (color.equals(Color.DARK_GRAY))
      return "DARK_GRAY";
    if (color.equals(Color.GRAY))
      return "GRAY";
    if (color.equals(Color.GREEN))
      return "GREEN";
    if (color.equals(Color.LIGHT_GRAY))
      return "LIGHT_GRAY";
    if (color.equals(Color.MAGENTA))
      return "MAGENTA";
    if (color.equals(Color.ORANGE))
      return "ORANGE";
    if (color.equals(Color.PINK))
      return "PINK";
    if (color.equals(Color.RED))
      return "RED";
    if (color.equals(Color.WHITE))
      return "WHITE";
    if (color.equals(Color.YELLOW))
      return "YELLOW";
    return "WHITE";
  }

  /**
   * Converts string to correct color.
   * 
   * @param colorName
   *          of the color
   * @return the correct color
   */
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

  /**
   * Loads the correct strings based on language.
   * 
   * @param locale
   *          is the language
   */
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

  /**
   * Creates the main window with title and size.
   */
  private void initializeWindow()
  {
    setTitle(strings.getString("main_window_title"));
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  /**
   * Creates the logo for the main window.
   */
  private void initializeLogo()
  {
    loadConfig();
    logoIcon = createImageIcon(logoPath);

    if (logoIcon != null)
    {
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

  /**
   * Creates the button icons in the files.
   * 
   * @param path
   * @return image icon
   */
  private ImageIcon createImageIcon(final String path)
  {
    java.net.URL imgURL = getClass().getResource(path);

    if (imgURL != null)
    {
      return new ImageIcon(imgURL);
    }
    else
    {
      // If the file wasn't found, print an error message and return null
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  /**
   * Creates the menu bar for the main window.
   */
  private void createMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    createFileMenu(menuBar);
    createEditMenu(menuBar);
    createSearchMenu(menuBar);
    createViewMenu(menuBar);
    createToolsMenu(menuBar);
    createConfigureMenu(menuBar);
    createHelpMenu(menuBar);
  }

  /**
   * Creates the File menu Bar.
   * 
   * @param menuBar
   *          of the application
   */
  private void createFileMenu(final JMenuBar menuBar)
  {
    JMenu fileMenu = new JMenu(strings.getString("menu_file"));
    menuBar.add(fileMenu);

    JMenuItem exitItem = new JMenuItem(strings.getString("menu_item_exit"));
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);
  }

  /**
   * Sets the unit correctly for the system.
   * 
   * @param unitSystem
   *          the units used
   */
  private void setUnitSystem(final UnitSystemPreferences.UnitSystem unitSystem)
  {
    UnitSystemPreferences.setCurrentUnitSystem(unitSystem);
    if (converterWindow != null && converterWindow.isDisplayable())
    {
      converterWindow.updateUnits(unitSystem);
    }
    if (calorieWindow != null && calorieWindow.isDisplayable())
    {
      calorieWindow.updateUnits();
    }
    SwingUtilities.updateComponentTreeUI(this);
    saveConfig();
  }

  /**
   * Creates the edit menu in the main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createEditMenu(final JMenuBar menuBar)
  {
    JMenu editMenu = new JMenu(strings.getString("menu_edit"));
    menuBar.add(editMenu);
    JMenuItem recipeEditorItem = new JMenuItem(strings.getString("menu_item_recipe"));
    editMenu.add(recipeEditorItem);

    // action listener to open the recipe editor when this item is clicked
    recipeEditorItem.addActionListener(e -> 
    {
      RecipeEditor recipeViewer = new RecipeEditor(currentLocale);
      recipeViewer.setVisible(true);
    });

    JMenuItem mealEditorItem = new JMenuItem(strings.getString("menu_item_meal"));
    editMenu.add(mealEditorItem);

    // action listener to open the meal editor when this item is clicked
    mealEditorItem.addActionListener(e -> 
    {
      MealEditor mealViewer = new MealEditor(currentLocale);
      mealViewer.setVisible(true);
    });
  }

  /**
   * Creates view menu in main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createViewMenu(final JMenuBar menuBar)
  {
    JMenu viewMenu = new JMenu(strings.getString("menu_view"));
    menuBar.add(viewMenu);

    JMenuItem shoppingListViewerItem = new JMenuItem(strings.getString("menu_item_shopping_list"));
    viewMenu.add(shoppingListViewerItem);

    shoppingListViewerItem.addActionListener(e -> 
    {
      ShoppingListWindow shoppingListViewer = new ShoppingListWindow(currentLocale);
      shoppingListViewer.setVisible(true);
    });
  }

  /**
   * Creates the tool menu in the main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createToolsMenu(final JMenuBar menuBar)
  {
    JMenu toolsMenu = new JMenu(strings.getString("menu_tools"));
    menuBar.add(toolsMenu);
    JMenuItem caloriesCalculatorItem = new JMenuItem(
        strings.getString("menu_item_calories_calculator"));
    JMenuItem unitsConverterItem = new JMenuItem(strings.getString("menu_item_units_converter"));
    toolsMenu.add(caloriesCalculatorItem);
    toolsMenu.add(unitsConverterItem);

    unitsConverterItem.addActionListener(e -> 
    {
      // Check if the converter window doesn't exist or is not visible
      if (converterWindow == null || !converterWindow.isDisplayable())
      {
        // Create a new converter window and make it visible
        converterWindow = new UnitConverterWindow(currentLocale,
            UnitSystemPreferences.getCurrentUnitSystem());
        converterWindow.setVisible(true);
      }
      else
      {
        // If the window already exists, bring it to the front and focus on it
        converterWindow.toFront();
        converterWindow.requestFocus();
      }
    });

    // action listener for the Calories Calculator item
    caloriesCalculatorItem.addActionListener(e -> 
    {
      // Check if the calorie window doesn't exist or is not visible
      if (calorieWindow == null || !calorieWindow.isDisplayable())
      {
        calorieWindow = new CalorieCalculatorWindow(currentLocale,
            UnitSystemPreferences.getCurrentUnitSystem());
        calorieWindow.setVisible(true);
      }
      else
      {
        calorieWindow.toFront();
        calorieWindow.requestFocus();
      }
    });
  }

  /**
   * Creates menu bar for main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createSearchMenu(final JMenuBar menuBar)
  {
    JMenu searchMenu = new JMenu(strings.getString("menu_search"));
    menuBar.add(searchMenu);
    JMenuItem recipeSearcher = new JMenuItem(strings.getString("search_recipe_searcher"));
    searchMenu.add(recipeSearcher);
    // Add action listener for Recipe Searcher
    recipeSearcher.addActionListener(e -> 
    {
      RecipeSearcher rSearcher = new RecipeSearcher(currentLocale);
      rSearcher.setVisible(true);
    });

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

  /**
   * Create config menu in main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createConfigureMenu(final JMenuBar menuBar)
  {
    JMenu configureMenu = new JMenu(strings.getString("menu_configure"));
    menuBar.add(configureMenu);

    JRadioButtonMenuItem metricItem = new JRadioButtonMenuItem(
        strings.getString("menu_metric_units"));
    JRadioButtonMenuItem imperialItem = new JRadioButtonMenuItem(
        strings.getString("menu_imperial_units"));

    ButtonGroup unitGroup = new ButtonGroup();
    unitGroup.add(metricItem);
    unitGroup.add(imperialItem);

    configureMenu.add(metricItem);
    configureMenu.add(imperialItem);

    metricItem.setSelected(UnitSystemPreferences.isMetric());
    imperialItem.setSelected(UnitSystemPreferences.isImperial());

    metricItem.addActionListener(e -> setUnitSystem(UnitSystemPreferences.UnitSystem.METRIC));
    imperialItem.addActionListener(e -> setUnitSystem(UnitSystemPreferences.UnitSystem.IMPERIAL));
  }

  /**
   * Creates the actual main window.
   * 
   */
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

  /**
   * Creates the help menu in the main window.
   * 
   * @param menuBar
   *          of the application
   */
  private void createHelpMenu(final JMenuBar menuBar)
  {
      JMenu helpMenu = new JMenu(strings.getString("menu_help"));
      menuBar.add(helpMenu);

      JMenuItem userGuide = new JMenuItem(strings.getString("menu_item_user_guide"));
      helpMenu.add(userGuide);

      // Add an action listener to open the HTML guide when User Guide is clicked
      userGuide.addActionListener(e -> openHtmlGuide());

      // Create and add the About menu item
      JMenuItem aboutItem = new JMenuItem(strings.getString("menu_item_about"));
      helpMenu.add(aboutItem);

      // Add an action listener to open the About window when clicked
      aboutItem.addActionListener(e -> {
          AboutWindow aboutWindow = new AboutWindow(currentLocale);
          aboutWindow.setVisible(true);
      });
  }


  /**
   * Sets the correct html guide based on language.
   * 
   * @param locale
   *          is the language
   */
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
        htmlPath = "/gui/indexEn.html"; // English guide
        break;
    }
  }

  /**
   * Opens the correct html path for the language.
   * 
   */
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
      htmlContent = htmlContent.replaceAll("src=\"../gui/", "src=\"gui/"); // Correct paths in HTML
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

  /**
   * Gets all the images to be used in the application.
   * 
   * @param destinationDir
   *          where files are located
   * @throws IOException
   *           if file isn't found
   */
  private void copyImagesFromGuiFolder(final File destinationDir) throws IOException
  {
    // Assuming images are in the "gui" folder inside the JAR
    String[] imageFiles = {"gui/mainEn.png", "gui/recipeButtonsEn.png", "gui/recipeUtensilEn.png",
        "gui/recipeStepEn.png", "gui/recipeIngredientEn.png", "gui/buttonsEn.png",
        "gui/mealSelectEn.png", "gui/mealNameEn.png", "gui/unitResultEn.png",
        "gui/unitSelectEn.png", "gui/buttonsLessEn.png", "gui/buttonsMoreEn.png",
        "gui/calorieResultEn.png", "gui/calorieSelectEn.png", "gui/searchRTopEn.png",
        "gui/searchRBottomEn.png", "gui/processStepEn.png", "gui/processUtensil.png",
        "gui/processButtonEn.png", "gui/configEn.png", "gui/chooseEn.png",
        "gui/shoplistEn.png", "gui/print.png", "gui/drinkEn.png","gui/configIt.png",
        "gui/calorieResultIt.png", "gui/unitResultIt.png", "gui/shoplistIt.png",
        "gui/chooseIt.png", "gui/searchRBottomIt.png", "gui/searchRTopIt.png",
        "gui/mealSelectIt.png", "gui/mealNameIt.png", "gui/drinkIt.png",
        "gui/recipeStepIt.png", "gui/recipeIngredientIt.png", "gui/recipeUtensilIt.png",
        "gui/recipeButtonsIt.png", "gui/mainIt.png", "gui/calorieSelectIt.png"

    };

    for (String imagePath : imageFiles)
    {
      copyResource(imagePath, destinationDir);
    }
  }

  /**
   * Copies a resource from the JAR file to a specified destination directory.
   * 
   * @param resourcePath
   *          where the file is in jar
   * @param destinationDir
   *          where the resource should be copied to
   * @throws IOException
   *           error occurs during the copying process
   */
  private void copyResource(final String resourcePath, final File destinationDir) throws IOException
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

  /**
   * Main method to call to get everything working.
   * 
   * @param args
   *          string
   */
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
