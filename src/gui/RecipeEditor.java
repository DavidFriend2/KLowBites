package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class RecipeEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922930697879333939L;
	private String delete = "Delete";
	private String add = "Add";
	private String name = "Name: ";
	
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
        
	    buttonCreation buttonCreation = new buttonCreation();
		//initialize buttons ----------------------------------------------
	    buttonCreation.addImageButton(imagePanel, "/img/new.png", "New");
        buttonCreation.addImageButton(imagePanel, "/img/open.png", "Open");
        buttonCreation.addImageButton(imagePanel, "/img/save.png", "Save");
        buttonCreation.addImageButton(imagePanel, "/img/saveAs.png", "Save As");
        buttonCreation.addImageButton(imagePanel, "/img/close.png", "Close");
        imagePanel.getComponent(0).setEnabled(false);
        imagePanel.getComponent(1).setEnabled(false);
        imagePanel.getComponent(2).setEnabled(false);
        imagePanel.getComponent(3).setEnabled(false);
        imagePanel.getComponent(4).setEnabled(false);
        mainPanel.add(imagePanel, BorderLayout.NORTH);
        
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
        mainPanel.add(namePanel);
        
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
        JTextArea utensilsList = new JTextArea(8, 48);
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
        
        mainPanel.add(utensilPanel);
        
        
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
        JComboBox<String> ingUnitCombo = new JComboBox<>();
        JButton ingAdd = new JButton(add);
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
        JTextArea ingList = new JTextArea(8, 48);
        JScrollPane ingScroll = new JScrollPane(ingList);
        ingBottom.add(ingScroll, BorderLayout.WEST);
        //button
        JPanel ingBR = new JPanel(new BorderLayout());
        JButton ingDelete = new JButton("Delete");
        ingBR.add(ingDelete, BorderLayout.SOUTH);
        ingBottom.add(ingBR, BorderLayout.EAST);
        
        ingPanel.add(ingBottom, BorderLayout.SOUTH);
        ingPanel.add(ingInputs, BorderLayout.NORTH);
        
        mainPanel.add(ingPanel);
        
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
        JTextArea stepList = new JTextArea(8, 48);
        JScrollPane stepScroll = new JScrollPane(stepList);
        stepBottom.add(stepScroll, BorderLayout.WEST);
        //button
        JPanel stepBR = new JPanel(new BorderLayout());
        JButton stepDelete = new JButton("Delete");
        stepBR.add(stepDelete, BorderLayout.SOUTH);
        stepBottom.add(stepBR, BorderLayout.EAST);

        stepPanel.add(stepInputs, BorderLayout.NORTH);
        stepPanel.add(stepBottom, BorderLayout.SOUTH);
        
        
        mainPanel.add(stepPanel);
        
        
        
        
        add(mainPanel);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecipeEditor().setVisible(true);
        });
    }
}
