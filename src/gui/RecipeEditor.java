package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

public class RecipeEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922930697879333939L;
	private String delete = "Delete";
	private String add = "Add";
	private String name = "Name: ";
	public DefaultListModel<String> dlm = new DefaultListModel<>(); // ingredients held in this
	public List<RecipeIngredient> fullIngredientList = new ArrayList<>();
	public DefaultListModel<String> dlm3 = new DefaultListModel<>();
	public List<Step> fullStepList = new ArrayList<>();
	public DefaultListModel<String> dlm2 = new DefaultListModel<>();
	public List<Utensil> fullUtensilList = new ArrayList<>();
	
	public RecipeEditor() {
		setTitle("KiLowBites Recipe Editor");
		setSize(600,775);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create main content panel
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
		
        
        //namepanel to add name and serves ---------------------------------
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel(name);
        JTextField nameText = new JTextField(25);
        JLabel serves = new JLabel("Serves: ");
        JTextField servesText = new JTextField(10);
        namePanel.add(nameLabel);
        namePanel.add(nameText);
        namePanel.add(serves);
        namePanel.add(servesText);
        
        
        //utensils editor ---------------------------------------------
        JPanel utensilPanel = new JPanel(new BorderLayout());
        utensilPanel.setPreferredSize(new Dimension(575,200));
        TitledBorder utensilBorder = new TitledBorder("Utensils");
        utensilPanel.setBorder(utensilBorder);

        //Top half of utensil editor -----------
        JPanel utensilInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel utensilName = new JLabel(name);
        JTextField utensilNameText = new JTextField(20);
        JLabel details = new JLabel("Details: ");
        JTextField detailsText = new JTextField(15);
        JButton addUtensil = new JButton(add);
        utensilInputs.add(utensilName);
        utensilInputs.add(utensilNameText);
        utensilInputs.add(details);
        utensilInputs.add(detailsText);
        utensilInputs.add(addUtensil);
        
        //Bottom half of utensil editor ----------
        JPanel utensilBottom = new JPanel(new BorderLayout());
        JList utensilsList = new JList();
        JScrollPane utensilScroll = new JScrollPane(utensilsList);
        utensilBottom.add(utensilScroll, BorderLayout.WEST);
        //button
        JPanel utensilBR = new JPanel(new BorderLayout());
        JButton utensilDelete = new JButton("Delete");
        utensilBR.add(utensilDelete, BorderLayout.SOUTH);
        utensilBottom.add(utensilBR, BorderLayout.EAST);
        //add everything to panel
        utensilPanel.add(utensilBottom, BorderLayout.SOUTH);
        utensilPanel.add(utensilInputs, BorderLayout.NORTH);
        
        
        
        
        //ingredients editor ---------------------------------------------
        JPanel ingPanel = new JPanel(new BorderLayout());
        ingPanel.setPreferredSize(new Dimension(575,200));
        TitledBorder ingBorder = new TitledBorder("Ingredients");
        ingPanel.setBorder(ingBorder);

        //Top half of ingredients editor -----------
        JPanel ingInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ingName = new JLabel(name);
        JTextField ingNameInput = new JTextField(10);
        JLabel ingDetails = new JLabel("Details: ");
        JTextField ingDetailsInput = new JTextField(8);
        JLabel ingAmount = new JLabel("Amount: ");
        JTextField ingAmountInput = new JTextField(3);
        JLabel ingUnits = new JLabel("Units: ");
        JComboBox<String> ingUnitCombo = new JComboBox<String>();
        JButton ingAdd = new JButton(add);
        AddIngListener ingAddListen = new AddIngListener(ingNameInput,
        		ingDetailsInput, ingAmountInput);
        ingAdd.addActionListener(ingAddListen);
        ingInputs.add(ingName);
        ingInputs.add(ingNameInput);
        ingInputs.add(ingDetails);
        ingInputs.add(ingDetailsInput);
        ingInputs.add(ingAmount);
        ingInputs.add(ingAmountInput);
        ingInputs.add(ingUnits);
        ingInputs.add(ingUnitCombo);
        ingInputs.add(ingAdd);

        //bottom half of ingredients-----------
        JPanel ingBottom = new JPanel(new BorderLayout());
        JList<String> ingList = new JList(dlm);
        JScrollPane sp = new JScrollPane(ingList);
        ingBottom.add(sp);
        //button
        JPanel ingBR = new JPanel(new BorderLayout());
        JButton ingDelete = new JButton("Delete");
        deleteIngListener ingDeleteListener= new deleteIngListener(ingList);
        ingDelete.addActionListener(ingDeleteListener);
        ingBR.add(ingDelete, BorderLayout.SOUTH);
        ingBottom.add(ingBR, BorderLayout.EAST);
        
        ingPanel.add(ingBottom, BorderLayout.SOUTH);
        ingPanel.add(ingInputs, BorderLayout.NORTH);
        
        
        
        //steps editor ---------------------------------------------------
        JPanel stepPanel = new JPanel(new BorderLayout());
        stepPanel.setPreferredSize(new Dimension(575,200));
        TitledBorder stepBorder = new TitledBorder("Steps");
        stepPanel.setBorder(stepBorder);
        
        //top half of steps
        JPanel stepInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel stepAction = new JLabel("Action: ");
        JComboBox<String> stepActionCombo = new JComboBox<>();
        JLabel stepOn = new JLabel("On: ");
        JComboBox<String> stepOnCombo = new JComboBox<>();
        JLabel stepUtensil = new JLabel("Utensil: ");
        JComboBox<String> stepUtensilCombo = new JComboBox<>();
        JLabel stepDetails = new JLabel("Details: ");
        JTextField stepDetailsText = new JTextField(5);
        JButton stepAdd = new JButton(add);
        stepInputs.add(stepAction);
        stepInputs.add(stepActionCombo);
        stepInputs.add(stepOn);
        stepInputs.add(stepOnCombo);
        stepInputs.add(stepUtensil);
        stepInputs.add(stepUtensilCombo);
        stepInputs.add(stepDetails);
        stepInputs.add(stepDetailsText);
        stepInputs.add(stepAdd);

        //bottom half of ingredients-----------
        JPanel stepBottom = new JPanel(new BorderLayout());
        JList stepList = new JList();
        JScrollPane stepScroll = new JScrollPane(stepList);
        stepBottom.add(stepScroll, BorderLayout.WEST);
        //button
        JPanel stepBR = new JPanel(new BorderLayout());
        JButton stepDelete = new JButton("Delete");
        stepBR.add(stepDelete, BorderLayout.SOUTH);
        stepBottom.add(stepBR, BorderLayout.EAST);

        stepPanel.add(stepInputs, BorderLayout.NORTH);
        stepPanel.add(stepBottom, BorderLayout.SOUTH);
        
        
        
        
      //initialize new button ----------------------------------------------
        ImageIcon newIcon = createImageIcon("/img/new.png");
	    Image newImg = newIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    JButton newButton = new JButton(new ImageIcon(newImg));
	    newButton.setPreferredSize(new Dimension(50, 50));
	    newButton.setToolTipText("New");
	    NewListener newListener = new NewListener();
	    newButton.addActionListener(newListener);
	    imagePanel.add(newButton);
	    
	    //initialize open button ----------------------------------------------
        ImageIcon openIcon = createImageIcon("/img/open.png");
	    Image openImg = openIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    JButton openButton = new JButton(new ImageIcon(openImg));
	    openButton.setPreferredSize(new Dimension(50, 50));
	    openButton.setToolTipText("Open");
	    OpenListener openListener = new OpenListener(nameText, servesText, ingNameInput,
	    		ingDetailsInput, ingAmountInput, ingUnitCombo, ingList);
	    openButton.addActionListener(openListener);
	    imagePanel.add(openButton);
	    
	    //initialize save button ----------------------------------------------
        ImageIcon saveIcon = createImageIcon("/img/save.png");
	    Image saveImg = saveIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    JButton saveButton = new JButton(new ImageIcon(saveImg));
	    saveButton.setPreferredSize(new Dimension(50, 50));
	    saveButton.setToolTipText("Save");
	    SaveListener saveListener = new SaveListener();
	    saveButton.addActionListener(saveListener);
	    imagePanel.add(saveButton);
	    
	    //initialize save as button ----------------------------------------------
        ImageIcon saveAsIcon = createImageIcon("/img/saveas.png");
	    Image saveAsImg = saveAsIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    JButton saveAsButton = new JButton(new ImageIcon(saveAsImg));
	    saveAsButton.setPreferredSize(new Dimension(50, 50));
	    saveAsButton.setToolTipText("Save As");
	    SaveAsListener saveAsListener = new SaveAsListener();
	    saveAsButton.addActionListener(saveAsListener);
	    imagePanel.add(saveAsButton);
	    
	    //initialize close button ----------------------------------------------
        ImageIcon closeIcon = createImageIcon("/img/close.png");
	    Image closeImg = closeIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    JButton closeButton = new JButton(new ImageIcon(closeImg));
	    closeButton.setPreferredSize(new Dimension(50, 50));
	    closeButton.setToolTipText("Close");
	    CloseListener closeListener = new CloseListener();
	    closeButton.addActionListener(closeListener);
	    imagePanel.add(closeButton);
	    
	    
        mainPanel.add(imagePanel, BorderLayout.NORTH);
        mainPanel.add(namePanel);
        mainPanel.add(ingPanel);
        mainPanel.add(stepPanel);
        mainPanel.add(utensilPanel);
        
        add(mainPanel);
	}
	
	public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	private class NewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
	
	private class AddIngListener implements ActionListener {

		JTextField ingName;
		JTextField ingDetail;
		JTextField ingAmount;
		JComboBox ingUnit;
		
		public AddIngListener(JTextField ingName, JTextField ingDetail, JTextField ingAmount) {
			this.ingName = ingName;
			this.ingDetail = ingDetail;
			this.ingAmount = ingAmount;
			this.ingUnit = ingUnit;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//add info to thing
			dlm.addElement(ingName.getText());
			fullIngredientList.add(new RecipeIngredient(ingName.getText(), ingDetail.getText(),
					Double.valueOf(ingAmount.getText()), "test"));
		}
		
	}
	
	private class deleteIngListener implements ActionListener {

		JList ingredientList;
		
		public deleteIngListener(JList ingredientList) {
			this.ingredientList = ingredientList;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dlm.removeElement(ingredientList.getSelectedValue());
			fullIngredientList.remove(ingredientList.getSelectedValue());
		}
		
	}
	
	private class OpenListener implements ActionListener {

		JTextField name;
		JTextField serves;
		JTextField ingNameInput;
		JTextField ingDetailsInput;
		JTextField ingAmountInput;
		JComboBox ingUnitCombo;
		JList ingList;
		private String currentFileName;
		
		public OpenListener(JTextField name, JTextField serves, JTextField ingNameInput, 
				JTextField ingDetailsInput, JTextField ingAmountInput, JComboBox ingUnitCombo, JList ingList) {
			this.name = name;
			this.serves = serves;
			this.ingAmountInput = ingAmountInput;
			this.ingDetailsInput = ingDetailsInput;
			this.ingUnitCombo = ingUnitCombo;
			this.ingNameInput = ingNameInput;
			this.ingList = ingList;
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
					Recipe loaded = Recipe.loadRecipeFromFile(chooser.getSelectedFile().getAbsolutePath());
					name.setText(loaded.getName());
					serves.setText(String.valueOf(loaded.getServes()));
					for (RecipeIngredient ri : loaded.getIngredients()) {
						dlm.addElement(ri.getName());
						fullIngredientList.add(ri);
					}
					for (Utensil ut : loaded.getUtenils()) {
						dlm2.addElement(ut.getName());
						fullUtensilList.add(ut);
					}
					for (Step st : loaded.getSteps()) {
						dlm2.addElement(st.getAction());
						fullStepList.add(st);
					}
					ingList.setModel(dlm);
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
	
//	private class SaveListener implements ActionListener {
//	  
//	  OpenListener openListener;
//
//    public SaveListener(OpenListener openListener) {
//        this.openListener = openListener;
//    }
//    
//		@Override
//		public void actionPerformed(ActionEvent e) 
//		{
//		  try {
//		    String fileName = openListener.getCurrentFileName();
//		    // Need to add name.gettext, serves, etc. ingredients is good tho. 
//	      Recipe updatedRecipe = new Recipe(name.getText(), Integer.parseInt(serves.getText()), fullIngredientList, stepsList);
//	      
//		    // If new Recipe
//	      if (fileName == null)
//	      {
//	        fileName = updatedRecipe.getFileName();
//	      }
//	      
//	      updatedRecipe.saveRecipeToFile(fileName);
//	      
//		  } catch(IOException ex) {
//		    ex.printStackTrace();
//		  }
//			
//		}
//		
//	}
	
//	private class SaveAsListener implements ActionListener {
//	  
//    
//		@Override
//		public void actionPerformed(ActionEvent e) {
//		  //Allow user to type in filename
//		  JFileChooser fileChooser = new JFileChooser();
//      fileChooser.setDialogTitle("Save Recipe As");
//      int userSelection = fileChooser.showSaveDialog(null);
//      
//      if (userSelection == JFileChooser.APPROVE_OPTION) {
//          String fileName = fileChooser.getSelectedFile().getAbsolutePath();
//          
//          try {
//              // Create an updated recipe and save it to the chosen file name
//         // Need to add name.gettext, serves, etc. ingredients is good tho. 
//              Recipe newRecipe = new Recipe(name.getText(), Integer.parseInt(serves.getText()), fullIngredientList, stepsList);
//              newRecipe.saveRecipeToFile(fileName);
//          } catch (IOException ex) {
//              ex.printStackTrace();
//          }
//      }
//			
//		}
//		
//	}
	
	private class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecipeEditor().setVisible(true);
        });
    }
}
