package gui;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import Information.Meal;
import Information.Recipe;
import gui.EditorListeners.ChangeTracker;

/**
 * Meal editor is saved as a Jframe.
 * 
 * @author natek
 */
public class MealEditor extends JFrame
{

  /**
   * I really wish i knew what this did.
   */
  private static final long serialVersionUID = -4907901058401288335L;
  private static final String OPEN = "Open";
  private static Locale currentLocale = Locale.getDefault();
  private JPanel mainPanel;
  private JButton close;
  private Map<JPanel, RecipeEditor> recipeEditorPanels = new HashMap<>();
  private List<RecipeEditor> recipeEditors = new ArrayList<>();
  private ResourceBundle strings;
  private JButton save;
  private JButton saveAs;
  private JButton open;
  private OpenListener ol;
  private SaveAsListener sal;
  private JTextField textBox;
  
  /**
   * default constructor for the window.
   */
  public MealEditor(final Locale locale) 
  {
    strings = ResourceBundle.getBundle("resources.Strings", 
        locale); // Load the resource bundle based on locale

    setTitle(strings.getString("meal_editor_title"));
    setSize(1000, 400);
    setResizable(false);
    setLocationRelativeTo(null);
      
    // Create main content panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
    // Create image panel with FlowLayout aligned to the left
    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
    buttonCreation buttonCreation = new buttonCreation();
    //initialize buttons
    buttonCreation.addImageButton(imagePanel, "/img/new.png", strings.getString("tooltip_new"));
    buttonCreation.addImageButton(imagePanel, "/img/open.png", strings.getString("tooltip_open"));
    buttonCreation.addImageButton(imagePanel, "/img/save.png", strings.getString("tooltip_save"));
    buttonCreation.addImageButton(imagePanel, "/img/saveAs.png",
        strings.getString("tooltip_save_as"));
    buttonCreation.addImageButton(imagePanel, "/img/close.png", strings.getString("tooltip_close"));
        
    // Add image panel to the top (NORTH) of the main panel
    mainPanel.add(imagePanel);

    // text box panel
    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textBox = new JTextField(20);
    textBox.getDocument().addDocumentListener(new TextFieldChanger());
    JLabel textLabel = new JLabel(strings.getString("label_name"));
    textBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    textPanel.add(textLabel);
    textPanel.add(textBox);
    mainPanel.add(textPanel);
        
    // add recipe window
    JPanel recipeBox = new JPanel(new BorderLayout());
    recipeBox.setLayout(new BoxLayout(recipeBox, BoxLayout.Y_AXIS));
    recipeBox.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10), 
        BorderFactory.createLineBorder(Color.BLACK, 2)));
        
    //top half of recipe box
    JPanel recipeBoxTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton addRecipe = new JButton(strings.getString("button_add_recipe"));
    JButton createRecipe = new JButton(strings.getString("button_create_recipe"));
    createRecipe.addActionListener(new CreateRecipeListener());
    recipeBoxTop.add(addRecipe);
    recipeBoxTop.add(createRecipe);
    recipeBox.add(recipeBoxTop);
        
    //bottom half of recipe box
    JPanel recipeBoxBottom = new JPanel(new BorderLayout());
    JList recipes = new JList();
    JScrollPane scroll = new JScrollPane(recipes);
    recipeBoxBottom.add(scroll);
    //bottom right half
    JPanel bottomRight = new JPanel(new BorderLayout());
    JButton delete = new JButton(strings.getString("button_delete"));
    bottomRight.add(delete, BorderLayout.SOUTH);
    recipeBoxBottom.add(bottomRight, BorderLayout.EAST);
    recipeBox.add(recipeBoxBottom);

    addRecipe.addActionListener(new AddRecipeListener(this, scroll, recipeBox));
    //add recipe box
    JScrollPane d = new JScrollPane(recipeBox);
    mainPanel.add(d);
        
    //Initialize buttons listeners
    JButton newB = (JButton) imagePanel.getComponent(0);
    newB.addActionListener(new NewListener());
    open = (JButton) imagePanel.getComponent(1);
    ol = new OpenListener(scroll, this, recipeBox, textBox, open);
    open.addActionListener(ol);
    save = (JButton) imagePanel.getComponent(2);
    save.setEnabled(false);
    sal = new SaveAsListener(textBox);
    saveAs = (JButton) imagePanel.getComponent(3);
    saveAs.addActionListener(sal);
    saveAs.setEnabled(false);
    close = (JButton) imagePanel.getComponent(4);
    close.addActionListener(new CloseListener());
    save.addActionListener(new SaveListener(textBox, ol, sal));
        
    //initialize delete button listener
    DeleteListener dl = new DeleteListener(recipeBox, recipeBoxBottom, scroll, this);
    delete.addActionListener(dl);
            
    //add everything
    add(mainPanel);
  }
  
  private JButton getCloseButton() 
  {
    return close;
  }
  
  //Adds the recipe editor to the jscrollpane or to a new one
  private class AddRecipeListener implements ActionListener 
  {

    JFrame frame;
    JScrollPane scroll;
    JPanel panel;
    
    public AddRecipeListener(final JFrame frame, final JScrollPane scroll, final JPanel panel) 
    {
      this.frame = frame;
      this.scroll = scroll;
      this.panel = panel;
    }
    public void actionPerformed(final ActionEvent e)
    {
      if (recipeEditorPanels.size() > 0) 
      {
        RecipeEditor re = new RecipeEditor(currentLocale);
        //String temp = ol.getCurrentFileName();
        re.getOpenButton().doClick();
        if (re.getOpenListener().getCurrentFileName() != null) 
        {
          JPanel recipeBoxBottom = new JPanel(new BorderLayout());
          JList recipes = new JList();
          JScrollPane scroll2 = new JScrollPane(recipes);
          scroll2.setSize(200,50);
          recipeBoxBottom.add(scroll2, BorderLayout.CENTER);
          //bottom right half
          JPanel bottomRight = new JPanel(new BorderLayout());
          JButton delete = new JButton("Delete");
          bottomRight.add(delete, BorderLayout.SOUTH);
          recipeBoxBottom.add(bottomRight, BorderLayout.EAST);
          
          JPanel j = new JPanel();
          j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
          int count = 0;
          for (Component jp : re.getMainPanel().getComponents()) 
          {
            if (count != 0) 
            {
              j.add(jp);
            }
            count = 1;
          }
          scroll2.setViewportView(j);
          panel.add(recipeBoxBottom);
          DeleteListener dl = new DeleteListener(panel, recipeBoxBottom, scroll2, frame);
          delete.addActionListener(dl);
          frame.setSize(1000, 825);
          recipeEditorPanels.put(j, re);
          recipeEditors.add(re);
          List<JComponent> comps = re.getComps();
          comps.add(textBox);
          if (sal.getCurrentFileName() == null && ol.getCurrentFileName() == null) {
            new ChangeTracker(comps, saveAs);
          } else {
            new ChangeTracker(comps, saveAs, save);
          }
        }
      } else 
      {
        RecipeEditor re = new RecipeEditor(currentLocale);
        re.getOpenButton().doClick();
        if (re.getOpenListener().getCurrentFileName() != null) 
        {
          JPanel j = new JPanel();
          j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
          int count = 0;
          for (Component jp : re.getMainPanel().getComponents()) 
          {
            if (count != 0) 
            {
              j.add(jp);
            }
            count = 1;
          }
          scroll.setViewportView(j);
          scroll.revalidate();
          scroll.repaint();
          recipeEditorPanels.put(j, re);
          recipeEditors.add(re);
          frame.setSize(1000, 825);
          List<JComponent> comps = re.getComps();
          comps.add(textBox);
          if (sal.getCurrentFileName() == null && ol.getCurrentFileName() == null) {
            new ChangeTracker(comps, saveAs);
          } else {
            new ChangeTracker(comps, saveAs, save);
          }
        }
      }
    }
    
  }
  
  //Creates a new recipeEditor
  private class CreateRecipeListener implements ActionListener 
  {

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      RecipeEditor re = new RecipeEditor(currentLocale);
      re.setVisible(true);
      re.getCloseButton().setEnabled(false);
    }
  }
  
  //Saves the meal editor
  private class SaveListener implements ActionListener 
  {
    
    MealEditor.OpenListener openListener;
    MealEditor.SaveAsListener saveAsListener;
    JTextField textBox;

    public SaveListener(final JTextField textBox, final MealEditor.OpenListener openListener, MealEditor.SaveAsListener saveAsListener) 
    {
      this.textBox = textBox;
      this.openListener = openListener;
      this.saveAsListener = saveAsListener;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      try 
      {
        String fileName;
        if (openListener.getCurrentFileName() == null) {
          fileName = saveAsListener.getCurrentFileName();
        } else {
          fileName = openListener.getCurrentFileName();
        }
        List<Recipe> recList = new ArrayList<>();
        for (RecipeEditor re: recipeEditors) 
        {
          recList.add(re.getSaveListener().getRecipe());
        }
        Meal updatedMeal = new Meal(textBox.getText(), recList);
        
        // If new Recipe
        if (fileName == null)
        {
          fileName = updatedMeal.getFileName();
        }
        
        // Save recipe to its file
        updatedMeal.saveMealToFile(fileName);
        save.setEnabled(false);
        if (recipeEditors.size() > 0) {
          for (RecipeEditor re : recipeEditors) {
            List<JComponent> comps = re.getComps();
            comps.add(textBox);
            new ChangeTracker(comps, save, saveAs);
          }
        } else {
          List<JComponent> comps = new ArrayList<>();
          comps.add(textBox);
          new ChangeTracker(comps, save, saveAs);
        }
      } 
      catch(IOException ex) 
      {
        ex.printStackTrace();
      }
      
    }
    
  }
  //Opens a new meal editor
  private class NewListener implements ActionListener 
  {

    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      MealEditor me = new MealEditor(currentLocale);
      me.setVisible(true);
      me.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      me.getCloseButton().setEnabled(false);
       
    }
    
  }
  //Closes the mealEditor
  private class CloseListener implements ActionListener 
  {

    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      System.exit(0);
    }
    
  }
  
  //Saves the meal as a file
  private class SaveAsListener implements ActionListener 
  {
    
    JTextField textBox;
    String fileName;
    
    public SaveAsListener(final JTextField textBox) 
    {
      this.textBox = textBox;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      UIManager.put("FileChooser.folderNameLabelText", strings.getString("file_chooser_folder_name")); // Add this line
      UIManager.put("FileChooser.saveInLabelText", strings.getString("file_chooser_save_in"));
      UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
      UIManager.put("FileChooser.filesOfTypeLabelText", strings.getString("file_chooser_files_of_type"));
      UIManager.put("FileChooser.upFolderToolTipText", strings.getString("file_chooser_up_folder"));
      UIManager.put("FileChooser.homeFolderToolTipText", strings.getString("file_chooser_home_folder"));
      UIManager.put("FileChooser.newFolderToolTipText", strings.getString("file_chooser_new_folder"));
      UIManager.put("FileChooser.listViewButtonToolTipText", strings.getString("file_chooser_list_view"));
      UIManager.put("FileChooser.detailsViewButtonToolTipText", strings.getString("file_chooser_details_view"));
      UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
      UIManager.put("FileChooser.openButtonText", strings.getString("file_chooser_open_button"));
      UIManager.put("FileChooser.cancelButtonText", strings.getString("file_chooser_cancel_button"));
      UIManager.put("FileChooser.acceptAllFileFilterText", strings.getString("file_chooser_all_files"));
      
      //Allow user to type in filename
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save Meal As");
      int userSelection = fileChooser.showSaveDialog(null);
      
      if (userSelection == JFileChooser.APPROVE_OPTION) 
      {
        fileName = fileChooser.getSelectedFile().getAbsolutePath();
        try 
        {
            // Create an updated recipe and save it to the chosen file name
          List<Recipe> recList = new ArrayList<>();
          for (RecipeEditor re: recipeEditors) 
          {
            recList.add(re.getSaveListener().getRecipe());
          }
          Meal newMeal = new Meal(textBox.getText(), recList);
          newMeal.saveMealToFile(fileName);
          if (recipeEditors.size() > 0) {
            for (RecipeEditor re : recipeEditors) {
              List<JComponent> comps = re.getComps();
              comps.add(textBox);
              new ChangeTracker(comps, save, saveAs);
            }
          } else {
            List<JComponent> comps = new ArrayList<>();
            comps.add(textBox);
            new ChangeTracker(comps, save, saveAs);
          }
        } 
        catch (IOException ex) 
        {
          ex.printStackTrace();
        }
      }
      
    }  
    
    public String getCurrentFileName() 
    {
      return fileName;
    }
    
  }

  //Opens the meal file
  private class OpenListener implements ActionListener 
  {

    JScrollPane scroll;
    JFrame frame;
    JTextField name;
    JPanel recipeBox;
    JList ingList;
    JList stepList;
    JList utensilList;
    JButton button;
    String currentFileName;
    
    
    public OpenListener(final JScrollPane scroll, final JFrame frame, 
        final JPanel recipeBox, final JTextField name, final JButton button) 
    {
      this.recipeBox = recipeBox;
      this.scroll = scroll;
      this.frame = frame;
      this.button = button;
      this.name = name;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(final ActionEvent e) 
    {
      
      UIManager.put("FileChooser.folderNameLabelText", strings.getString("file_chooser_folder_name"));
      UIManager.put("FileChooser.lookInLabelText", strings.getString("file_chooser_look_in"));
      UIManager.put("FileChooser.fileNameLabelText", strings.getString("file_chooser_file_name"));
      UIManager.put("FileChooser.filesOfTypeLabelText", strings.getString("file_chooser_files_of_type"));
      UIManager.put("FileChooser.upFolderToolTipText", strings.getString("file_chooser_up_folder"));
      UIManager.put("FileChooser.homeFolderToolTipText", strings.getString("file_chooser_home_folder"));
      UIManager.put("FileChooser.newFolderToolTipText", strings.getString("file_chooser_new_folder"));
      UIManager.put("FileChooser.listViewButtonToolTipText", strings.getString("file_chooser_list_view"));
      UIManager.put("FileChooser.detailsViewButtonToolTipText", strings.getString("file_chooser_details_view"));
      UIManager.put("FileChooser.saveButtonText", strings.getString("file_chooser_save_button"));
      UIManager.put("FileChooser.openButtonText", strings.getString("file_chooser_open_button"));
      UIManager.put("FileChooser.cancelButtonText", strings.getString("file_chooser_cancel_button"));
      UIManager.put("FileChooser.acceptAllFileFilterText", strings.getString("file_chooser_all_files"));

      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "MEL files", "mel");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(null);
      if(returnVal == JFileChooser.APPROVE_OPTION) 
      {
        System.out.println("You chose to open this file: " 
            + chooser.getSelectedFile().getName());
        currentFileName = chooser.getSelectedFile().getAbsolutePath();
        System.out.println(currentFileName);
        try 
        {
          Meal loaded = Meal.loadMealFromFile(chooser.getSelectedFile().getAbsolutePath());
          //serves.setText(String.valueOf(loaded.getServes()));
          name.setText(loaded.getName());
          int count = 0;
          if (loaded.getRecipes().size() == 0) {
            List<JComponent> comps = new ArrayList<>();
            comps.add(textBox);
            new ChangeTracker(comps, save, saveAs);
          }
          for (Recipe r : loaded.getRecipes()) 
          {
            RecipeEditor re = new RecipeEditor(currentLocale);
            if (count == 0) 
            {
              re.getOpenListener().openMeal(r);
              JPanel j = new JPanel();
              j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
              int count2 = 0;
              for (Component jp : re.getMainPanel().getComponents()) 
              {
                if (count2 != 0) 
                {
                  j.add(jp);
                }
                count2 = 1;
              }
              count = 1;
              scroll.setViewportView(j);
              scroll.revalidate();
              scroll.repaint();
              recipeEditorPanels.put(j, re);
              recipeEditors.add(re);
              frame.setSize(1000, 825);
            } 
            else 
            {
              re.getOpenListener().openMeal(r);
              JPanel recipeBoxBottom = new JPanel(new BorderLayout());
              JList recipes = new JList();
              JScrollPane scroll2 = new JScrollPane(recipes);
              scroll2.setSize(200,50);
              recipeBoxBottom.add(scroll2, BorderLayout.CENTER);
              //bottom right half
              JPanel bottomRight = new JPanel(new BorderLayout());
              JButton delete = new JButton("Delete");
              bottomRight.add(delete, BorderLayout.SOUTH);
              recipeBoxBottom.add(bottomRight, BorderLayout.EAST);
              
              JPanel j = new JPanel();
              j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
              int count2 = 0;
              for (Component jp : re.getMainPanel().getComponents()) 
              {
                if (count2 != 0) 
                {
                  j.add(jp);
                }
                count2 = 1;
              }
              scroll2.setViewportView(j);
              recipeBox.add(recipeBoxBottom);
              DeleteListener dl = new DeleteListener(recipeBox, recipeBoxBottom, scroll2, frame);
              delete.addActionListener(dl);
              frame.setSize(1000, 825);
              recipeEditorPanels.put(j, re);
              recipeEditors.add(re);
            }
            button.setEnabled(false);
            List<JComponent> comps = re.getComps();
            comps.add(textBox);
            new ChangeTracker(comps, save, saveAs);
          }
          open.setEnabled(false);
        }    
        catch (ClassNotFoundException e1) 
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } 
        catch (IOException e1) 
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }
      
    
    public String getCurrentFileName() 
    {
      return currentFileName;
    }

  
  }
  
  //Deletes the recipe
  private class DeleteListener implements ActionListener 
  {
    JPanel recipeBox;
    JPanel recipeBoxBottom;
    JScrollPane scroll;
    JFrame frame;
    
    public DeleteListener(final JPanel recipeBox, final JPanel recipeBoxBottom, 
        final JScrollPane scroll, final JFrame frame) 
    {
      this.recipeBox = recipeBox;
      this.recipeBoxBottom = recipeBoxBottom;
      this.scroll = scroll;
      this.frame = frame;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
      if (scroll.getViewport().getView() instanceof JPanel) 
      {
        JPanel panel = (JPanel) scroll.getViewport().getView();
        if (recipeEditorPanels.size() > 1) 
        {
          recipeBox.remove(recipeBoxBottom);
          recipeBox.revalidate();
          recipeBox.repaint();
          recipeEditors.remove(recipeEditorPanels.get(panel));
          recipeEditorPanels.remove(panel);
          
        } 
        else 
        {
          recipeEditors.remove(recipeEditorPanels.get(panel));
          recipeEditorPanels.remove(panel);
          scroll.getViewport().setView(null);
          scroll.revalidate();
          scroll.repaint();
          scroll.setViewportView(new JList());
          frame.setSize(1000,400);
        }
      }
    }
    
  }
  public class TextFieldChanger implements DocumentListener
  {

    @Override
    public void insertUpdate(DocumentEvent e)
    {
      saveAs.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
      saveAs.setEnabled(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
      saveAs.setEnabled(true);
    }
  }
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> 
    {
      // You can change this to the desired default locale
      Locale desiredLocale = Locale.ITALIAN;
      
      // Create a new MealEditor instance with the desired locale
      MealEditor mealEditor = new MealEditor(desiredLocale);
      mealEditor.setVisible(true);
    });
  }
}