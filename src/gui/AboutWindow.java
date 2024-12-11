package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AboutWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234234234;
	private final ResourceBundle strings;

    public AboutWindow(Locale locale) {
    	strings = ResourceBundle.getBundle("resources.Strings", locale);
        initializeUI(locale);
    }

	private void initializeUI(Locale locale) {
		setTitle(strings.getString("about_window_title"));
		setSize(300, 200);
		setLocationRelativeTo(null); // Center on screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titleLabel = new JLabel(strings.getString("about_title"));
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);

		JTextArea descriptionArea = new JTextArea(strings.getString("about_description"));
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setLineWrap(true);
		descriptionArea.setOpaque(false);
		descriptionArea.setEditable(false);

		panel.add(titleLabel, BorderLayout.NORTH);
		panel.add(descriptionArea, BorderLayout.CENTER);

		add(panel);
	}
	
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            Locale locale = Locale.getDefault(); // Or any other Locale
            AboutWindow aboutWindow = new AboutWindow(locale);
            aboutWindow.setVisible(true);
        });
    }
}
