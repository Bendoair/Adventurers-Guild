package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;

/**
 * @author bened
 * Creates a panel that contains infomations about a quest
 * The button accepts the quest
 */
public class QuestPanel extends JPanel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Quest quest;
	
	
	
	
	QuestPanel(Quest q, ActionListener buttonAL){
		quest = q;
		this.setSize(150, 200);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getMaximumSize());
		this.setBackground(new Color(255, 0, 0));
		

		JTextArea Info;
		Info = new JTextArea();
		Info.setSize(100, 100);
		Info.setPreferredSize(new Dimension(100, 100));
		Info.setText(quest.getName() + "\n" + "Threat Level: " +quest.getThreatLevelString() + "\n" + "Reward: "+ quest.getReward() + "\nDeadline: " + quest.getDeadLineString());
		Info.setEditable(true);
		
		PanelButton Accept = new PanelButton("Accept", this, buttonAL);
		
		
		
		this.add(Info, BorderLayout.NORTH);
		this.add(Accept, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
	}
	
	public Quest getQuest() {return quest;}




	

}
