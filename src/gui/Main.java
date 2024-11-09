package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
public class Main extends JFrame {
    private static final long serialVersionUID = 1293847254;

    // Load the resource bundle for internationalization
    private static ResourceBundle strings;
    private static Locale currentLocale;

    // Path to our logo image
    private static final String LOGO_PATH = "/img/logo.png";

    // Some UI components we'll need later
    private ImageIcon logoIcon;
    private JLabel logoLabel;
    private static UnitConverterWindow converterWindow;
    private static CalorieCalculatorWindow calorieWindow;

    // Constructor with locale parameter
    public Main(Locale locale) {
        loadStrings(locale);
        System.out.println("ResourceBundle loaded: " + (strings != null));
        if (strings != null) {
            initializeWindow();
            initializeLogo();
            createMenuBar();
            createMainPanel();
        } else {
            System.err.println("Cannot initialize window due to missing ResourceBundle");
        }
    }

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
        logoIcon = createImageIcon(LOGO_PATH);
        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon);
        } else {
            logoLabel = new JLabel(strings.getString("error_logo_not_found"));
        }
    }

    // Create the menu bar at the top of our window
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        createFileMenu(menuBar);
        createEditMenu(menuBar);
        // createSearchMenu(menuBar);
        createViewMenu(menuBar);
        createToolsMenu(menuBar);
        // createConfigureMenu(menuBar);
        // createHelpMenu(menuBar);
    }

    // Add the "File" menu to our menu bar
    private void createFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu(strings.getString("menu_file"));
        menuBar.add(fileMenu);

        // For now, we just have an exit option
        JMenuItem exitItem = new JMenuItem(strings.getString("menu_item_exit"));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
    }

    // Add the "Edit" menu to our menu bar
    private void createEditMenu(JMenuBar menuBar) {
        JMenu editMenu = new JMenu(strings.getString("menu_edit"));
        menuBar.add(editMenu);

        // Add a recipe editor option
        JMenuItem recipeEditor = new JMenuItem(strings.getString("menu_item_recipe"));
        editMenu.add(recipeEditor);
        recipeEditor.addActionListener(e -> {
            RecipeEditor recipeViewer = new RecipeEditor();
            recipeViewer.setVisible(true);
        });
    }

    // Add the "View" menu to our menu bar
    private void createViewMenu(JMenuBar menuBar) {
        JMenu viewMenu = new JMenu(strings.getString("menu_view"));
        menuBar.add(viewMenu);

        // Add a process viewer option
        JMenuItem viewProcess = new JMenuItem(strings.getString("menu_item_process"));
        viewMenu.add(viewProcess);
        viewProcess.addActionListener(e -> {
            ProcessViewer processViewer = new ProcessViewer();
            processViewer.setVisible(true);
        });
    }

    // Add the "Tools" menu to our menu bar
    private void createToolsMenu(JMenuBar menuBar) {
        JMenu toolsMenu = new JMenu(strings.getString("menu_tools"));
        menuBar.add(toolsMenu);

        // Add calorie calculator and unit converter options
        JMenuItem caloriesCalculatorItem = new JMenuItem(strings.getString("menu_item_calories_calculator"));
        toolsMenu.add(caloriesCalculatorItem);

        JMenuItem unitsConverterItem = new JMenuItem(strings.getString("menu_item_units_converter"));
        toolsMenu.add(unitsConverterItem);

        // Open the unit converter window when clicked
        unitsConverterItem.addActionListener(e -> {
            if (converterWindow == null || !converterWindow.isDisplayable()) {
                converterWindow = new UnitConverterWindow();
                converterWindow.setVisible(true);
            } else {
                converterWindow.toFront();
                converterWindow.requestFocus();
            }
        });

        // Open the calorie calculator window when clicked
        caloriesCalculatorItem.addActionListener(e -> {
            if (calorieWindow == null || !calorieWindow.isDisplayable()) {
                calorieWindow = new CalorieCalculatorWindow();
                calorieWindow.setVisible(true);
            } else {
                calorieWindow.toFront();
                calorieWindow.requestFocus();
            }
        });
    }

    // Create the main panel and add our logo to it
    private void createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        if (logoLabel != null) {
            mainPanel.add(logoLabel, BorderLayout.CENTER);
        }

        add(mainPanel);
    }

    // Helper method to load an image icon
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println(strings.getString("error_file_not_found") + path);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
        
        // Set the desired locale here
        // Locale desiredLocale = Locale.ITALIAN; // For Italian
        // Locale desiredLocale = new Locale("es", "ES"); // For Spanish
         Locale desiredLocale = Locale.ENGLISH; // For English

        SwingUtilities.invokeLater(() -> {
            Main window = new Main(desiredLocale);
            if (strings != null) {
                window.setVisible(true);
            } else {
                System.err.println("Cannot display window due to missing ResourceBundle");
            }
        });
    }
}