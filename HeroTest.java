package Game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeroTest {
	Hero h1;
	Hero h2;
	double[] stats1;
	double[] stats2;
	
	@Before
	public void init() {
		h1 = new Hero(1, "Lajos", 1, 1, 3, 2, 0.88);
		h2 = new Hero(2, "Gabika", 1, 4, 1, 1, 0.88);
		stats1 = new double[] {1, 3, 2};
		stats2 = new double[] {4, 1, 1};
	}
	
	@Test
	public void correctStats() {
		Assert.assertArrayEquals(h1.Progression(), stats1, 0);
		Assert.assertArrayEquals(h2.Progression(), stats2, 0);
	}
	
	@Test
	public void diffSkills() {
		Assert.assertNotEquals(h1.Progression(), h2.Progression());
	}
	
	@Test
	public void canLevelUp() {
		Hero h1beforeLevel = h1;
		h1.LevelUp();
		Assert.assertNotEquals(h1beforeLevel.Progression(), h1.Progression());
	}
}
