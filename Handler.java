package Game;


import java.io.Serializable;


/**
 * @author bened
 * The class, who ties together all the others.
 * This class is also responsible for the GUI.
 * This class has the main method.
 */
public class Handler implements Tickable, Serializable{
	/**
	 * Eclipsegoesbrr
	 */
	private static final long serialVersionUID = 1L;
	
	private Guild playerGuild;
	private QuestBoard board;
	private Fate fate;
	private Calendar time;

	
	
	
	
	
	
	
	/**
	 * Constructor
	 * Creates the main windows for playing the game
	 * These windows will be filled up with each tick
	 */
	Handler(){
		time = new Calendar();
		fate = new Fate(time);
		playerGuild = new Guild(time);
		board = new QuestBoard(time);
	}
	
	
	
	public Guild getGuild() {return playerGuild;}
	public Fate getfate() {return fate;}
	public QuestBoard getBoard() {return board;}
	
	
	public void newQuest() {board.newQuest();}
	public boolean boardIsEmpty() {return board.QuestCount() == 0? true:false;}
	public Hero createHero() {return fate.createHero();}
	
	/**
	 * Ticks the tickable elements
	 */
	@Override
	public void Tick() {
		time.Tick();
		playerGuild.Tick();
	}
	

	/**
	 * Initial load. 
	 * Clears the shown things, so the display and the internal logic line up
	 */
	public void init() {
		playerGuild.nothingShown();
		board.nothingShown();
	}
	
}
