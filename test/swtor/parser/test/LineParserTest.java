package swtor.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import swtor.parser.model.LogEntry;
import swtor.parser.parser.Parser;
import swtor.parser.parser.RegexParser;
import swtor.parser.parser.SimpleParser;

public class LineParserTest {

	LogEntry entry;

	Parser regex = new RegexParser();
	Parser simple = new SimpleParser();

	@Before
	public void Before() {
		entry = new LogEntry(1);
	}

	@Test
	public void testDamageDone() {
		String line = "[03/17/2012 19:49:20] [@Psyfe] [@Argorash] [Series of Shots {2299572734918656}] [ApplyEffect {836045448945477}: Damage {836045448945501}] (234 energy {836045448940874} -glance {836045448945509} (234 absorbed {836045448945511})) <234>";
		simple.parse(entry, line);
	}

	@Test
//	@Ignore
	public void testHealingReceived() {
		String line = "[03/17/2012 19:48:14] [@Brockly] [@Argorash] [Kolto Missile {985514605805568}] [ApplyEffect {836045448945477}: Heal {836045448945500}] (1024) <214>";
		simple.parse(entry, line);
	}

	@Test
//	@Ignore
	public void testApplyEffect() {
		String line = "[03/17/2012 19:32:14] [@Argorash] [@Argorash] [Personal Vehicle {2176247043981312}] [ApplyEffect {836045448945477}: Personal Vehicle {2176247043981312}] ()";
		simple.parse(entry, line);
		assertEquals("Threat", entry.getResult().getThreatDelta(), 0);
	}

	@Test
//	@Ignore
	public void testRemoveEffect() {
		String string = "[03/17/2012 19:30:34] [@Argorash] [@Argorash] [Personal Vehicle {2176247043981312}] [RemoveEffect {836045448945478}: Personal Vehicle {2176247043981312}] ()";
	}

}
