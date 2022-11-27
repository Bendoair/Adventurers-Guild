package Game;

import org.junit.*;

public class GuildTest {
	Guild playerGuild;
	Calendar cl;
	Fate f1;
	
	@Before
	public void init() {
		cl = new Calendar();
		f1 = new Fate(cl);
		playerGuild = new Guild(cl);
	}
	
	@Test
	public void addHero() {
		Hero h1 = f1.createHero();
		playerGuild.addHero(h1);
		Assert.assertTrue(playerGuild.getHeroesToDisplay().contains(h1));
	}
	@Test
	public void addRecruit() {
		Hero h1 = f1.createHero();
		playerGuild.newRecruit(h1);
		Assert.assertTrue(playerGuild.getRecruitsToDisplay().contains(h1));
	}
	
	@Test
	public void addQuest() {
		Quest q1 = new Quest(1, 2, 3, 55, 230, "Eat orange");
		playerGuild.addQuest(q1);
		Assert.assertTrue(playerGuild.getQuestNotAssigned().contains(q1));
	}
	
	@Test
	public void assignQuest() {
		Quest q1 = new Quest(1, 2, 3, 55, 230, "Eat orange");
		playerGuild.addQuest(q1);
		Assert.assertTrue(playerGuild.getQuestNotAssigned().contains(q1));
		Hero h1 = f1.createHero();
		playerGuild.addHero(h1);
		
		playerGuild.assignHero(q1, h1);
		
		Assert.assertFalse(playerGuild.getQuestNotAssigned().contains(q1));

	}
}
