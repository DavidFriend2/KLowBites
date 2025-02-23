package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import Information.*;
import UnitConversion.MassVolumeConverter;

/**
 * Shopping List window.
 * 
 * @author ryan mendez
 */
public class ShoppingListWindow extends JFrame implements Printable
{

  private static final long serialVersionUID = 1L;
  private static final String PRINT_ICON_PATH = "/img/print.png";
  private JList<RecipeIngredient> shoppingJlist;
  private JTextField people;
  private Meal loadedMeal;
  private Recipe loadedRecipe;
  private String fileOpened;
  private ResourceBundle strings;

  /**
   * Constructor for window.
   * 
   * @param locale
   */
  public ShoppingListWindow(final Locale locale)
  {
    strings = ResourceBundle.getBundle("resources.Strings", locale);
    // based on locale
    setTitle(strings.getString("shopping_list_title"));
    setSize(500, 750);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Main Panel
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Print Panel =
    JPanel printPanel = new JPanel(new BorderLayout());
    JButton printButton = createButton(PRINT_ICON_PATH, 50, 50, "print button");
    printButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        printRecipe();
      }
    });
    printPanel.add(printButton, BorderLayout.WEST);

    // Directory chooser Panel
    JPanel chooserPanel = new JPanel();
    // JLabel chooserLabel = new JLabel(strings.getString("choose_directory_label"));
    // Internationalized label
    // JButton chooseDirectoryButton = new JButton(strings.getString("choose_button"));
    JButton chooseDirectoryButton = new JButton(strings.getString("choose_meal_or_recipe"));
    // Internationalized button
    chooseDirectoryButton.addActionListener(openMealorRecipe);
    chooserPanel.add(chooseDirectoryButton);

    // people panel
    JPanel peoplePanel = new JPanel();
    people = new JTextField(4);
    peoplePanel.add(new JLabel(strings.getString("number_of_people")));
    peoplePanel.add(people);
    JButton updatePeople = new JButton(strings.getString("enter"));
    updatePeople.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        System.out.print("Button clicked");
        peopleUpdated();
      }
    });
    peoplePanel.add(updatePeople);

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(printPanel, BorderLayout.NORTH);
    topPanel.add(chooserPanel, BorderLayout.CENTER);
    topPanel.add(peoplePanel, BorderLayout.SOUTH);

    // Shopping list panel
    // display ingredients **** needs to be done;
    JPanel shoppingListPanel = new JPanel(new BorderLayout());
    JLabel shoppingLabel = new JLabel(strings.getString("shopping_list_label"));
    shoppingLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
    shoppingListPanel.add(shoppingLabel, BorderLayout.NORTH);
    shoppingJlist = new JList<>(new DefaultListModel<>());
    shoppingJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shoppingJlist.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(final java.awt.event.MouseEvent evt)
      {
        if (evt.getClickCount() == 2)
        { // Double-click event
          int index = shoppingJlist.locationToIndex(evt.getPoint());
          if (index >= 0)
          {
            RecipeIngredient selectedIngredient = shoppingJlist.getModel().getElementAt(index);
            unitEditor(selectedIngredient, index);
          }
        }
      }
    });

    shoppingJlist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    shoppingListPanel.add(new JScrollPane(shoppingJlist), BorderLayout.CENTER);
    JLabel guide = new JLabel(strings.getString("select_ingredient_guide"));
    guide.setFont(new Font("Times new Roman", Font.HANGING_BASELINE, 12));
    shoppingListPanel.add(guide, BorderLayout.SOUTH);

    // main panel
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(shoppingListPanel, BorderLayout.CENTER);

    add(mainPanel);
  }

  /**
   * Opens meal or recipe action listener.
   */
  private ActionListener openMealorRecipe = e -> {

    UIManager.put("FileChooser.folderNameLabelText", strings.getString("file_chooser_folder_name"));
    UIManager.put("FileChooser.lookInLabelText", strings.getString("file_chooser_look_in"));
    UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
    UIManager.put("FileChooser.filesOfTypeLabelText",
        strings.getString("file_chooser_files_of_type"));
    UIManager.put("FileChooser.upFolderToolTipText", strings.getString("file_chooser_up_folder"));
    UIManager.put("FileChooser.homeFolderToolTipText",
        strings.getString("file_chooser_home_folder"));
    UIManager.put("FileChooser.newFolderToolTipText", strings.getString("file_chooser_new_folder"));
    UIManager.put("FileChooser.listViewButtonToolTipText",
        strings.getString("file_chooser_list_view"));
    UIManager.put("FileChooser.detailsViewButtonToolTipText",
        strings.getString("file_chooser_details_view"));
    UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
    UIManager.put("FileChooser.openButtonText", strings.getString("file_chooser_open_button"));
    UIManager.put("FileChooser.cancelButtonText", strings.getString("file_chooser_cancel_button"));
    UIManager.put("FileChooser.acceptAllFileFilterText",
        strings.getString("file_chooser_all_files"));

    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        strings.getString("file_chooser_recipe_meal_files"),
        "rcp", "mel");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
      try
      {
        // check if its a meal or recipe
        String file = chooser.getSelectedFile().getAbsolutePath();
        fileOpened = file;
        if (file.endsWith(".rcp"))
        {
          loadedMeal = null;
          loadedRecipe = Recipe.loadRecipeFromFile(file);
          updateShoppingList(new ShoppingList(loadedRecipe, getPeopleCount()));
        }
        else if (file.endsWith(".mel"))
        {
          loadedRecipe = null;
          loadedMeal = Meal.loadMealFromFile(file);
          updateShoppingList(new ShoppingList(loadedMeal, getPeopleCount()));
        }

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  };

  /**
   * Update people method.
   * 
   * Updates the people to shop for based on entered count
   */
  private void peopleUpdated()
  {
    int peopleCount = getPeopleCount();

    try
    {
      if (loadedRecipe != null)
      {
        loadedRecipe = Recipe.loadRecipeFromFile(fileOpened);
        updateShoppingList(new ShoppingList(loadedRecipe, peopleCount));
      }
      else if (loadedMeal != null)
      {
        loadedMeal = Meal.loadMealFromFile(fileOpened);
        updateShoppingList(new ShoppingList(loadedMeal, peopleCount));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  /**
   * Update shopping list method.
   * 
   * @param shoppingList to update
   */
  private void updateShoppingList(final ShoppingList shoppingList)
  {

    DefaultListModel<RecipeIngredient> model = (DefaultListModel<RecipeIngredient>) shoppingJlist
        .getModel();
    model.clear();
    for (RecipeIngredient ingredient : shoppingList.getShoppingList())
    {
      model.addElement(ingredient);
    }
  }

  /**
   * Get amount of people entered.
   * 
   * @return amount of people to shop for
   */
  private int getPeopleCount()
  {
    try
    {
      return Integer.parseInt(people.getText().trim());
    }
    catch (NumberFormatException e)
    {
      return 0; // Default to 1 if the input is invalid
    }
  }

  /**
   * Unit editor.
   * 
   * @param ingredient to edit
   * @param index to change
   */
  private void unitEditor(final RecipeIngredient ingredient, final int index)
  {
    UnitSystemPreferences.UnitSystem currentSystem = UnitSystemPreferences.getCurrentUnitSystem();
    String[] availableUnits = UnitSystemPreferences.getUnitsForCurrentSystem(strings);

    JComboBox<String> unitDropdown = new JComboBox<>(availableUnits);
    unitDropdown.setSelectedItem(ingredient.getUnit());

    int result = JOptionPane.showConfirmDialog(this, unitDropdown,
        strings.getString("edit_unit_for") + ingredient.getName(), JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION)
    {
      String newUnit = (String) unitDropdown.getSelectedItem();
      Ingredient ingr = Ingredient.getIngredientbyName(ingredient.getName());
      MassVolumeConverter.Unit fromUnit = ShoppingList.getUnitFromString(ingredient.getUnit());
      MassVolumeConverter.Unit toUnit = ShoppingList.getUnitFromString(newUnit);

      double converted = ShoppingList.convert(ingredient.getAmount(), fromUnit, toUnit, ingr);
      ingredient.setAmount(converted);
      ingredient.setUnit(newUnit);

      ((DefaultListModel<RecipeIngredient>) shoppingJlist.getModel()).set(index, ingredient);
    }
  }

  /**
   * Create print button method.
   * 
   * @param imagePath
   * @param width
   * @param height
   * @param toolTipText
   * @return button
   */
  private JButton createButton(final String imagePath, final int width, 
      final int height, final String toolTipText)
  {
    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

    JButton button = new JButton();

    if (icon != null)
    {
      Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
      button.setIcon(new ImageIcon(img));
    }

    button.setToolTipText(toolTipText);
    button.setPreferredSize(new Dimension(width, height));
    return button;
  }

  /**
   * Print shopping listmethod.
   * 
   * prints the shopping list
   */
  private void printRecipe()
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);

    if (job.printDialog())
    {
      try
      {
        job.print();
      }
      catch (PrinterException e)
      {
        JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Print overide method.
   */
  @Override
  public int print(final Graphics graphics, final PageFormat pageFormat, 
      final int pageIndex) throws PrinterException
  {
    if (pageIndex > 0)
    {
      return NO_SUCH_PAGE; // Only one page for simplicity
    }

    Graphics g2d = (Graphics) graphics;
    g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

    // Render the shopping list panel
    shoppingJlist.paint(g2d);

    return PAGE_EXISTS;
  }

  /**
   * Main program.
   * 
   * @param args
   */
  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> 
    {
      // You can change this to the desired default locale
      Locale desiredLocale = Locale.getDefault();

      // Create a new MealSearcher instance with the desired locale
      // ShoppingListWindow mealSearcher = new ShoppingListWindow(desiredLocale);
      ShoppingListWindow shoppinglist = new ShoppingListWindow(desiredLocale);
      shoppinglist.setVisible(true);
    });
  }

}
