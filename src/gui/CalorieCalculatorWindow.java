package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the Calorie Calculator Window for KILowBites
 * 
 * @author Jayden S
 */
public class CalorieCalculatorWindow extends JFrame {
    private static final long serialVersionUID = 1293847255;

    private static final String ICON1_PATH = "/img/calculate.png";
    private static final String ICON2_PATH = "/img/reset.png";

    private JPanel mainPanel;
    private JPanel iconPanel;
    private JPanel inputPanel;
    private JPanel caloriesPanel;
    private JButton calculateButton;
    private JButton resetButton;
    private JComboBox<String> ingredientComboBox;
    private JTextField amountField;
    private JComboBox<String> unitsComboBox;
    private JTextField caloriesField;

    public CalorieCalculatorWindow() {
        initializeWindow();
        createPanels();
        addComponentsToPanels();
        assembleMainPanel();
        setupActionListeners();
    }

    private void initializeWindow() {
        setTitle("KiLowBites Calorie Calculator");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createPanels() {
        mainPanel = new JPanel(new BorderLayout());
        iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        caloriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    private void addComponentsToPanels() {
        addIconsToPanel();
        addInputComponents();
        addCaloriesComponents();
    }

    private void addIconsToPanel() {
        calculateButton = createIconButton(ICON1_PATH, 30, 30, "Calculate");
        resetButton = createIconButton(ICON2_PATH, 30, 30, "Reset");
        iconPanel.add(calculateButton);
        iconPanel.add(resetButton);
    }

    private JButton createIconButton(String imagePath, int width, int height, String toolTipText) {
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

    private void addInputComponents() {
        inputPanel.add(new JLabel("Ingredient:"));
        ingredientComboBox = new JComboBox<>(new String[]{"Ingredient 1", "Ingredient 2", "Ingredient 3"});
        inputPanel.add(ingredientComboBox);
        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField(5);
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Units:"));
        unitsComboBox = new JComboBox<>(new String[]{"pinches", "teaspoons", "tablespoons", "fluid ounces", "cups",
            "pints", "quarts", "gallons", "milliliters", "drams", "grams", "ounces", "pounds"});
        inputPanel.add(unitsComboBox);
    }

    private void addCaloriesComponents() {
        caloriesPanel.add(new JLabel("Calories:"));
        caloriesField = new JTextField(10);
        caloriesField.setEditable(false);
        caloriesPanel.add(caloriesField);
    }

    private void assembleMainPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(iconPanel, BorderLayout.NORTH);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(caloriesPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.NORTH);
        add(mainPanel);
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void setupActionListeners() {
        calculateButton.addActionListener(e -> calculateCalories());
        resetButton.addActionListener(e -> resetFields());
    }

    private void calculateCalories() {
        String ingredient = (String) ingredientComboBox.getSelectedItem();
        String amountStr = amountField.getText();
        String unit = (String) unitsComboBox.getSelectedItem();
        
        try {
            double amount = Double.parseDouble(amountStr);
            
            // TODO call the calculation class here
            
            // Placeholder:
            caloriesField.setText("Calculation not implemented");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        ingredientComboBox.setSelectedIndex(0);
        amountField.setText("");
        unitsComboBox.setSelectedIndex(0);
        caloriesField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalorieCalculatorWindow window = new CalorieCalculatorWindow();
            window.setVisible(true);
        });
    }
}