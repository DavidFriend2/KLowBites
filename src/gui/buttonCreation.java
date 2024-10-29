package gui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class buttonCreation {

	@SuppressWarnings("unused")
	public void addImageButton(JPanel panel, String imagePath, String buttonName) {
        ImageIcon icon = createImageIcon(imagePath);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JButton button = new JButton(new ImageIcon(img));
            button.setPreferredSize(new Dimension(50, 50));
            button.setToolTipText(buttonName);
            Border emptyBorder = BorderFactory.createEmptyBorder();
            button.setBorder(emptyBorder);
            panel.add(button);
        }
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
}
