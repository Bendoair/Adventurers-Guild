package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;

/**
 * @author bened
 * Creates a panel that contains infomations about a quest
 * The button assigns the hero to the quest
 */
public class QuestAssignPanel extends JPanel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Quest quest;
	private JTextField whomToAssign;
	
	
	
	
	
	QuestAssignPanel(Quest q, ActionListener buttonAL){
		
		quest = q;
		
		this.setSize(200, 200);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getSize());
		this.setBackground(new Color(255, 0, 0));
		
		
		

		JTextArea Info;
		Info = new JTextArea();
		Info.setSize(100, 100);
		Info.setText( quest.getName() + "\n" + "Threat Level: " +quest.getThreatLevelString() + "\n" + "Reward: "+ quest.getReward() );
		Info.setEditable(false);
		
		whomToAssign = new JTextField(15);
		whomToAssign.setEditable(true);
		
		
		PanelButton Accept = new PanelButton("Assign Hero", this, buttonAL);
		
		
		this.add(Info, BorderLayout.NORTH);
		this.add(whomToAssign, BorderLayout.SOUTH);
		this.add(Accept, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}





	public Quest getQuest() {
		return quest;
	}





	public String getWhomToAssign() {
		return whomToAssign.getText();
	}
}
	
