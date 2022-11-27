package Game;

import java.io.Serializable;

/**
 * @author bened
 * Calendar is used for timekeeping in the program
 */
public class Calendar implements Tickable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int currTime = 0;
	
	
	public int getTime() {return currTime;}
	
	/**
	 * Increments current time
	 */
	@Override
	public void Tick() {
		currTime++;
	}

	
}
