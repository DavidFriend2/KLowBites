package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import Information.Recipe;
import Information.Step;
import Information.Utensil;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
/**
 * Creates the window to display utensils and steps in a recipe or meal.
 * 
 * @author Josh Browning.
 */
public class ProcessViewer extends JFrame
{

	private static final long serialVersionUID = 1L;
	private Recipe recipe;
	
	public ProcessViewer() 
	{
	    setTitle("Recipe Viewer");
	    setSize(500, 500);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    // Main panel
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    JPanel printButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    
	    // Add print button
	    buttonCreation button = new buttonCreation();
	    button.addImageButton(printButton, "/img/print.png", "Print");
	    
	    mainPanel.add(printButton, BorderLayout.NORTH);
	
	    // Utensils panel
	    JScrollPane utensilsPanel = createUtensilsPanel("None");
	    mainPanel.add(utensilsPanel, BorderLayout.CENTER);
	
	    // Steps panel
	    JScrollPane stepsPanel = createStepsPanel("None");
	    mainPanel.add(stepsPanel, BorderLayout.SOUTH);
	
	    add(mainPanel);
	}
	
	public ProcessViewer(Recipe recipe) 
	{
	    this.recipe = recipe;
	
	    setTitle("Recipe Viewer   " + recipe.getName());
	    setSize(500, 500);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    // Main panel
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    JPanel printButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    
	    // Add print button
	    buttonCreation button = new buttonCreation();
	    button.addImageButton(printButton, "/img/print.png", "Print");
	    
	    printButton.addMouseListener(new MouseAdapter() 
	    {
	        @Override
	        public void mouseClicked(MouseEvent e) 
	        {
	        	PrinterJob job = PrinterJob.getPrinterJob();
	    		if (job.printDialog()) 
	    		{
	    			try 
	    			{
						job.print();
					} 
	    			catch (PrinterException e1) 
	    			{
						e1.printStackTrace();
					}
	            }
	        }
        });
	    
	    mainPanel.add(printButton, BorderLayout.NORTH);
	
	    // Utensils panel
	    JScrollPane utensilsPanel = createUtensilsPanel();
	    mainPanel.add(utensilsPanel, BorderLayout.CENTER);
	
	    // Steps panel
	    JScrollPane stepsPanel = createStepsPanel();
	    mainPanel.add(stepsPanel, BorderLayout.SOUTH);
	
	    add(mainPanel);
	}
	
	private JScrollPane createUtensilsPanel(String string)
	{
		if (string.equals("None")) {
			JScrollPane utensilsPanel = new JScrollPane();
			utensilsPanel.setBorder(BorderFactory.createTitledBorder("Utensils"));
			return utensilsPanel;
		} else {
			return null;
		}
	}
	
	private JScrollPane createStepsPanel(String string)
	{
		if (string.equals("None")) {
			JScrollPane stepsPanel = new JScrollPane();
			stepsPanel.setBorder(BorderFactory.createTitledBorder("Steps"));
			return stepsPanel;
		} else {
			return null;
		}
	}
	
	private JScrollPane createUtensilsPanel() 
	{
		
		JScrollPane utensilsPanel = new JScrollPane();
		
		DefaultListModel<String> DLM = new DefaultListModel<>();
	    JList<String> utensilsList = new JList<>(DLM);
	
	    // Add items to utensils list
	    for (Utensil utensil : recipe.getUtenils())	    
	    {
            String name = utensil.getName();
            String details = utensil.getDetails() != null ? utensil.getDetails() : "No description";
            DLM.addElement(name + ": " + details);
        }
	    
	    utensilsPanel = new JScrollPane(utensilsList);
	    
	    // Create border with title
	    utensilsPanel.setBorder(BorderFactory.createTitledBorder("Utensils"));
	    
	    return utensilsPanel;
	}
	
	private JScrollPane createStepsPanel() 
	{
		DefaultListModel<String> DLM = new DefaultListModel<>();
	    JList<String> stepsList = new JList<>(DLM);
	    
	    JScrollPane stepsPanel = new JScrollPane(stepsList);
	    stepsPanel = new JScrollPane(stepsList);
	    stepsPanel.setBorder(BorderFactory.createTitledBorder("Steps"));
	    
	    // Initialize step number
	    int stepCount = 1;

	    // Iterate through each step in the recipe and format it
	    for (Step step : recipe.getSteps()) {
	        String actionText = String.format("Step %d: %s %s in %s%s\n",
	            stepCount++, 
	            step.getAction(),
	            step.getSourceUtensilOrIngredient(),
	            step.getDestinationUtensil() != null ? step.getDestinationUtensil() : "No destination",
	            step.getDetails() != null ? " - " + step.getDetails() : ""
	        );
	        DLM.addElement(actionText);
	    }

	    // Set the formatted steps in the JTextArea
	    
	    // Holds the steps
	    return stepsPanel;
	}
	
	public static void main(String[] args) 
	{
	    SwingUtilities.invokeLater(() -> {
	        Recipe bananasFoster = Recipe.getRecipes().get(1);
	        new ProcessViewer().setVisible(true);
	    });
	}
}