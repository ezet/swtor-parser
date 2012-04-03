/**
 * 
 */
package swtor.parser.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import swtor.parser.LogParser;
import swtor.parser.utility.Logger;

/**
 * @author Lars Kristian
 * 
 */
public class LogParserTest {

	@Test
	public void test() throws URISyntaxException {
		File p = new File("D:/Dev/Projects/SwtorParser/SampleLogs/huge.txt");
		try {
//			for (File p : file.listFiles()) {
				Logger.log(p);
				new LogParser(p).parse();
//			}
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO error");
		}
		
	}
}
