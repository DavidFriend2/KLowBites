package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
	    buttonCreation buttonCreation = new buttonCreation();
		//initialize buttons
	    buttonCreation.addImageButton(imagePanel, "/img/calculate.png", "Calculate");
        buttonCreation.addImageButton(imagePanel, "/img/reset.png", "Rest");
        
        // Add image panel to the top (NORTH) of the main panel
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        // Add a label to the center of the panel
        JLabel label = new JLabel("TODO", SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);

        add(mainPanel);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MealEditor().setVisible(true);
        });
    }
}
