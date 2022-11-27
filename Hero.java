package Game;

import java.io.Serializable;

/**
 * @author bened
 * Hero chosen by fate with a specific strengths and weaknesses 
 * (Intelligence, Strength, Dexterity and probability of doing something dangerous)
 * 
 */
public class Hero implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int Id;
	private String Name;
	private int Level;
	private double Int;
	private double Str;
	private double Dex;
	private double Danger;
	
	
	
	
	
	/**
	 * Constructor for Heroes
	 * @param id
	 * @param nam
	 * @param Init_Int
	 * @param Init_Str
	 * @param Init_Dex
	 * @param Init_Danger
	 */
	Hero(int id,  String nam, int Init_Level, double Init_Int, double Init_Str, double Init_Dex, double Init_Danger){
		Id = id;
		Name = nam;
		Int = Init_Int;
		Str = Init_Str;
		Dex = Init_Dex;
		Danger = Init_Danger;
		Level = Init_Level;
	}
	
	@Override
	public String toString() {
		return Name + "\n\n" + "Int: " + Int + "\n" + "Strength: " + Str + "\n" + "Dexterity: " + Dex ;
		}
	
	
	/**
	 * This method levels up characters, increasing their stats and making them more reliable
	 */
	public void LevelUp() {
		Danger *= 0.9;
		Int += (1.02)*Int;
		Str += (1.02)*Str;
		Dex += (1.02)*Dex;
		Level++;
	}
	
	/**
	 * Returns true if the Hero is dead (his Id is changed to 0)
	 * @return Aliveness of hero
	 */
	public boolean isDead() {return Id == 0?true:false;}
	
	/**
	 * Basically a getter for all stats of the Hero
	 * @return [Int, Str, Dex]
	 */
	public double[] Progression(){
		//I didn't implement the danger factor yet. The hero might die, in that case he shouldn't progress the quest at all
		
		double[] ret = new double[3];
		ret[0] = Int;
		ret[1] = Str;
		ret[2] = Dex;
		return ret;
	}
	
	public int getValue() {return Level*10;}
	public String getName() {return Name;}

}
