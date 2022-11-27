package Game;

import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;

/**
 * @author bened
 * A button that knows its home panel
 */
public class PanelButton extends JButton implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	
	PanelButton(String s, JPanel p, ActionListener AL){
		panel = p;
		this.setText(s);
		this.addActionListener(AL);
	}
	public JPanel getPanel() {return panel;}
	
}
