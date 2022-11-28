package Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


/**
 * @author bened
 * Visual representations of application
 */
public class Visual{

	
	
	private Handler handler;
	
	//Randomness
	private Timer timer;
	private Random Dice;

	
	//Swing things
	private String currentView;
	private JComboBox<String> views;
	private JFrame ViewF;
	private JTextArea GuildInfo;
	
	private JPanel panel_cont;
	private JPanel questBoard;
	
	private JPanel heroesBoard;
	private JPanel heroesBoardLeft;
	private JPanel heroesBoardRight;
	
	private JPanel assignBoard;
	
	private CardLayout cl;
	private FlowLayout fl;
	
	private JPanel MainFrame;
	
	////Action Listeners for the buttons
	private ActionListener QABAL;
	private ActionListener HHBAL;
	private ActionListener HLBAL;
	private ActionListener HABAL;
	

	//Saving
	Persistency p;
	
	/**
	 * @author bened
	 * Changes current view based on the little combo box
	 */
	public class ViewChanger implements ActionListener, Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent ae) {
			currentView = (String)views.getSelectedItem();
			
			cl.show(panel_cont, currentView);
			
		}
	}
	
	
	
	/**
	 * @author bened
	 * Button that Accepts a quest from the quest board
	 */
	private class QuestAcceptButtonActionListener implements ActionListener, Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			QuestPanel sourcePanel = (QuestPanel) ((PanelButton)e.getSource()).getPanel();
			//JPanel is removed, no need to cast it
			questBoard.remove( ((PanelButton)e.getSource()).getPanel() );
			Quest questAccepted = sourcePanel.getQuest();
			//remove from quest board available quests
			handler.getBoard().removeQuest(questAccepted);
			//Add to players' quests
			handler.getGuild().addQuest(questAccepted);
			handleQuestBoard();
			questBoard.repaint();
			
		}
		
	}
	
	/**
	 * @author bened
	 * Button that hires a Hero from the Hero board
	 */
	private class HeroHireButtonActionListener implements ActionListener, Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			HeroHirePanel sourcePanel = (HeroHirePanel) ((PanelButton) e.getSource()).getPanel();
			heroesBoardRight.remove(sourcePanel);
			handler.getGuild().buyHero(sourcePanel.getHero());
			handleHeroes();
		}
		
	}
	
	
	/**
	 * @author bened
	 * Button that levels up  a hero 
	 */
	private class HeroLevelButtonActionListener implements ActionListener, Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		
		public void actionPerformed(ActionEvent e) {
			HeroPanel sourcePanel = (HeroPanel) ((PanelButton) e.getSource()).getPanel();
			
			heroesBoardLeft.remove(sourcePanel);

			handler.getGuild().levelHero(sourcePanel.getHero());
			handleHeroes();
			heroesBoardLeft.repaint();
		}
		
	}
	
	/**
	 * @author bened
	 * A button that assigns a hero to a quest
	 */
	private class HeroAssignButtonActionListener implements ActionListener, Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		
		public void actionPerformed(ActionEvent e) {
			QuestAssignPanel sourcePanel = (QuestAssignPanel) ((PanelButton) e.getSource()).getPanel();
			
			assignBoard.remove(sourcePanel);
			handler.getGuild().assignByName(sourcePanel.getQuest(), sourcePanel.getWhomToAssign());
			assignBoard.repaint();
			handleAssigner();

			
		}
		
	}
	
	
	
	
	
	
	
	Visual(Handler handlerToShow){
		
		handler = handlerToShow;
		
		p = new Persistency();
		timer = new Timer();
		Dice = new Random();
		
		QABAL = new QuestAcceptButtonActionListener();
		HHBAL = new HeroHireButtonActionListener();
		HLBAL = new HeroLevelButtonActionListener();
		HABAL = new HeroAssignButtonActionListener();
		
		fl = new FlowLayout();
		
		
		ViewF = new JFrame("Adventurers Guild");
		ViewF.setSize(1280, 900);
		ViewF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//InformationalFrame setup (views, stats etc.)
		MainFrame = new JPanel();
		MainFrame.setSize(100, 200);
		MainFrame.setPreferredSize(MainFrame.getSize());
		
		GuildInfo = new JTextArea();
		GuildInfo.setPreferredSize(new Dimension(200, 100));
		GuildInfo.setEditable(false);
		updateGuildInfo();
		
		//Combo box to select the view
		views = new JComboBox<String>();
		views.setBounds(50, 50,150,20);
		views.setPreferredSize(views.getSize());
		views.addItem("Questboard");
		views.addItem("Heroes");
		views.addItem("Hero Assigner");
		views.addActionListener(new ViewChanger());
		currentView = (String)views.getSelectedItem();
		
		
		JButton exitbutton = new JButton("SaveAndQuit");
		exitbutton.addActionListener(e -> {p.save(handler); ViewF.dispose(); System.exit(0);});
		
		
		
		MainFrame.setLayout(new FlowLayout());
		MainFrame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		MainFrame.add(GuildInfo);
		MainFrame.add(views);
		MainFrame.add(exitbutton);
		
		
		//Menu bar that is needed but unnecessary
		JMenuBar kell = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem help = new JMenuItem("Help");
		menu.add(help);
		kell.add(menu);
		ViewF.setJMenuBar(kell);
		help.addActionListener(e -> help());
		
		


		
		
		ViewF.setVisible(true);
		
		
		
		//Handling the three views
		
		cl = new CardLayout();
		
		panel_cont = new JPanel();
		panel_cont.setSize(ViewF.getSize());
		panel_cont.setLayout(cl);
		
		
		questBoard = new JPanel();
		questBoard.setSize(ViewF.getSize());
		questBoard.setBackground(Color.blue);
		
		heroesBoard = new JPanel();
		heroesBoard.setSize(ViewF.getSize());
		heroesBoard.setBackground(Color.green);
		
		
		assignBoard = new JPanel();
		assignBoard.setSize(ViewF.getSize());
		assignBoard.setBackground(Color.ORANGE);
		
		
		
		
		
		
		panel_cont.add(assignBoard, "Hero Assigner");
		panel_cont.add(heroesBoard, "Heroes");
		panel_cont.add(questBoard, "Questboard");
		
		cl.show(panel_cont, "Questboard");
		
		setUpHeroView();
		ViewF.add(MainFrame, BorderLayout.PAGE_START);
		ViewF.add(panel_cont, BorderLayout.CENTER);
		ViewF.setVisible(true);
	}
	
	

	/**
	 * Updates the text field representing the information about the guild
	 */
	private void updateGuildInfo() {
		GuildInfo.setText(handler.getGuild().getInfo());
	}
	
	private void setUpHeroView() {
		heroesBoardLeft = new JPanel();
		heroesBoardLeft.setSize((1280/2), 720);
		heroesBoardLeft.setMaximumSize(heroesBoardLeft.getSize());

		heroesBoardLeft.setVisible(true);
		heroesBoardRight = new JPanel();
		heroesBoardRight.setSize((1280/2), 720);
		heroesBoardRight.setMaximumSize(heroesBoardRight.getSize());

		heroesBoardRight.setBackground(Color.BLACK);
		heroesBoardRight.setVisible(true);
				
		heroesBoardRight.setLayout(new BoxLayout(heroesBoardRight, BoxLayout.PAGE_AXIS));
		heroesBoardLeft.setLayout(new BoxLayout(heroesBoardLeft, BoxLayout.PAGE_AXIS));

		
		JScrollPane rightscroll = new JScrollPane(heroesBoardRight);
		rightscroll.setPreferredSize(heroesBoardRight.getSize());
		rightscroll.getVerticalScrollBar().setUnitIncrement(20);
		JScrollPane leftscroll = new JScrollPane(heroesBoardLeft);
		leftscroll.setPreferredSize(heroesBoardLeft.getSize());
		leftscroll.getVerticalScrollBar().setUnitIncrement(20);


		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftscroll, rightscroll);
		
		heroesBoard.add(splitPane, BorderLayout.CENTER);
	}
	
	/**
	 * Initializes the visuals
	 * clears everything shown in the handlers internal logic
	 */
	public void init() {
		MainFrame.setVisible(true);
		ViewF.setVisible(true);
		handler.init();
		
		TimerTask toDoTick = new TimerTask() { 
			@Override public void run() {
				handler.Tick();
				if(handler.getGuild().recruitCount() < 3) {
					handler.getGuild().newRecruit(handler.createHero());
				}
				
				if(Dice.nextDouble() < 0.04 || handler.boardIsEmpty()) {
					handler.newQuest();
				}
			}
		};
		timer.schedule(toDoTick, 0, 1000);
	}
	
	public void help() {
		JFrame helpframe = new JFrame("Help");
		JTextArea info = new JTextArea();
		info.setEditable(false);
		info.setBackground(Color.pink);
		info.setText("Use the mouse to click on the GUI.\n"
				+ "You can switch between views using the little box near the save button!\n"
				+ "Quest Panel: Accept quests, but be vary of their deadline!\n"
				+ "Hero Panel: Hire Heroes or upgrade existing ones!\n"
				+ "Assign Panel: Assign Heroes to quests. Type in the name of the Hero and click assign!");
		helpframe.add(info);
		
		helpframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		helpframe.pack();
		helpframe.setVisible(true);
	}
	
	
	/**
	 * Heroes board display
	 */
	public void handleHeroes() {
		//Displaying owned heroes
		ArrayList<Hero> myHeroesToDisplay = handler.getGuild().getHeroesToDisplay();
		for(int i = 0; i < myHeroesToDisplay.size(); i++) {
			heroesBoardLeft.add(new HeroPanel(myHeroesToDisplay.get(i), HLBAL));
//			handler.getGuild().notifyHeroDisplayed(myHeroesToDisplay.get(i));

		}
		
		
		//Displaying the recruits
		ArrayList<Hero> myRecruitsToDisplay = handler.getGuild().getRecruitsToDisplay();
		for(int i = 0; i < myRecruitsToDisplay.size(); i++) {
			heroesBoardRight.add(new HeroHirePanel(myRecruitsToDisplay.get(i), HHBAL));
//			handler.getGuild().notifyRecruitDisplayed(myRecruitsToDisplay.get(i));
		}
	}
	
	/**
	 * Quest board display
	 */
	public void handleQuestBoard() {
		
		questBoard.setLayout(fl);		
		ArrayList<Quest> questsToShow = handler.getBoard().getQuestsToDisplay();
		for(int i = 0; i < questsToShow.size(); i++) {
			questBoard.add(new QuestPanel(questsToShow.get(i), QABAL));
		}
		
		
	}

	/**
	 * Assigner board display
	 */
	public void handleAssigner() {
		assignBoard.setLayout(fl);
		ArrayList<Quest> questsToShow = handler.getGuild().getQuestNotAssigned();
		for(int i = 0; i < questsToShow.size(); i++) {
			assignBoard.add(new QuestAssignPanel(questsToShow.get(i), HABAL));
		}
	}

	/**
	 * Refreshes each frame
	 */
	public void refresh(){
		handleHeroes();
		handleQuestBoard();
		handleAssigner();
		updateGuildInfo();
	}
	
		
	
}

