package gui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Button Creation helper.
 * 
 * @author Team3C
 */
public class buttonCreation {

  /**
   * Creates an image button.
   * 
   * @param panel
   * @param imagePath given image
   * @param buttonName
   */
	public void addImageButton(final JPanel panel, final String imagePath, 
	    final String buttonName) 
	{
    ImageIcon icon = createImageIcon(imagePath);
    if (icon != null) 
    {
      Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
      JButton button = new JButton(new ImageIcon(img));
      button.setPreferredSize(new Dimension(50, 50));
      button.setToolTipText(buttonName);
      Border emptyBorder = BorderFactory.createEmptyBorder();
      button.setBorder(emptyBorder);
      panel.add(button);
    }
  }
  	/**
  	 * Creates the image icon.
  	 * 
  	 * @param path
  	 * @return the image
  	 */
  public ImageIcon createImageIcon(final String path)
  {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) 
    {
      return new ImageIcon(imgURL);
    } 
    else 
    {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
}
