package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bened
 * Guild class is the class responsible for keeping the player's informations
 */

public class Guild implements Tickable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int Fame;
	private int Money;
	private ArrayList<Hero> Heroes;
	private ArrayList<Hero> Recruits;
	private ArrayList<Quest> Quests;
	private Map<Quest, Hero> Assign;
	private Calendar calendar;
	
	private ArrayList<Hero> ShownHeroes;
	private ArrayList<Hero> ShownRecruits;
	private ArrayList<Quest> ShownQuests;

	
	
	
	Guild(Calendar t){
		Fame = 0;
		Money = 100;
		Heroes = new ArrayList<Hero>();
		ShownHeroes = new ArrayList<Hero>();

		Recruits = new ArrayList<Hero>();
		ShownRecruits = new ArrayList<Hero>();

		Quests = new ArrayList<Quest>();
		ShownQuests = new ArrayList<Quest>();
		
		Assign = new HashMap<Quest, Hero>();
		calendar = t;
		
		
		
	}
	
	public int getMoney() {return Money;}
	public int getFame() {return Fame;}
	
	public String getMoneyString() {return Integer.toString(Money);}
	public String getFameString() {return Integer.toString(Fame);}
	
	/**
	 * The system knows which heroes are already displayed
	 * Returns the ones that aren't
	 * @return the heroes to display 
	 */
	public ArrayList<Hero> getHeroesToDisplay() {
		ArrayList<Hero> ret = new ArrayList<Hero>();
		for (int i = 0; i < Heroes.size(); i++) {
			if(!ShownHeroes.contains(Heroes.get(i))) {
				ShownHeroes.add(Heroes.get(i));
				ret.add(Heroes.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * The system knows which recruits are already displayed
	 * Returns the ones that aren't
	 * @return the recruits that should be displayed
	 */
	public ArrayList<Hero> getRecruitsToDisplay() {
		ArrayList<Hero> ret = new ArrayList<Hero>();
		for (int i = 0; i < Recruits.size(); i++) {
			if(!ShownRecruits.contains(Recruits.get(i))) {
				ShownRecruits.add(Recruits.get(i));
				ret.add(Recruits.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * returns unassigned quests
	 * (the quests that should be shown)
	 * @return quests
	 */
	public ArrayList<Quest> getQuestNotAssigned(){
		ArrayList<Quest> ret = new ArrayList<Quest>();
		for (int i = 0; i < Quests.size(); i++) {
			if(!ShownQuests.contains(Quests.get(i))&&!Assign.containsKey(Quests.get(i))) {
				ShownQuests.add(Quests.get(i));
				ret.add(Quests.get(i));
			}
		}
		return ret;
	}
	
	public void notifyQuestDisplayed(Quest q) {
		ShownQuests.add(q);
	}
	public void notifyHeroDisplayed(Hero h) {
		ShownHeroes.add(h);
	}
	public void notifyRecruitDisplayed(Hero h) {
		ShownRecruits.add(h);
	}
	
	public String getInfo() {
		String Info = new String("Fame:\t" + getFame() + "\nMoney:\t" + getMoney() + "\nCurrent Time:\t" + calendar.getTime());
		
		return Info;
		
	}

	
	/**
	 * Adds a Hero to the Heroes List;
	 * @param Hero to add
	 */
	public void addHero(Hero h) {
		Heroes.add(h);
	}
	
	/**
	 * Adds a Quest to the Quests List;
	 * @param Hero to add
	 */
	public void addQuest(Quest q) {
		Quests.add(q);
	}

	/**
	 * Assigns the Hero to the Quest
	 * The Hero will try to complete the quest from now on
	 * @param quest
	 * @param hero
	 */
	public void assignHero(Quest q, Hero h) {
		Assign.put(q, h);
	}
	
	/**
	 * Assigns heroes by name, if Hero object is not known or hard to reach 
	 * @param q quest
	 * @param heroName
	 */
	public void assignByName(Quest q, String heroName) {
		ShownQuests.remove(q);
		for(int i = 0; i < Heroes.size(); i++) {
			if(Heroes.get(i).getName().equals(heroName) && !Assign.containsValue(Heroes.get(i))) {
				Assign.put(q, Heroes.get(i));
				ShownQuests.add(q);
			}
		}
		
		
		
	}
	
	/**
	 * Removes the given Hero from the Heroes list
	 * @param Hero
	 */
	public void removeHero(Hero h) {
		Heroes.remove(h);
		Assign.forEach((k, v) -> {if (v == h) Assign.remove(k);});
	}
	
	
	/**
	 * Buy a recruit and move them to the Heroes
	 * @param h hero
	 */
	public void buyHero(Hero h) {
		if(Money >= h.getValue()) {
			Recruits.remove(h);
			Money -= h.getValue();
			Heroes.add(h);
		}
		ShownRecruits.remove(h);

		
	}
	
	/**
	 * If the guild contains the hero and the guild
	 * has enough money and the hero is not assigned,
	 * the Hero is leveled up
	 * @param h hero
	 */
	public void levelHero(Hero h) {
		if (Heroes.contains(h) && Money >= h.getValue() && !Assign.containsValue(h)) {
			Heroes.remove(h);
			Money -= h.getValue();
			h.LevelUp();
			Heroes.add(h);
		}
		ShownHeroes.remove(h);
			
	}
	
	/**
	 * Adds a Hero to current available recruits
	 * @param h Hero
	 */
	public void newRecruit(Hero h) {
		Recruits.add(h);
	}
	
	/**
	 * @return recruits count
	 */
	public int recruitCount() {
		return Recruits.size();
	}
	
	
	/**
	 * Progresses the quest.
	 * If the quest is complete, it removes the assignment, freeing up the Hero
	 * and the Guild gains the rewards for the quest
	 * If the time runs out, the quest fails, freeing up the Hero, but the Guild loses Fame
	 * If the Hero dies on his adventures the quest fails as well
	 * @param Quest
	 */
	private void progressQuest(Quest q) {
		q.Increment(Assign.get(q).Progression());
		if(q.isComplete()) {
			succeedQuest(q);
		}else if(!q.isIntime(calendar.getTime())){
			failQuest(q);
		}else if(Assign.get(q).isDead()) {
			Heroes.remove(Assign.get(q));
			failQuest(q);
		}
	}
	
	
	/**
	 * removes quest and scolds the guild (- fame)
	 * @param q quest
	 */
	private void failQuest(Quest q) {
		Assign.remove(q);
		Fame -= q.getThreatLevel();
		Quests.remove(q);
	}
	
	/**
	 * removes quest and rewards the guild
	 * @param q Quest
	 */
	private void succeedQuest(Quest q) {
		Money += q.getReward();
		Assign.remove(q).LevelUp();
		Fame += q.getThreatLevel();
		Quests.remove(q);
	}
	
	
	/**
	 * gets ready for initial display
	 * clears all shown things
	 */
	public void nothingShown() {
		ShownHeroes.clear();
		ShownQuests.clear();
		ShownRecruits.clear();
	}
	

	/**
	 * progresses all the quests
	 */
	@Override
	public void Tick() {
		//Progress all quests
		for(int i = 0; i < Quests.size(); i++) {			
			if(Assign.containsKey(Quests.get(i))) {
				progressQuest(Quests.get(i));
			}
		}
		
	}
	
	
	
	
}
