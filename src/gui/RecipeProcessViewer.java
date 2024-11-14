package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;


import gui.EditorListeners.*;

/**
 * Creates the window to display utensils and steps in a recipe or meal.
 * 
 * @author Josh Browning.
 */
public class RecipeProcessViewer extends JFrame implements Printable
{

	private static final long serialVersionUID = 1L;
	private static final String PRINT_ICON_PATH = "/img/print.png";
	private static final String OPEN_ICON_PATH = "/img/open.png";
	private Recipe recipe;
	private boolean doubleBuffered;
    private JComponent delegate;
    private JButton printButton, openButton;
    private JPanel printPanel, mainPanel, utensilsPanel, stepsPanel;
	
	public RecipeProcessViewer() 
	{
	    initialize();
	}
	
	public RecipeProcessViewer(Recipe recipe) 
	{
		this.recipe = recipe;
        initialize();
        setTitle("KiLowBites Process Viewer: " + recipe.getName());
	}
	
	private void initialize() 
	{
		setTitle("KiLowBites Process Viewer");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        printPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        mainPanel.add(printPanel, BorderLayout.NORTH);
        
        // Add utensils 
        utensilsPanel = new JPanel(new BorderLayout());
	    JScrollPane utensilsScroll = createUtensilsPanel();
	    utensilsPanel.add(utensilsScroll, BorderLayout.CENTER);
	    utensilsPanel.setPreferredSize(new Dimension(200, 100));

        // Add steps
        stepsPanel = new JPanel(new BorderLayout());
        JScrollPane stepsScroll = createStepsPanel();
        stepsPanel.add(stepsScroll, BorderLayout.CENTER);
	    stepsPanel.setPreferredSize(new Dimension(200, 300));
	    
	    // Add both to main panel
	    mainPanel.add(utensilsPanel, BorderLayout.CENTER);
        mainPanel.add(stepsPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        addPrintButton();
        addOpenButton();
    }
	
	private void addPrintButton() 
	{
        printButton = createButton(PRINT_ICON_PATH, 50, 50, "Print");
        printButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                printRecipe();
            }
        });
        printPanel.add(printButton);
    }
	
	private void addOpenButton()
	{
		openButton = createButton(OPEN_ICON_PATH, 50, 50, "Open");
        openButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new RecipeSearcher().setVisible(true);
            }
        });
        printPanel.add(openButton);
	}
	
	private void printRecipe() 
	{
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) 
        {
            try 
            {
                job.print();
            } 
            catch (PrinterException e) 
            {
                JOptionPane.showMessageDialog(this, "Unable to print", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	private JScrollPane createUtensilsPanel() 
	{
		
		JScrollPane utensilsPanel = new JScrollPane();
        utensilsPanel.setPreferredSize(new Dimension(10,1000));
		JList<String> utensilsList;
		DefaultListModel<String> DLM;
		
		if (this.recipe != null) 
		{
			DLM = new DefaultListModel<>();
		    utensilsList = new JList<>(DLM);
		
		    // Add items to utensils list
		    for (Utensil utensil : recipe.getUtenils())	    
		    {
		        String name = utensil.getName();
		        String details = utensil.getDetails() != null ? utensil.getDetails() : "No description";
		        DLM.addElement(name + ": " + details);
		    }
		    
		    utensilsPanel = new JScrollPane(utensilsList);
		}
	    
	    // Create border with title
	    utensilsPanel.setBorder(BorderFactory.createTitledBorder("Utensils"));
	    
	    return utensilsPanel;
	}
	
	private JScrollPane createStepsPanel() 
	{
		DefaultListModel<String> DLM = new DefaultListModel<>();
	    JList<String> stepsList = new JList<>(DLM);
	    
	    JScrollPane stepsPanel = new JScrollPane(stepsList);
	    
	    if (this.recipe != null)
	    {
		    int stepCount = 1;
		    double time = 0;
		
		    // Iterate through each step in the recipe and format it
		    for (Step step : recipe.getSteps()) 
		    {
		    	String source = step.getSourceUtensilOrIngredient();
		    	String destination = " in " + step.getDestinationUtensil();
		    	String info = source.toString();
		    	time = step.getTime();
		    	
		    	if (source.equals(step.getDestinationUtensil())) 
		    	{
		    		destination = "";
		    	}
		    	
		    	for (RecipeIngredient ingredient : recipe.getIngredients())
		    	{
		    		if (ingredient.getName().equalsIgnoreCase(step.getSourceUtensilOrIngredient()))
		    		{
		    			info = ingredient.toString();
		    			break;
		    		}
		    	}
		    	
		    	String actionText = String.format("Step %d (%s min): %s %s%s%s\n",
		            stepCount++,
		            time,
		            step.getAction(),
		            info,
		            destination,
		            step.getDetails() != null ? " - " + step.getDetails() : ""
		        );
		        DLM.addElement(actionText);
		    }
	    }
	    
	    stepsPanel.setBorder(BorderFactory.createTitledBorder("Steps"));
	    
	    // Holds the steps
	    return stepsPanel;
	}
	
	private JButton createButton(String imagePath, int width, int height, String toolTipText) 
	{  
		ImageIcon icon = createImageIcon(imagePath);
        JButton button = new JButton();

        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } else {
            button.setText(toolTipText);
        }

        button.setToolTipText(toolTipText);
        button.setPreferredSize(new Dimension(width, height));
        return button;                                                                             
	}
	
	private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Could not find image at " + path);
            return null;
        }
    }
	
	public int print(Printable p, JFrame j) throws PrinterException 
	{
		PrinterJob job = PrinterJob.getPrinterJob();
		try
		{
			job.setPrintable(p);
			boolean shouldPrint = job.printDialog();
			if (shouldPrint)
				job.print();
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(j, "Unable to print!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}
	
	public void DelegatingPrintable(JComponent delegate)
    {
       this.delegate = delegate;
 
       doubleBuffered = delegate.isDoubleBuffered();
    }
	
	@Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics g2d = graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        // double scaleX = pageFormat.getImageableWidth() / delegate.getWidth();
        //double scaleY = pageFormat.getImageableHeight() / delegate.getHeight();
        // double scale = Math.min(scaleX, scaleY);
        // g2d.scale(scale, scale);

        delegate.setDoubleBuffered(false);
        delegate.paint(g2d);
        delegate.setDoubleBuffered(doubleBuffered);

        return PAGE_EXISTS;
    }
	
	public static void main(String[] args) 
	{
	    SwingUtilities.invokeLater(() -> {
	        Recipe bananasFoster = Recipe.getRecipes().get(0);
	        new RecipeProcessViewer(bananasFoster).setVisible(true);
	    });
	}
}