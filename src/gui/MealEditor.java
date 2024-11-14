package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Information.Meal;
import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;
import gui.EditorListeners.OpenListener;

/**
 * Meal editor is saved as a Jframe.
 * 
 * @author natek
 */
public class MealEditor extends JFrame
{

  /**
   * I really wish i knew what this did
   */
  private static final long serialVersionUID = -4907901058401288335L;
  static final String OPEN = "Open";
  private JPanel mainPanel;
  private JButton close;
  private Map<JPanel, RecipeEditor> recipeEditorPanels = new HashMap<>();
  private List<RecipeEditor> recipeEditors = new ArrayList<>();
  private static Locale currentLocale = Locale.getDefault();
  
  /**
   * default constructor for the window
   */
  public MealEditor() 
  {
    setTitle("KiLowBites Meal Editor");
    setSize(1000,400);
    setResizable(false);
    setLocationRelativeTo(null);
      
      // Create main content panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
       // imagePanel.setPreferredSize(new Dimension(100, 1));
        
        buttonCreation buttonCreation = new buttonCreation();
        //initialize buttons
        buttonCreation.addImageButton(imagePanel, "/img/new.png", "New");
        buttonCreation.addImageButton(imagePanel, "/img/open.png", "Open");
        buttonCreation.addImageButton(imagePanel, "/img/save.png", "Save");
        buttonCreation.addImageButton(imagePanel, "/img/saveAs.png", "Save As");
        buttonCreation.addImageButton(imagePanel, "/img/close.png", "Close");
        
        // Add image panel to the top (NORTH) of the main panel
        mainPanel.add(imagePanel);

        // text box panel
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField textBox = new JTextField(20);
        JLabel textLabel = new JLabel("Name:");
        //textPanel.setPreferredSize(new Dimension(100,1));
        //textPanel.setMaximumSize(new Dimension(0,1000));
        //textPanel.setBorder(BorderFactory.createEmptyBorder(0,6,0,0));
        textBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textPanel.add(textLabel);
        textPanel.add(textBox);
        mainPanel.add(textPanel);
        
        // add recipe window
        JPanel recipeBox = new JPanel(new BorderLayout());
        recipeBox.setLayout(new BoxLayout(recipeBox, BoxLayout.Y_AXIS));
        //recipeBox.setPreferredSize(new Dimension(600,265));
        recipeBox.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK, 2)));
        
        //top half of recipe box
        JPanel recipeBoxTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addRecipe = new JButton("Add Recipe");
        JButton createRecipe = new JButton("Create Recipe");
        createRecipe.addActionListener(new CreateRecipeListener());
        //addRecipe.addActionListener(new AddRecipeListener(scroll));
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
        JButton delete = new JButton("Delete");
        bottomRight.add(delete, BorderLayout.SOUTH);
        recipeBoxBottom.add(bottomRight, BorderLayout.EAST);
        //JScrollPane scroll2 = new JScrollPane(recipeBoxBottom);
        recipeBox.add(recipeBoxBottom);

        addRecipe.addActionListener(new AddRecipeListener(this, scroll, recipeBox));
        //add recipe box
        JScrollPane d = new JScrollPane(recipeBox);
        mainPanel.add(d);
        
        //Initialize buttons listeners
        JButton newB = (JButton) imagePanel.getComponent(0);
        newB.addActionListener(new NewListener());
        JButton open = (JButton) imagePanel.getComponent(1);
        OpenListener ol = new OpenListener(scroll, this, recipeBox, textBox, open);
        open.addActionListener(ol);
        JButton save = (JButton) imagePanel.getComponent(2);
        save.addActionListener(new SaveListener(textBox, ol));
        JButton saveAs = (JButton) imagePanel.getComponent(3);
        saveAs.addActionListener(new SaveAsListener(textBox));
        close = (JButton) imagePanel.getComponent(4);
        close.addActionListener(new CloseListener());
        
        //initialize delete button listener
        DeleteListener dl = new DeleteListener(recipeBox, recipeBoxBottom, scroll, this);
        delete.addActionListener(dl);
        
        //add everything
        add(mainPanel);
  }
  
  private JButton getCloseButton() {
    return close;
  }
  
  //Adds the recipe editor to the jscrollpane or to a new one
  private class AddRecipeListener implements ActionListener {

    JFrame frame;
    JScrollPane scroll;
    JPanel panel;
    
    public AddRecipeListener(JFrame frame, JScrollPane scroll, JPanel panel) {
      this.frame = frame;
      this.scroll = scroll;
      this.panel = panel;
    }
    public void actionPerformed(ActionEvent e)
    {
      if (recipeEditorPanels.size() > 0) {
        RecipeEditor re = new RecipeEditor(currentLocale);
        //String temp = ol.getCurrentFileName();
        re.getOpenButton().doClick();
        if (re.getOpenListener().getCurrentFileName() != null) {
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
          for (Component jp : re.getMainPanel().getComponents()) {
            if (count != 0) {
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
        }
      } else {
        RecipeEditor re = new RecipeEditor(currentLocale);
        re.getOpenButton().doClick();
        if (re.getOpenListener().getCurrentFileName() != null) {
          JPanel j = new JPanel();
          j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
          int count = 0;
          for (Component jp : re.getMainPanel().getComponents()) {
            if (count != 0) {
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
        }
      }
    }
    
  }
  
  //Creates a new recipeEditor
  private class CreateRecipeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e)
    {
      RecipeEditor re = new RecipeEditor(currentLocale);
      re.setVisible(true);
      re.getCloseButton().setEnabled(false);
    }
  }
  
  //Saves the meal editor
  private class SaveListener implements ActionListener {
    
    MealEditor.OpenListener openListener;
    JTextField textBox;

    public SaveListener(JTextField textBox, MealEditor.OpenListener openListener) {
      this.textBox = textBox;
      this.openListener = openListener;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      try {
        String fileName = openListener.getCurrentFileName();
        List<Recipe> recList = new ArrayList<>();
        for (RecipeEditor re: recipeEditors) {
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
        
      } catch(IOException ex) {
        ex.printStackTrace();
      }
      
    }
    
  }
  //Opens a new meal editor
  private class NewListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      MealEditor me = new MealEditor();
       me.setVisible(true);
       me.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       me.getCloseButton().setEnabled(false);
       
    }
    
  }
  //Closes the mealEditor
  private class CloseListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
    
  }
  
  //Saves the meal as a file
  private class SaveAsListener implements ActionListener {
    
    JTextField textBox;
    
    public SaveAsListener(JTextField textBox) {
      this.textBox = textBox;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      //Allow user to type in filename
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save Recipe As");
      int userSelection = fileChooser.showSaveDialog(null);
      
      if (userSelection == JFileChooser.APPROVE_OPTION) {
          String fileName = fileChooser.getSelectedFile().getAbsolutePath();
          try {
              // Create an updated recipe and save it to the chosen file name
              List<Recipe> recList = new ArrayList<>();
              for (RecipeEditor re: recipeEditors) {
                recList.add(re.getSaveListener().getRecipe());
              }
              Meal newMeal = new Meal(textBox.getText(), recList);
              newMeal.saveMealToFile(fileName);
          } catch (IOException ex) {
              ex.printStackTrace();
          }
      }
      
    }  
    
  }

  //Opens the meal file
  private class OpenListener implements ActionListener {

    JScrollPane scroll;
    JFrame frame;
    JTextField name;
    JPanel recipeBox;
    JList ingList;
    JList stepList;
    JList utensilList;
    JButton button;
    String currentFileName;
    
    
    public OpenListener(JScrollPane scroll, JFrame frame, JPanel recipeBox, JTextField name, JButton button) {
      this.recipeBox = recipeBox;
      this.scroll = scroll;
      this.frame = frame;
      this.button = button;
      this.name = name;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser chooser = new JFileChooser();
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
                  "MEL files", "mel");
          chooser.setFileFilter(filter);
          int returnVal = chooser.showOpenDialog(null);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
              System.out.println("You chose to open this file: " +
                      chooser.getSelectedFile().getName());
//              File file = new File(chooser.getSelectedFile(), chooser.getSelectedFile().getName());
              currentFileName = chooser.getSelectedFile().getAbsolutePath();
              System.out.println(currentFileName);
              try {
                Meal loaded = Meal.loadMealFromFile(chooser.getSelectedFile().getAbsolutePath());
                //serves.setText(String.valueOf(loaded.getServes()));
                name.setText(loaded.getName());
                int count = 0;
                for (Recipe r : loaded.getRecipes()) {
                  RecipeEditor re = new RecipeEditor(currentLocale);
                  if (count == 0) {
                    re.getOpenListener().openMeal(r);
                    JPanel j = new JPanel();
                    j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
                    int count2 = 0;
                    for (Component jp : re.getMainPanel().getComponents()) {
                      if (count2 != 0) {
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
                  else {
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
                    for (Component jp : re.getMainPanel().getComponents()) {
                      if (count2 != 0) {
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
                }
                } catch (ClassNotFoundException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
          }
    }
      
    
    public String getCurrentFileName() {
      return currentFileName;
    }

  
  }
  
  //Deletes the recipe
  private class DeleteListener implements ActionListener {
    JPanel recipeBox;
    JPanel recipeBoxBottom;
    JScrollPane scroll;
    JFrame frame;
    
    public DeleteListener(JPanel recipeBox, JPanel recipeBoxBottom, JScrollPane scroll, JFrame frame) {
      this.recipeBox = recipeBox;
      this.recipeBoxBottom = recipeBoxBottom;
      this.scroll = scroll;
      this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (scroll.getViewport().getView() instanceof JPanel) {
        JPanel panel = (JPanel) scroll.getViewport().getView();
        if (recipeEditorPanels.size() > 1) {
          recipeBox.remove(recipeBoxBottom);
          recipeBox.revalidate();
          recipeBox.repaint();
          recipeEditors.remove(recipeEditorPanels.get(panel));
          recipeEditorPanels.remove(panel);
          
        } else {
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
  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MealEditor().setVisible(true);
        });
  }
}
