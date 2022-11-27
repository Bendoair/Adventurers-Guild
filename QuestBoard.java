package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;



/**
 * @author bened
 * Quest board creates quests, and manages them
 * (add, remove, etc.)
 */
public class QuestBoard implements Serializable, Tickable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Quest> AvailableQuests;
	private ArrayList<Quest> AddedQuests;
	private ArrayList<Quest> ShownQuests;
	private Calendar calendar;
	
	QuestBoard(Calendar t){
		AvailableQuests = new ArrayList<Quest>();
		AddedQuests = new ArrayList<Quest>();
		ShownQuests = new ArrayList<Quest>();
		calendar = t;
	}
	
	/**
	 * Removes specified Quest.
	 * @param q Quest
	 * @return q
	 */
	public Quest removeQuest(Quest q) {
		AvailableQuests.remove(q);
		ShownQuests.remove(q);
		AddedQuests.add(q);
		return q;
	}
	
	/**
	 * Creates a new Quest and adds it to the AvailableQuests list
	 */
	public void newQuest() {
		if(calendar == null) {
			calendar = new Calendar();
		}
		Random randgen = new Random();
		double[] Attr = new double[3];
		int BestAttr = 0;
		for (int i = 0; i < Attr.length; i++) {
			Attr[i] = randgen.nextInt((Integer)calendar.getTime()/100 + 1)+1;
			if(Attr[i] > Attr[BestAttr])
				BestAttr = i;
		}
		int rew = (int)Attr[BestAttr]*5;
		//Naming not properly implemented
		Quest nq = new Quest(Attr[0], Attr[1],Attr[2], rew, calendar.getTime(), ("Quest" + Integer.toString(calendar.getTime())));
	
	
		AvailableQuests.add(nq);
	}
	
	public int QuestCount() {
		return AvailableQuests.size();
	}
	
	public ArrayList<Quest> getQuestsToDisplay(){
		ArrayList<Quest> ret = new ArrayList<Quest>();
		for (int i = 0; i < AvailableQuests.size(); i++) {
			if(!ShownQuests.contains(AvailableQuests.get(i))) {
				ShownQuests.add(AvailableQuests.get(i));
				ret.add(AvailableQuests.get(i));
			}
		}
		
		return ret;
	}

	@Override
	public void Tick() {
		calendar.Tick();
		
	}
	
	public void nothingShown() {
		ShownQuests.clear();
	}
	
	
	
	
}
