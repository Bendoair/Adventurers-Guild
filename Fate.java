package Game;
import java.io.Serializable;
import java.util.Random;

/**
 * @author bened
 * Fate creates heroes
 */
public class Fate implements Serializable, Tickable{
	/**
	 * EclipseGoesBrr
	 */
	private static final long serialVersionUID = 1L;
	
	private int Stamper;
	private Calendar calendar;
	
	
	Fate(Calendar t){
		Stamper = 0;
		calendar = t;
	}
	
	/**
	 * Creates a Hero with random attributes, one of which will be higher, than average
	 * @return new Hero
	 */
	public Hero createHero() {
		Random randgen = new Random();
		
		double[] Attr = new double[4];
		int BestAttr = 0;
		for (int i = 0; i < Attr.length; i++) {
			Attr[i] = randgen.nextInt(3)+1;
			if(Attr[i] > Attr[BestAttr])
				BestAttr = i;
		}
		Attr[BestAttr] *= calendar.getTime()/100+1.5;
		
		
		//Naming not properly implemented
		Stamper++;
		String name = "Hero"+Integer.toString(Stamper);
		
		int Level = (int)calendar.getTime()/365+1;
		
		
		return new Hero(Stamper, name, Level, Attr[0], Attr[1], Attr[2], Attr[3]);
	}

	@Override
	public void Tick() {
		calendar.Tick();
		
	}
	
	
	
	
	
	
}
