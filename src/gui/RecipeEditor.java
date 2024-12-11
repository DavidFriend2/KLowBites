package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import Information.Ingredient;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;
import gui.EditorListeners.*;

/**
 * recipe class.
 */
public class RecipeEditor extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -1922930697879333939L;
  private String delete = "Delete";
  private String add = "Add";
  private String name = "Name: ";
  private JPanel mainPanel;
  
  private SaveListener saveListener;
  private OpenListener openListener;
  private JButton openButton;
  private JButton closeButton;
  private JButton saveButton;
  private JButton newButton;
  private JButton saveAsButton;
  private ResourceBundle strings;
  @SuppressWarnings("unused")
private String state;
  private Locale currentLocale;
  private List<JComponent> components;
  
  public DefaultListModel<String> dlm = new DefaultListModel<>(); // ingredients held in this
  public List<RecipeIngredient> fullIngredientList = new ArrayList<>();
  public DefaultListModel<String> dlm3 = new DefaultListModel<>();
  public List<Step> fullStepList = new ArrayList<>();
  public DefaultListModel<String> dlm2 = new DefaultListModel<>();
  public List<Utensil> fullUtensilList = new ArrayList<>();
  
  /**
   * recipe.
   * 
   * @param locale l
   */
  public RecipeEditor(final Locale locale) {
    this.currentLocale = locale;
    this.components = new ArrayList<>();
    strings = ResourceBundle.getBundle("resources.Strings", locale); // Corrected line
    setTitle(strings.getString("recipe_editor_title"));
    setSize(825, 800);
    setResizable(false);
    setLocationRelativeTo(null);
    
    // Create main content panel
    mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    
    // Create image panel with FlowLayout aligned to the left
    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    
    // Name panel to add name and serves ---------------------------------
    JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel nameLabel = new JLabel(strings.getString("label_name"));
    JTextField nameText = new JTextField(25);
    components.add(nameText);
    JLabel serves = new JLabel(strings.getString("label_serves"));
    JTextField servesText = new JTextField(10);
    components.add(servesText);
    namePanel.add(nameLabel);
    namePanel.add(nameText);
    namePanel.add(serves);
    namePanel.add(servesText);
    
    //utensils editor ---------------------------------------------
    JPanel utensilPanel = new JPanel(new BorderLayout());
    utensilPanel.setPreferredSize(new Dimension(800,200));
    TitledBorder utensilBorder = new TitledBorder(strings.getString("label_utensils"));
    utensilPanel.setBorder(utensilBorder);

    //Top half of utensil editor -----------
    JPanel utensilInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel utensilName = new JLabel(strings.getString("label_name"));
    JTextField utensilNameText = new JTextField(20);
    components.add(utensilNameText);
    JLabel details = new JLabel(strings.getString("label_details"));
    JTextField detailsText = new JTextField(15);
    components.add(detailsText);
    JButton addUtensil = new JButton(strings.getString("button_add"));
    
    utensilInputs.add(utensilName);
    utensilInputs.add(utensilNameText);
    utensilInputs.add(details);
    utensilInputs.add(detailsText);
        
        
  //Bottom half of utensil editor ----------
    JPanel utensilBottom = new JPanel(new BorderLayout());
    JList utensilsList = new JList(dlm2);
    JScrollPane utensilScroll = new JScrollPane(utensilsList);
    utensilBottom.add(utensilScroll);
    //button
    JPanel utensilBR = new JPanel(new BorderLayout());
    JButton utensilDelete = new JButton(strings.getString("button_delete"));

    utensilBR.add(utensilDelete, BorderLayout.SOUTH);
    utensilBR.add(addUtensil, BorderLayout.NORTH);
    utensilBottom.add(utensilBR, BorderLayout.EAST);
    //add everything to panel
    utensilPanel.add(utensilBottom, BorderLayout.SOUTH);
    utensilPanel.add(utensilInputs, BorderLayout.NORTH);

    //ingredients editor ---------------------------------------------
    JPanel ingPanel = new JPanel(new BorderLayout());
    ingPanel.setPreferredSize(new Dimension(800,200));
    TitledBorder ingBorder = new TitledBorder(strings.getString("label_ingredients"));
    ingPanel.setBorder(ingBorder);

    //Top half of ingredients editor -----------
    JPanel ingInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel ingName = new JLabel(strings.getString("label_name"));
    JTextField ingNameInput = new JTextField(8);
    components.add(ingNameInput);
    JLabel ingDetails = new JLabel(strings.getString("label_details"));
    JTextField ingDetailsInput = new JTextField(6);
    components.add(ingDetailsInput);
    JLabel ingAmount = new JLabel(strings.getString("label_amount"));
    JTextField ingAmountInput = new JTextField(3);
    components.add(ingAmountInput);
    JLabel ingUnits = new JLabel(strings.getString("label_units"));
    JComboBox<String> ingUnitCombo = new JComboBox<String>();
    components.add(ingUnitCombo);
    for (String unit : UnitSystemPreferences.getUnitsForCurrentSystem(strings)) {
        ingUnitCombo.addItem(unit);
    }
    JButton ingAdd = new JButton(strings.getString("button_add"));

    ingInputs.add(ingName);
    ingInputs.add(ingNameInput);
    ingInputs.add(ingDetails);
    ingInputs.add(ingDetailsInput);
    ingInputs.add(ingAmount);
    ingInputs.add(ingAmountInput);
    ingInputs.add(ingUnits);
    ingInputs.add(ingUnitCombo);

  //bottom half of ingredients-----------
    JPanel ingBottom = new JPanel(new BorderLayout());
    JList<String> ingList = new JList(dlm);
    JScrollPane sp = new JScrollPane(ingList);
    ingBottom.add(sp);
    //button
    JPanel ingBR = new JPanel(new BorderLayout());
    JButton ingDelete = new JButton(strings.getString("button_delete"));

    ingBR.add(ingDelete, BorderLayout.SOUTH);
    ingBR.add(ingAdd, BorderLayout.NORTH);
    ingBottom.add(ingBR, BorderLayout.EAST);

    ingPanel.add(ingBottom, BorderLayout.SOUTH);
    ingPanel.add(ingInputs, BorderLayout.NORTH);

    //steps editor ---------------------------------------------------
    JPanel stepPanel = new JPanel(new BorderLayout());
    stepPanel.setPreferredSize(new Dimension(800,200));
    TitledBorder stepBorder = new TitledBorder(strings.getString("label_steps"));
    stepPanel.setBorder(stepBorder);

    //top half of steps
    JPanel stepInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel stepAction = new JLabel(strings.getString("label_action"));
    JComboBox<String> stepActionCombo = new JComboBox<String>();
    components.add(stepActionCombo);
    stepActionCombo.addItem("");
    for (String s : Step.getActions()) 
    {
      stepActionCombo.addItem(s);
    }
    JLabel stepOn = new JLabel(strings.getString("label_on"));
    JComboBox<String> stepOnCombo = new JComboBox<>();
    stepOnCombo.addItem("");
    components.add(stepOnCombo);

    JLabel stepUtensil = new JLabel(strings.getString("label_utensil"));
    JComboBox<String> stepUtensilCombo = new JComboBox<String>();
    stepUtensilCombo.addItem("");
    components.add(stepUtensilCombo);
    JLabel stepDetails = new JLabel(strings.getString("label_details"));
    JLabel time = new JLabel(strings.getString("label_time"));
    JTextField timeInput = new JTextField(3);
    components.add(timeInput);
    JTextField stepDetailsText = new JTextField(5);
    components.add(stepDetailsText);
    JButton stepAdd = new JButton(strings.getString("button_add"));
    AddStepListener stepAddListen = new AddStepListener(stepActionCombo,
        stepOnCombo, stepUtensilCombo, stepDetailsText, timeInput);
    stepAdd.addActionListener(stepAddListen);
    stepInputs.add(stepAction);
    stepInputs.add(stepActionCombo);
    stepInputs.add(stepOn);
    stepInputs.add(stepOnCombo);
    stepInputs.add(stepUtensil);
    stepInputs.add(stepUtensilCombo);
    stepInputs.add(stepDetails);
    stepInputs.add(stepDetailsText);
    stepInputs.add(time);
    stepInputs.add(timeInput);
        

  //bottom half of step-----------
    JPanel stepBottom = new JPanel(new BorderLayout());
    JList<String> stepList = new JList(dlm3);
    JScrollPane stepScroll = new JScrollPane(stepList);
    stepBottom.add(stepScroll);
    //button
    JPanel stepBR = new JPanel(new BorderLayout());
    JButton stepDelete = new JButton(strings.getString("button_delete"));
    DeleteStepListener stepDeleteListener = new DeleteStepListener(stepList);
    stepDelete.addActionListener(stepDeleteListener);
    stepBR.add(stepDelete, BorderLayout.SOUTH);
    stepBR.add(stepAdd, BorderLayout.NORTH);
    stepBottom.add(stepBR, BorderLayout.EAST);
    stepPanel.add(stepInputs, BorderLayout.NORTH); 
    stepPanel.add(stepBottom, BorderLayout.SOUTH);

    //utensil listener
    AddUtensilListener utensilAddListen = new AddUtensilListener(utensilNameText,
            detailsText, stepUtensilCombo, stepOnCombo);
    addUtensil.addActionListener(utensilAddListen);
    DeleteUtensilListener utensilDeleteListener = 
        new DeleteUtensilListener(utensilsList, stepUtensilCombo, stepOnCombo);
    utensilDelete.addActionListener(utensilDeleteListener);
    AddIngListener ingAddListen = new AddIngListener(ingNameInput,
            ingDetailsInput, ingAmountInput, ingUnitCombo, stepOnCombo);
    ingAdd.addActionListener(ingAddListen);
    DeleteIngListener ingDeleteListener= new DeleteIngListener(ingList, stepOnCombo);
    ingDelete.addActionListener(ingDeleteListener);
    
    //pairings
    JPanel pairPanel = new JPanel(new BorderLayout());
    pairPanel.setPreferredSize(new Dimension(200, 25));
    JLabel pairLabel = new JLabel(strings.getString("label_pairing"));
    JTextField pairText = new JTextField();
    components.add(pairText);
    pairPanel.add(pairLabel, BorderLayout.WEST);
    pairPanel.add(pairText);

    //initialize new button ----------------------------------------------
    ImageIcon newIcon = createImageIcon("/img/new.png");
    Image newImg = newIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    newButton = new JButton(new ImageIcon(newImg));
    newButton.setPreferredSize(new Dimension(50, 50));
    newButton.setToolTipText(strings.getString("tooltip_new"));
    NewListener newListener = new NewListener();
    //newButton.addActionListener(newListener);
    imagePanel.add(newButton);

    //initialize open button ----------------------------------------------
    ImageIcon openIcon = createImageIcon("/img/open.png");
    Image openImg = openIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    openButton = new JButton(new ImageIcon(openImg));
    openButton.setPreferredSize(new Dimension(50, 50));
    openButton.setToolTipText(strings.getString("tooltip_open"));
    imagePanel.add(openButton);

    //initialize save button ----------------------------------------------
    ImageIcon saveIcon = createImageIcon("/img/save.png");
    Image saveImg = saveIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    saveButton = new JButton(new ImageIcon(saveImg));
    saveButton.setPreferredSize(new Dimension(50, 50));
    saveButton.setToolTipText(strings.getString("tooltip_save"));
    saveButton.setEnabled(false);
    imagePanel.add(saveButton);

    //initialize save as button ----------------------------------------------
    ImageIcon saveAsIcon = createImageIcon("/img/saveas.png");
    Image saveAsImg = saveAsIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    saveAsButton = new JButton(new ImageIcon(saveAsImg));
    saveAsButton.setPreferredSize(new Dimension(50, 50));
    saveAsButton.setToolTipText(strings.getString("tooltip_save_as"));
    saveAsButton.setEnabled(false);
    imagePanel.add(saveAsButton);
      
    ImageIcon closeIcon = createImageIcon("/img/close.png");
    Image closeImg = closeIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    closeButton = new JButton(new ImageIcon(closeImg));
    closeButton.setPreferredSize(new Dimension(50, 50));
    closeButton.setToolTipText(strings.getString("tooltip_close"));
    closeButton.setEnabled(false);
    CloseListener closeListener = new CloseListener();
    closeButton.addActionListener(closeListener);
    imagePanel.add(closeButton);

    openListener = new OpenListener(nameText, servesText, ingNameInput,
        ingDetailsInput, ingAmountInput, ingUnitCombo, ingList, stepList,
        utensilsList, stepOnCombo, stepUtensilCombo, fullIngredientList,
        fullStepList, fullUtensilList, dlm, dlm2, dlm3, currentLocale, openButton, saveButton,
        closeButton, components, newButton, pairText);
    openButton.addActionListener(openListener);
    
    SaveAsListener saveAsListener = new SaveAsListener(nameText, 
        servesText, fullIngredientList, fullUtensilList, fullStepList, components, saveButton,
        closeButton, newButton, currentLocale, pairText);
    saveAsButton.addActionListener(saveAsListener);
    
    saveListener = new SaveListener(openListener, saveAsListener, nameText, 
        servesText, fullIngredientList, fullStepList, fullUtensilList, saveButton,
        closeButton, newButton, components, pairText);
    saveButton.addActionListener(saveListener);
    
    newButton.addActionListener(saveAsListener);
    new ChangeTracker(closeButton, newButton, components, saveAsButton);
    
    mainPanel.add(imagePanel);
    mainPanel.add(namePanel);
    mainPanel.add(utensilPanel);
    mainPanel.add(ingPanel);
    mainPanel.add(stepPanel);
    mainPanel.add(pairPanel);

    add(mainPanel);
  }
  
  /**
   * main.
   * 
   * @return main
   */
  protected JPanel getMainPanel() 
  {
    return mainPanel;
  }

  /**
   * button.
   * 
   * @return button
   */
  protected JButton getOpenButton() 
  {
    return openButton;
  }
  
  /**
   * button.
   * 
   * @return button
   */
  protected JButton getCloseButton() 
  {
    return closeButton;
  }

  /**
   * button.
   * 
   * @return button
   */
  protected SaveListener getSaveListener() 
  {
    return saveListener;
  }
  
  /**
   * button.
   * 
   * @return listen
   */
  protected OpenListener getOpenListener() 
  {
    return openListener;
  }
  
  /**
   * comps.
   * 
   * @return comps
   */
  protected List<JComponent> getComps() {
    return components;
  }

  /**
   * create icon.
   * 
   * @param path path
   * @return images
   */
  public ImageIcon createImageIcon(final String path) 
  {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) 
    {
      return new ImageIcon(imgURL);
    }
    else 
    {
      System.err.println(strings.getString("error_file_not_found") + path);
      return null;
    }
  }

  /**
   * listen.
   */
  private class NewListener implements ActionListener 
  {
    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      //new SaveAsListener()
    }
  }
  
  /**
   * listen.
   */
  private class AddIngListener implements ActionListener 
  {
    JTextField ingName;
    JTextField ingDetail;
    JTextField ingAmount;
    JComboBox<String> ingUnit;
    JComboBox<String> stepOnCombo;
  
    /**
     * add listen.
     * 
     * @param ingName name
     * @param ingDetail detail
     * @param ingAmount amount
     * @param ingUnit unit
     * @param stepOnCombo combo
     */
    public AddIngListener(final JTextField ingName, final JTextField ingDetail,
        final JTextField ingAmount, final JComboBox<String> ingUnit, 
        final JComboBox<String> stepOnCombo) 
    {
      this.ingName = ingName;
      this.ingDetail = ingDetail;
      this.ingAmount = ingAmount;
      this.ingUnit = ingUnit;
      this.stepOnCombo = stepOnCombo;
    }
  
    @SuppressWarnings("unchecked")
  @Override
  /**
   * action.
   */
  public void actionPerformed(final ActionEvent e) 
    {
      try 
      {
          // Load ingredients
        Ingredient.setIngredients(Ingredient.loadIngredients(
            "IngredientsNutrition/ingredients.ntr"));
  
        Ingredient exists = Ingredient.getIngredientbyName(ingName.getText());
  
        if (exists == null) 
        {
              // If the ingredient isn't found, prompt to make a new one
          JTextField newname = new JTextField(ingName.getText());
          JTextField newcals = new JTextField();
          JTextField newgperml = new JTextField();
          JPanel newpanel = new JPanel(new GridLayout(0, 1));
          newpanel.add(new JLabel(strings.getString("label_ingredient_name")));
          newpanel.add(newname);
          newpanel.add(new JLabel(strings.getString("label_calories_per_100g")));
          newpanel.add(newcals);
          newpanel.add(new JLabel(strings.getString("label_grams_per_ml")));
          newpanel.add(newgperml);

          int result = JOptionPane.showConfirmDialog(null, newpanel,
                  strings.getString("dialog_add_new_ingredient"),
                  JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

          // Get inputs and make a new ingredient, then save it
          if (result == JOptionPane.OK_OPTION) 
          {
            String name = newname.getText();
            int cals = Integer.parseInt(newcals.getText());
            double gramsper = Double.parseDouble(newgperml.getText());

            Ingredient newIngredient = new Ingredient(name, cals, gramsper);
            Ingredient.addIngredient(newIngredient);
            Ingredient.saveIngredients("IngredientsNutrition/ingredients.ntr");
  
                  // Add ingredient to list, using name in case they change it while adding
            fullIngredientList.add(new RecipeIngredient(name, ingDetail.getText(),
                Double.valueOf(ingAmount.getText()), ingUnit.getSelectedItem().toString()));
          }
        } 
        else 
        {
          fullIngredientList.add(new RecipeIngredient(ingName.getText(), ingDetail.getText(),
                  Double.valueOf(ingAmount.getText()), ingUnit.getSelectedItem().toString()));
        }
  
          // Adding the ingredient to the list display
        if (!ingDetail.getText().equals("")) 
        {
          dlm.addElement(ingAmount.getText() 
              + " " + ((String) ingUnit.getSelectedItem()).toLowerCase() +
                  " (" + ingDetail.getText() + ") " + ingName.getText());
        } 
        else 
        {
          dlm.addElement(ingAmount.getText() + " " + ((
              String) ingUnit.getSelectedItem()).toLowerCase() 
              + " " + ingName.getText());
        }
        stepOnCombo.addItem(ingName.getText());
        ingName.setText("");
        ingDetail.setText("");
        ingAmount.setText("");
        ingUnit.setSelectedItem("");
      } catch (IOException | ClassNotFoundException ex) 
      {
        JOptionPane.showMessageDialog(null,
                  strings.getString("error_loading_saving_ingredients"),
                  strings.getString("error_title"),
                  JOptionPane.ERROR_MESSAGE);
      } 
      catch (NumberFormatException ex) 
      {
        JOptionPane.showMessageDialog(null,
                  strings.getString("error_new_ingredient_inputs"),
                  strings.getString("error_title"),
                    JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  /**
   * step.
   */
  private class AddStepListener implements ActionListener 
  {
    JComboBox<String> stepAction;
    JComboBox<String> stepOn;
    JComboBox<String> stepUtensil;
    JTextField stepDetails;
    JTextField time;
  
    /**
     * add step.
     * 
     * @param stepAction action
     * @param stepOn on
     * @param stepUtensil utensil
     * @param stepDetails details
     * @param time time
     */
    public AddStepListener(final JComboBox<String> stepAction, 
        final JComboBox<String> stepOn, final JComboBox<String> stepUtensil,
        final JTextField stepDetails, final JTextField time) 
    {
      this.stepAction = stepAction;
      this.stepOn = stepOn;
      this.stepUtensil = stepUtensil;
      this.stepDetails = stepDetails;
      this.time = time;
    }
  
    @Override
    /**
     * action.
     */
    public void actionPerformed(final ActionEvent e) 
    {
        // Add info to the list
      boolean isUtensil = false;
      for (Utensil ut : fullUtensilList) {
        if (ut.getName().equals(stepOn.getSelectedItem().toString())) {
          isUtensil = true;
          break;
        }
      }
      StringBuilder stepDescription;
      if (isUtensil) {
        stepDescription = new StringBuilder();
        stepDescription.append(stepAction.getSelectedItem().toString())
                       .append(" the ")
                       .append("contents of the " + stepOn.getSelectedItem().toString())
                       .append(" in the ")
                       .append(stepUtensil.getSelectedItem().toString());
      } else {
        stepDescription = new StringBuilder();
        stepDescription.append(stepAction.getSelectedItem().toString())
                       .append(" the ")
                       .append(stepOn.getSelectedItem().toString())
                       .append(" in the ")
                       .append(stepUtensil.getSelectedItem().toString());
      }

      if (!stepDetails.getText().equals("")) 
      {
        stepDescription.append(" " + stepDetails.getText()).append(". ");
      }

      stepDescription.append(strings.getString("estimated_time"))
                     .append(time.getText()).append(" minutes");

      dlm3.addElement(stepDescription.toString());
      
      fullStepList.add(new Step(
          stepAction.getSelectedItem().toString(),
          stepOn.getSelectedItem().toString(),
          stepUtensil.getSelectedItem().toString(),
          stepDetails.getText(),
          Double.parseDouble(time.getText())
      ));
      stepDetails.setText("");
      time.setText("");
      stepAction.setSelectedItem("");
      stepOn.setSelectedItem("");
      stepUtensil.setSelectedItem("");
    }
  }
  
  /**
   * add utensil.
   */
  private class AddUtensilListener implements ActionListener 
  {

    JTextField utensilName;
    JTextField details;
    JComboBox stepUtensilCombo;
    JComboBox stepOn;
    
    /**
     * add utensil.
     * 
     * @param utensilName name
     * @param details detail
     * @param stepUtensilCombo combo
     * @param stepOn on
     */
    public AddUtensilListener(final JTextField utensilName, 
        final JTextField details, final JComboBox stepUtensilCombo, final JComboBox stepOn) 
    {
      this.details = details;
      this.utensilName = utensilName;
      this.stepUtensilCombo = stepUtensilCombo;
      this.stepOn = stepOn;
      
    }
    @SuppressWarnings("unchecked")
	@Override
	/**
	 * action.
	 */
    public void actionPerformed(final ActionEvent e) 
    {
      //add info to thing
      if (!details.getText().equals("")) 
      {
        dlm2.addElement(utensilName.getText()+ " (" + details.getText() + ")");
      } 
      else 
      {
        dlm2.addElement(utensilName.getText());
      }
      fullUtensilList.add(new Utensil(utensilName.getText(),
          details.getText()));
      stepUtensilCombo.addItem(utensilName.getText());
      stepOn.addItem(utensilName.getText());
      utensilName.setText("");
      details.setText("");
    }
    
  }
  
  /**
   * delete.
   */
  private class DeleteIngListener implements ActionListener 
  {
    JList<String> ingredientList;
    JComboBox<String> stepOnCombo;

    /**
     * delete.
     * 
     * @param ingredientList list
     * @param stepOnCombo combo
     */
    public DeleteIngListener(final JList<String> ingredientList, 
        final JComboBox<String> stepOnCombo)
    {
      this.ingredientList = ingredientList;
      this.stepOnCombo = stepOnCombo;
    }

    @Override
    /**
     * action.
     */
    public void actionPerformed(final ActionEvent e) 
    {
      int selectedIndex = ingredientList.getSelectedIndex();
      if (selectedIndex != -1) 
      { // Ensure an item is selected
        fullIngredientList.remove(selectedIndex);
        dlm.removeElement(ingredientList.getSelectedValue());
        stepOnCombo.removeAllItems();
        for (RecipeIngredient ri : fullIngredientList) 
        {
          stepOnCombo.addItem(ri.getName());
        }
      }
      else 
      {
        JOptionPane.showMessageDialog(null,
                  strings.getString("error_no_ingredient_selected"),
                  strings.getString("error_title"),
                  JOptionPane.WARNING_MESSAGE);
      }
    }
  }
  
  /**
   * delete.
   */
  private class DeleteUtensilListener implements ActionListener 
  {
    JList<String> utensilList;
    JComboBox<String> stepUtensilCombo;
    JComboBox stepOn;

    /**
     * delete utensil.
     * 
     * @param utensilList list
     * @param stepUtensilCombo combo
     * @param stepOn on
     */
    public DeleteUtensilListener(final JList<String> utensilList, 
        final JComboBox<String> stepUtensilCombo, JComboBox stepOn) 
    {
      this.utensilList = utensilList;
      this.stepUtensilCombo = stepUtensilCombo;
      this.stepOn = stepOn;
    }

    @Override
    /**
     * action.
     */
    public void actionPerformed(final ActionEvent e) 
    {
      int selectedIndex = utensilList.getSelectedIndex();
      if (selectedIndex != -1) 
      { // Ensure an item is selected
        stepOn.removeItem(fullUtensilList.get(selectedIndex).getName());
        fullUtensilList.remove(selectedIndex);
        dlm2.removeElement(utensilList.getSelectedValue());
        stepUtensilCombo.removeAllItems();
        for (Utensil r : fullUtensilList) 
        {
          stepUtensilCombo.addItem(r.getName());
        }
      } 
      else 
      {
        JOptionPane.showMessageDialog(null,
                  strings.getString("error_no_utensil_selected"),
                  strings.getString("error_title"),
                  JOptionPane.WARNING_MESSAGE);
      }
    }
  }
  
  /**
   * delete.
   */
  private class DeleteStepListener implements ActionListener 
  {
    JList<String> stepList;

    /**
     * delete step.
     * 
     * @param stepList step
     */
    public DeleteStepListener(final JList<String> stepList) 
    {
      this.stepList = stepList;
    }

    @Override
    /**
     * action.
     */
    public void actionPerformed(final ActionEvent e) 
    {
      int selectedIndex = stepList.getSelectedIndex();
      if (selectedIndex != -1) 
      { // Ensure an item is selected
        fullStepList.remove(selectedIndex);
        saveButton.setEnabled(true);
        dlm3.removeElement(stepList.getSelectedValue());
      } 
      else 
      {
        JOptionPane.showMessageDialog(null,
                  strings.getString("error_no_step_selected"),
                  strings.getString("error_title"),
                  JOptionPane.WARNING_MESSAGE);
      }
    }
  }
  
  /**
   * close.
   */
  private class CloseListener implements ActionListener 
  {
    @Override
    /**
     * action
     */
    public void actionPerformed(final ActionEvent e) 
    {
      RecipeEditor re = new RecipeEditor(currentLocale); // Pass the current locale
      setVisible(false);
      re.setVisible(true);
      closeButton.setEnabled(false);
    }
  }

  /**
   * main.
   * 
   * @param args args
   */
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> 
    {
      Locale locale = Locale.getDefault(); //change this
      new RecipeEditor(locale).setVisible(true);
    });
  }
}