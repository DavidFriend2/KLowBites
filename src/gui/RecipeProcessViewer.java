package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.File;
import java.sql.Time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
public class RecipeProcessViewer extends JFrame implements Printable {

  private static final long serialVersionUID = 1L;
  private static final String PRINT_ICON_PATH = "/img/print.png";
  private static final String OPEN_ICON_PATH = "/img/open.png";
  private Recipe recipe;
  private boolean doubleBuffered;
  private JComponent delegate;
  private JButton printButton, openButton;
  private JPanel printPanel, mainPanel, utensilsPanel, stepsPanel;
  private JTextField platingTime;
  private List<Recipe> selectedRecipes = new ArrayList<>();
  private ResourceBundle strings;
    
	
  public RecipeProcessViewer(Recipe recipe, Locale locale) {
    this.recipe = recipe;
    strings = ResourceBundle.getBundle("resources.Strings", locale);
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
        addTimePrompt();
    }
	
	private void addPrintButton() {
    printButton = createButton(PRINT_ICON_PATH, 50, 50, strings.getString("print_button_tooltip"));
    printButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            printRecipe();
        }
    });
    printPanel.add(printButton);
}

private void addOpenButton() {
    openButton = createButton(OPEN_ICON_PATH, 50, 50, strings.getString("open_button_tooltip"));
    openButton.addActionListener(new OpenRecipesListener());
    printPanel.add(openButton);
}

private void addTimePrompt() {
    JLabel platingTimeLabel = new JLabel(strings.getString("plating_time_label"));
    
    platingTime = new JTextField(3);
    
    platingTime.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //enteredTime = ((Object) platingTime).parseTime();
            //createStepsPanel(enteredTime);
        }
    });
    
    printPanel.add(platingTimeLabel);
    printPanel.add(platingTime);
}

private void printRecipe() {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);

    if (job.printDialog()) {
        try {
            job.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, strings.getString("print_error_message"), strings.getString("error_title"), JOptionPane.ERROR_MESSAGE);
        }
    }
}

private class OpenRecipesListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        directoryChooser.setDialogTitle(strings.getString("choose_directory_dialog_title"));

        int userSelection = directoryChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File directory = directoryChooser.getSelectedFile();
            loadAllRecipes(directory);
            for (Recipe r : selectedRecipes) {
                new RecipeProcessViewer(r).setVisible(true);
            }
        }
    }
}
	
	private void loadAllRecipes(File directory)
	{
		File[] files = directory.listFiles((dir, name) -> name.endsWith(".rcp"));

		if (files.length > 0) 
		{
			selectedRecipes.clear();

			for (File file : files) 
			{
				try 
				{
					Recipe recipe = Recipe.loadRecipeFromFile(file.getAbsolutePath());
					selectedRecipes.add(recipe);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		} else {
			// status.setText("No recipe files found");
		}

	}
	
	private JScrollPane createUtensilsPanel() {
    JScrollPane utensilsPanel = new JScrollPane();
    utensilsPanel.setPreferredSize(new Dimension(10, 1000));
    DefaultListModel<String> DLM = new DefaultListModel<>();
    JList<String> utensilsList = new JList<>(DLM);
    
    if (this.recipe != null) {
        // Add items to utensils list
        for (Utensil utensil : recipe.getUtenils()) {
            String name = utensil.getName();
            String details = utensil.getDetails() != null ? utensil.getDetails() : "";
            DLM.addElement(strings.getString("utensil_format").formatted(name, details));
        }
        
        utensilsPanel = new JScrollPane(utensilsList);
    }
    
    // Create border with title
    utensilsPanel.setBorder(BorderFactory.createTitledBorder(strings.getString("utensils_panel_title")));
    
    return utensilsPanel;
}
	
	private JScrollPane createStepsPanel() {
    DefaultListModel<String> DLM = new DefaultListModel<>();
    JList<String> stepsList = new JList<>(DLM);
    
    JScrollPane stepsPanel = new JScrollPane(stepsList);
    
    if (this.recipe != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        int stepCount = 1;
    
        // Iterate through each step in the recipe and format it
        for (Step step : recipe.getSteps()) {
            String source = step.getSourceUtensilOrIngredient();
            String destination = strings.getString("step_destination_format").formatted(step.getDestinationUtensil());
            String info = source.toString();
            
            if (source.equals(step.getDestinationUtensil())) {
                destination = "";
            }
            
            for (RecipeIngredient ingredient : recipe.getIngredients()) {
                if (ingredient.getName().equalsIgnoreCase(step.getSourceUtensilOrIngredient())) {
                    info = ingredient.toString();
                    break;
                }
            }
            
            String actionText = strings.getString("step_format").formatted(
                stepCount++,
                step.getAction(),
                info,
                destination,
                step.getDetails() != null ? strings.getString("step_details_format").formatted(step.getDetails()) : ""
            );
            DLM.addElement(actionText);
        }
    }
    
    stepsPanel.setBorder(BorderFactory.createTitledBorder(strings.getString("steps_panel_title")));
    
    return stepsPanel;
}
	
	/*
	private JScrollPane createStepsPanel(Time time) 
	{
		DefaultListModel<String> DLM = new DefaultListModel<>();
	    JList<String> stepsList = new JList<>(DLM);
	    
	    JScrollPane stepsPanel = new JScrollPane(stepsList);
	    
	    if (this.recipe != null && enteredTime != null)
	    {
		    int stepCount = 1;
		    double cumTime;
		    
		    try {
	            cumTime = Double.parseDouble(enteredTime);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Invalid time entered", "Error", JOptionPane.ERROR_MESSAGE);
	            return stepsPanel;
	        }
		
		    // Iterate through each step in the recipe and format it
		    for (Step step : recipe.getSteps()) 
		    {
		    	double stepTime = step.getTime();
	            cumTime -= stepTime;
		    	String source = step.getSourceUtensilOrIngredient();
		    	String destination = " in " + step.getDestinationUtensil();
		    	String info = source.toString();
		    	
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
		    	
		    	String actionText = String.format("At %.2f min - Step %d (%s min): %s %s%s%s",
		    			cumTime,
		    			stepCount++,
		    			stepTime,
		    			step.getAction(),
		    			info,
		    			destination,
		    			step.getDetails() != null ? " - " + step.getDetails() : ""
		        );
		        DLM.addElement(actionText);
		    }
		    /*
		    stepsList.addMouseListener(new MouseListener()
	        {
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					Recipe selectedRecipe = (Recipe) stepsList.getSelectedValue();
					if (selectedRecipe.equals(recipe)) 
	                {
	                    new RecipeProcessViewer(selectedRecipe).setVisible(true);
	                }
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}
	        });
	    }
	    
	    stepsPanel.setBorder(BorderFactory.createTitledBorder("Steps"));
	    
	    // Holds the steps
	    return stepsPanel;
	}*/
	
	
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
	
	public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        // You can change this to the desired default locale
        Locale desiredLocale = Locale.getDefault();
        
        Recipe bananasFoster = Recipe.getRecipes().get(0);
        
        // Create a new RecipeProcessViewer instance with the desired locale and recipe
        RecipeProcessViewer viewer = new RecipeProcessViewer(bananasFoster, desiredLocale);
        viewer.setVisible(true);
    });
}
}