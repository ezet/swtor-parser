package swtor.parser.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import swtor.parser.parser.Parser;
import swtor.parser.parser.RegexParser;
import swtor.parser.parser.SimpleParser;

public class LineParserTest {

	Parser regex = new RegexParser();
	Parser simple = new SimpleParser();

	@Test
	public void testDamageDone() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testApplyEffect() {
		String string = "[03/17/2012 19:32:14] [@Argorash] [@Argorash] [Personal Vehicle {2176247043981312}] [ApplyEffect {836045448945477}: Personal Vehicle {2176247043981312}] ()";
	}

	@Test
	public void testRemoveEffect() {
		String string = "[03/17/2012 19:30:34] [@Argorash] [@Argorash] [Personal Vehicle {2176247043981312}] [RemoveEffect {836045448945478}: Personal Vehicle {2176247043981312}] ()";
	}

}
