package gui.UnitConverterListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

//ActionListener for the reset button
public class ResetListener implements ActionListener
{
  private JLabel toAmount;
  private JComboBox<String> from;
  private JComboBox<String> to;
  private JComboBox<String> ingredient;
  private JTextField amount;
  private Locale currentLocale;
  private ResourceBundle strings;
 
  public ResetListener(final JLabel toAmount, final JComboBox<String> from, 
      final JComboBox<String> to, final JComboBox<String> ingredient, 
      final JTextField amount, final Locale locale) 
  {
    this.toAmount = toAmount;
    this.from = from;
    this.to = to;
    this.ingredient = ingredient;
    this.amount = amount;
    this.currentLocale = locale;
    loadStrings(locale);
  }
  
  private void loadStrings(final Locale locale) 
  {
    try 
    {
      strings = ResourceBundle.getBundle("resources.Strings", locale);
    } 
    catch (MissingResourceException e) 
    {
      System.err.println("Could not find resources.Strings for locale " 
          + locale + ": " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  //Resets the window
  public void actionPerformed(final ActionEvent e)
  {
    toAmount.setText(strings.getString("to_amount_label") + ": ______________");
    from.setSelectedItem("");
    to.setSelectedItem("");
    ingredient.setSelectedItem("");
    ingredient.setEnabled(false);
    amount.setText("");
  }
  
  public void updateLocale(final Locale newLocale)
  {
    this.currentLocale = newLocale;
    loadStrings(newLocale);
  }
}
