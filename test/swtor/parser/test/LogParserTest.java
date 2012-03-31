/**
 * 
 */
package swtor.parser.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import swtor.parser.LogParser;

/**
 * @author Lars Kristian
 *
 */
public class LogParserTest {

	@Test
	public void test() throws URISyntaxException {
		Path path;
		path = Paths.get("D:/Dev/Projects/SwtorParser/SampleLogs/combat_2012-03-17_10_39_06_966767.txt");
		try {
			new LogParser(path).parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Not yet implemented", true); // TODO
	}

}
