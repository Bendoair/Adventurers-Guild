package Game;

import org.junit.*;

public class ClockTest {
	Calendar c1;
	
	@Before
	public void init() {
		c1 = new Calendar();
	}
	@Test
	public void ticksGood() {
		int i = 0;
		Assert.assertEquals(i, c1.getTime());
		
		while(i <= 10) {
			c1.Tick();
			i++;
		}
		Assert.assertEquals(i, c1.getTime());
	}
}
