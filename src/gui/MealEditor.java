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
import java.util.List;

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
	private List<RecipeEditor> recipeEditors = new ArrayList<>();
	
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
        mainPanel = new JPanel(new BorderLayout());
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
	    buttonCreation buttonCreation = new buttonCreation();
		//initialize buttons
	    buttonCreation.addImageButton(imagePanel, "/img/new.png", "New");
        buttonCreation.addImageButton(imagePanel, "/img/open.png", "Open");
        buttonCreation.addImageButton(imagePanel, "/img/save.png", "Save");
        buttonCreation.addImageButton(imagePanel, "/img/saveAs.png", "Save As");
        buttonCreation.addImageButton(imagePanel, "/img/close.png", "Close");
        
        // Add image panel to the top (NORTH) of the main panel
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        // text box panel
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField textBox = new JTextField(20);
        JLabel textLabel = new JLabel("Name:");
        textPanel.setBorder(BorderFactory.createEmptyBorder(0,6,0,0));
        textBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textPanel.add(textLabel);
        textPanel.add(textBox);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        
        // add recipe window
        JPanel recipeBox = new JPanel(new BorderLayout());
        recipeBox.setLayout(new BoxLayout(recipeBox, BoxLayout.Y_AXIS));
        //recipeBox.setPreferredSize(new Dimension(300,265));
        recipeBox.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK, 2)));
        
        //top half of recipe box
        JPanel recipeBoxTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addRecipe = new JButton("Add Recipe");
        //addRecipe.addActionListener(new AddRecipeListener(scroll));
        recipeBoxTop.add(addRecipe, BorderLayout.EAST);
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
        mainPanel.add(d,BorderLayout.CENTER);
        
        //add main panel
        JButton newB = (JButton) imagePanel.getComponent(0);
        newB.addActionListener(new NewListener());
        imagePanel.getComponent(1).setEnabled(false);
        JButton save = (JButton) imagePanel.getComponent(2);
        save.addActionListener(new SaveListener(textBox));
        imagePanel.getComponent(2).setEnabled(false);
        JButton saveAs = (JButton) imagePanel.getComponent(3);
        saveAs.addActionListener(new SaveAsListener(textBox));
        imagePanel.getComponent(3).setEnabled(false);
        JButton close = (JButton) imagePanel.getComponent(4);
        close.addActionListener(new CloseListener());
        add(mainPanel);
	}
	
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
      if (recipeEditors.size() > 0) {
        RecipeEditor re = new RecipeEditor();
        re.getOpenButton().doClick();
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
        for (Component jp : re.getMainPanel().getComponents()) {
          j.add(jp);
        }
        scroll2.setViewportView(j);
        recipeBoxBottom.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(recipeBoxBottom);
        frame.setSize(1000, 800);
        recipeEditors.add(re);
      } else {
        RecipeEditor re = new RecipeEditor();
        recipeEditors.add(re);
        re.getOpenButton().doClick();
        JPanel j = new JPanel();
        j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
        for (Component jp : re.getMainPanel().getComponents()) {
          j.add(jp);
        }
        scroll.setViewportView(j);
        recipeEditors.add(re);
      }
    }
	  
	}
	
	private class SaveListener implements ActionListener {
	  
	  OpenListener openListener;
	  JTextField textBox;

	  public SaveListener(JTextField textBox) {
	    this.textBox = textBox;
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
	private class NewListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      MealEditor me = new MealEditor();
       me.setVisible(true);
    }
    
  }
	private class CloseListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
    
  }
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

	private class OpenListener implements ActionListener {

	  JTextField name;

	  JList ingList;
	  JList stepList;
	  JList utensilList;
	  JComboBox stepUtensilCombo;
	  String currentFileName;
	  List<RecipeIngredient> fullIngredientList;
	  List<Step> fullStepList;
	  List<Utensil> fullUtensilList;
	  DefaultListModel<String> dlm;
	  DefaultListModel<String> dlm2;
	  DefaultListModel<String> dlm3;
	  
	  public OpenListener() {
	    
	  }
	  
	  @SuppressWarnings("unchecked")
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    JFileChooser chooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                "RCP files", "rcp");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            System.out.println("You chose to open this file: " +
	                    chooser.getSelectedFile().getName());
//	            File file = new File(chooser.getSelectedFile(), chooser.getSelectedFile().getName());
	            currentFileName = chooser.getSelectedFile().getAbsolutePath();
	            try {
	        Meal loaded = Meal.loadMealFromFile(chooser.getSelectedFile().getAbsolutePath());
	        name.setText(loaded.getName());
	        //serves.setText(String.valueOf(loaded.getServes()));
	        int count = 0;
	        for (Recipe r : loaded.getRecipes()) {
	          if (count == 0) {
	            
	          }
	        }
	        //sort fullingredientslist somehow
	        
	        
	      
	        
	        ingList.setModel(dlm);
	        stepList.setModel(dlm3);
	        utensilList.setModel(dlm2);
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
	
	private class DeleteListener implements ActionListener {
	  JPanel recipeBox;
	  JScrollPane[] scroll;
	  
	  public DeleteListener(JPanel recipeBox, JScrollPane... scroll) {
	    this.recipeBox = recipeBox;
	    this.scroll = scroll;
	  }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (recipeEditors.size() > 0) {
        for (JScrollPane scroll1: scroll) {
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
