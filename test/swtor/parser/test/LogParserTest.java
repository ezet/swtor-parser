/**
 * 
 */
package swtor.parser.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import swtor.parser.LogParser;
import swtor.parser.exception.LogParserException;
import swtor.parser.util.Logger;

/**
 * @author Lars Kristian
 * 
 */
public class LogParserTest {

	@Test
	public void test() {
		try {
			File file = new File("D:/Dev/Projects/SwtorParser/SampleLogs/");
			// file = new
			// File("D:/Dev/Projects/SwtorParser/SampleLogs/combat_2012-03-17_10_39_06_966767.txt");
			// file = new
			// File("D:/Dev/Projects/SwtorParser/SampleLogs/huge.txt");
			// new LogParser(file).parse();

			for (File p : file.listFiles()) {
				Logger.log(p);
				new LogParser(p).parse();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LogParserException e) {
			e.printStackTrace();
			fail("test failed");
			// TODO Auto-generated catch block
		}

	}
}
