package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;

/**
 * @author bened
 * Creates a panel that contains infomations about a hero
 * The button hires the hero
 */
public class HeroHirePanel extends JPanel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hero hero;
	
	
	
	
	HeroHirePanel(Hero h, ActionListener buttonAL){
		hero = h;
		this.setSize(200, 200);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getSize());
		this.setBackground(new Color(255, 0, 0));
		

		JTextArea Info;
		Info = new JTextArea();
		Info.setSize(100, 100);
		Info.setPreferredSize(new Dimension(100, 100));
		Info.setText(hero.toString() + "\nPrice: " + h.getValue());
		Info.setEditable(true);
		
		PanelButton Accept = new PanelButton("Recruit", this, buttonAL);
		
		
		
		this.add(Info, BorderLayout.NORTH);
		this.add(Accept, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
	}
	
	public Hero getHero() {return hero;}




	

}
