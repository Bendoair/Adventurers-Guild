package Game;

import org.junit.*;

public class FateTest {
	Fate f1;
	Calendar c1;
	
	@Before
	public void init() {
		c1 = new Calendar();
		f1 = new Fate(c1);
	}
	
	@Test
	public void diffHeroes() {
		Hero h1 = f1.createHero();
		Hero h2 = f1.createHero();
		Assert.assertNotEquals(h1, h2);
	}
	
	
	@Test
	public void valueTest() {
		Hero h1 = f1.createHero();
		
		for(int i = 0; i < 10000; i++) {
			c1.Tick();
		}
		
		//Later the Heroes should be more valuable
		
		Hero h2 = f1.createHero();
		Assert.assertTrue(h2.getValue() > h1.getValue());
		
	}
}
