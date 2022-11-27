package Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * @author bened
 * A class with which I save and reload the game
 */
public class Persistency implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileInputStream isr;
	private ObjectInputStream oisr;

	
	private FileOutputStream osr;
	private ObjectOutputStream oosr;

	
	

	
	Persistency(){
		
	}
	
	public void save(Handler h) {
		
		try {
			osr = new FileOutputStream("Save.ser");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oosr = new ObjectOutputStream(osr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oosr.writeObject(h);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't write mah object :(");
		}
	}
	public Handler load() {
		try {
			isr = new FileInputStream("Save.ser");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oisr = new ObjectInputStream(isr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			Handler handler = (Handler) oisr.readObject();
			return handler;
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Class not found or couldn't open file, anyway its a serializable problem");
		}
		return null;
	}

	
}
