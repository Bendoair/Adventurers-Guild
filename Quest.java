package Game;

import java.io.Serializable;

/**
 * @author bened
 * Quest class represents quests completable by heroes
 */
public class Quest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double[] Requirements;
	private int Reward;
	private int Deadline;
	private String Name;
	private int ThreatLevel;
	private double[] Progress;
	
	/**
	 * Basic constructor
	 * @param Int
	 * @param Str
	 * @param Dex
	 * @param Rew
	 * @param Deadl
	 * @param Nam
	 */
	Quest(double Int, double Str, double Dex, int Rew, int Deadl, String Nam){
		Requirements = new double[3];
		Progress = new double[3];
		for(int i = 0; i < Progress.length; i++) {
			Progress[i] = 0;
		}
		
		Requirements[0] = Int;
		Requirements[1] = Str;
		Requirements[2] = Dex;
		Reward = Rew;
		Deadline = Deadl;
		Name = Nam;
		ThreatLevel = (int) (Requirements[0] + Requirements[1] + Requirements[2]);
		Deadline += ThreatLevel*100;
	}
	
	/**
	 * Name getter
	 * @return Name
	 */
	public String getName() { return Name;}
	
	/**
	 * ThreatLevel getter
	 * @return ThreatLevel as String
	 */
	public String getThreatLevelString() { return Integer.toString(ThreatLevel);}
	public int getThreatLevel() {return ThreatLevel;}
	
	public String getDeadLineString() {return Integer.toString(Deadline);}
	
	
	/**
	 * Reward getter
	 * @return Reward
	 */
	public int getReward() {return Reward;}
	
	@Override
	public String toString() {
		return Name + "\n" + "\nThreat Level: " + ThreatLevel + "\nReward: " + Reward;
		}
	
	
	/**
	 * Increments quest progression by stats amount
	 * @param stats
	 */
	public void Increment(double[] stats) {
		for(int i = 0; i < Progress.length; i++) {
			Progress[i] += stats[i];
		}
	}
	
	/**
	 * Returns true is the quest requirements are met by the quest progress
	 * @return Is the quest completed?
	 */
	public boolean isComplete() {
		return (Progress[0] >=Requirements[0] && Progress[1] >=Requirements[1] && Progress[2] >=Requirements[2])? true:false;
	}
	
	/**
	 * Compares the current time with the quest deadline and
	 * returns weather the quest has failed by running out of time
	 * @param currTime
	 * @return Is there still time to complete the quest?
	 */
	public boolean isIntime(int currTime) {
		return (Deadline>=currTime)?true:false;
	}
	
	
	
}
