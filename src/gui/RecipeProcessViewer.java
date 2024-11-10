package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;
import java.awt.print.*;
import java.util.List;

/**
 * Creates the window to display utensils and steps in a recipe or meal.
 * 
 * @author Josh Browning.
 */
public class RecipeProcessViewer extends JFrame implements Printable
{

	private static final long serialVersionUID = 1L;
	private Recipe recipe;
	
	public RecipeProcessViewer() 
	{
	    setTitle("Recipe Viewer");
	    setSize(500, 500);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    // Main panel
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    // JPanel printButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    
	    // Add print button
	    // buttonCreation button = new buttonCreation();
	    // button.addImageButton(printButton, "/img/print.png", "Print");
	    
	    // mainPanel.add(printButton, BorderLayout.NORTH);
	
	    // Utensils panel
	    JScrollPane utensilsPanel = createEmptyPanel("Utensils");
	    utensilsPanel.setSize(200, 200);
	    mainPanel.add(utensilsPanel, BorderLayout.CENTER);
	
	    // Steps panel
	    JScrollPane stepsPanel = createEmptyPanel("Steps");
	    stepsPanel.setSize(200, 200);
	    mainPanel.add(stepsPanel, BorderLayout.SOUTH);
	
	    add(mainPanel);
	}
	
	public RecipeProcessViewer(Recipe recipe) 
	{
	    this.recipe = recipe;
	
	    setTitle("Recipe Viewer   " + recipe.getName());
	    setSize(500, 500);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    // Main panel
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    // JPanel printButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    
	    // Add print button
	    // buttonCreation button = new buttonCreation();
	    // button.addImageButton(printButton, "/img/print.png", "Print");
	
	    // Utensils panel
	    JScrollPane utensilsPanel = createUtensilsPanel();
	    utensilsPanel.setSize(100, 100);
	    mainPanel.add(utensilsPanel, BorderLayout.CENTER);
	
	    // Steps panel
	    JScrollPane stepsPanel = createStepsPanel();
	    stepsPanel.setSize(200, 200);
	    mainPanel.add(stepsPanel, BorderLayout.SOUTH);
	
	    add(mainPanel);
	}
	
	private JScrollPane createEmptyPanel(String title) {
        JScrollPane emptyPanel = new JScrollPane();
        emptyPanel.setBorder(BorderFactory.createTitledBorder(title));
        return emptyPanel;
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
	    
	    int stepCount = 1;

	    // Iterate through each step in the recipe and format it
	    for (Step step : recipe.getSteps()) 
	    {
	    	String source = step.getSourceUtensilOrIngredient();
	    	String destination = "in " + step.getDestinationUtensil();
	    	Double amount;
	    	String unit;
	    	
	    	if (source.equals(step.getDestinationUtensil())) 
	    	{
	    		destination = "";
	    	}
	    	
	    	int ingredientCount = 0;
	    	
	    	if (source.equalsIgnoreCase(recipe.getIngredients().get(ingredientCount).getName())) 
	    	{
	    		amount = recipe.getIngredients().get(0).getAmount();
	            unit = recipe.getIngredients().get(0).getUnit();
	    	} 
	    	else
	    	{
	    		amount = recipe.getIngredients().get(1).getAmount();
	            unit = recipe.getIngredients().get(1).getUnit();
	    	}
	    	
	    	String actionText = String.format("Step %d: %s %s %s %s %s%s\n",
	            stepCount++,
	            step.getAction(),
	            amount,
	            unit,
	    	    source,
	            destination,
	            step.getDetails() != null ? " - " + step.getDetails() : ""
	        );
	        DLM.addElement(actionText);
	    }

	    // Set the formatted steps in the JTextArea
	    
	    // Holds the steps
	    return stepsPanel;
	}
	
	public int print(Printable p, JFrame j) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		try
		{
			job.setPrintable(p);
			boolean shouldPrint = job.printDialog();
			if (shouldPrint)
				job.print();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(j, "Unable to print!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void main(String[] args) 
	{
	    SwingUtilities.invokeLater(() -> {
	        Recipe bananasFoster = Recipe.getRecipes().get(1);
	        new RecipeProcessViewer(bananasFoster).setVisible(true);
	    });
	}
}