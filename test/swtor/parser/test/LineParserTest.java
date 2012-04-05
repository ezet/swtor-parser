package swtor.parser.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swtor.parser.model.LogEntry;
import swtor.parser.parser.DefaultParser;
import swtor.parser.parser.LogEntryParser;
import swtor.parser.parser.SafeParser;
import swtor.parser.parser.SplitParser;

public class LineParserTest {

	private LogEntry entry;
	private String line;

	private LogEntryParser simple = new SplitParser();
	private LogEntryParser regex = new SafeParser();
	private LogEntryParser basic = new DefaultParser();

	@Before
	public void Before() {
		entry = new LogEntry(1);
		line = "";
	}

	@Test
	public void testDamageDone() {
		line = "[03/17/2012 19:49:20] [@Psyfe] [@Argorash] [Series of Shots (burning) {2299572734918656}] [ApplyEffect {836045448945477}: Damage [Tech] (burnig) {836045448945501}] (234 energy {836045448940874} -glance {836045448945509} (234 absorbed {836045448945511})) <234>";
		parse();
	}

	@Test
	public void testHealingReceived() {
		line = "[03/17/2012 19:48:14] [@Brockly] [@Argorash] [Kolto Missile {985514605805568}] [ApplyEffect {836045448945477}: Heal {836045448945500}] (1024) <214>";
		parse();
	}

	@Test
	public void testApplyEffect() {
		line = "[03/18/2012 20:27:57] [Hybrid Rakghoul Techmaster {2898192391733248}] [@Evianir] [Smoke Grenade {2848396540903424}] [ApplyEffect {836045448945477}: Accuracy Reduced [Tech] {2848396540903685}] ()";
		parse();
		assertEquals("Threat", entry.getResult().getThreatDelta(), 0);
	}

	@Test
	public void testRemoveEffect() {
		line = "[03/17/2012 19:30:34] [@Argorash] [@Argorash] [Personal Vehicle {2176247043981312}] [RemoveEffect {836045448945478}: Personal Vehicle {2176247043981312}] ()";
		parse();
	}
	
	@Test
	public void testThis() {
		line = "[03/17/2012 20:51:05] [@Exalted] [Annihilation Droid XRR-3 {2034573252755456}] [] [Event {836045448945472}: ModifyThreat {836045448945483}] () <-3817>";
		parse();
	}

	private void validate() {

	}

	private void parse() {
		try {
			basic.parse(entry, line);
//			simple.parse(entry, line);
			regex.parse(entry, line);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
