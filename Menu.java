package Game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * @author bened
 * Main menu and start of the game
 * A reaaly basic Swing application to load teh game or create new game
 */
public class Menu {
	Persistency p;
	boolean startnew;
	volatile boolean inMenu;
	
	
	Menu(){
		p = new Persistency();
		startnew = true;
		inMenu = true;
		JFrame main = new JFrame();
		main.setSize(500,200);
		BorderLayout bl = new BorderLayout();
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		main.setLayout(bl);
		pan2.setLayout(new FlowLayout());
		JTextPane txt = new JTextPane();
		txt.setEditable(false);
		txt.setText("Would you like to start a new game or load an existing one?");
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(e -> {main.dispose(); startnew = true; inMenu = false; }); 

		
		
		JButton loadBtn = new JButton("Load Game");
		loadBtn.addActionListener(e -> {main.dispose(); startnew = false; inMenu = false;}); 
		
		
		pan1.add(txt);
		pan2.add(newGame);
		pan2.add(loadBtn);
		
		main.add(pan1, BorderLayout.CENTER);
		main.add(pan2, BorderLayout.SOUTH);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		
	}
	
	public void doit(Handler h1) {
		Visual display = new Visual(h1);
		display.init();
		while(true) {
			display.refresh();
		}
	}
	
	public static void main(String[] args) {
		Menu m = new Menu();	
		synchronized(m) {
			while(m.inMenu) {
				try {
					m.wait(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Handler h1 = m.startnew? new Handler(): m.p.load();
		Visual display = new Visual(h1);
		display.init();
		while(true) {
			display.refresh();
		}
	}
	
}
