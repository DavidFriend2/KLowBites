package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
	
	/**
	 * default constructor for the window
	 */
	public MealEditor() 
	{
		setTitle("KiLowBites Meal Editor");
		setSize(600,400);
		setResizable(false);
		setLocationRelativeTo(null);
	    
	    // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
	    buttonCreation buttonCreation = new buttonCreation();
		//initialize buttons
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
        recipeBox.setPreferredSize(new Dimension(300,265));
        recipeBox.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.BLACK, 2)));
        
        //top half of recipe box
        JPanel recipeBoxTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addRecipe = new JButton("Add Recipe");
        recipeBoxTop.add(addRecipe, BorderLayout.EAST);
        recipeBox.add(recipeBoxTop, BorderLayout.NORTH);
        
        
        //bottom half of recipe box
        JPanel recipeBoxBottom = new JPanel(new BorderLayout());
        JTextArea recipes = new JTextArea(12, 48);
        JScrollPane scroll = new JScrollPane(recipes);
        recipeBoxBottom.add(scroll, BorderLayout.WEST);
        //bottom right half
        JPanel bottomRight = new JPanel(new BorderLayout());
        JButton delete = new JButton("Delete");
        bottomRight.add(delete, BorderLayout.SOUTH);
        recipeBoxBottom.add(bottomRight, BorderLayout.EAST);
        recipeBox.add(recipeBoxBottom);

        //add recipe box
        mainPanel.add(recipeBox,BorderLayout.SOUTH);
        
        //add main panel
        add(mainPanel);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MealEditor().setVisible(true);
        });
    }
}
