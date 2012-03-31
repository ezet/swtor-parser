/**
 * 
 */
package swtor.parser.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
		Path path = Paths.get("D:/Dev/Projects/SwtorParser/SampleLogs/sample.txt");
		System.out.println(path.toAbsolutePath().toString());
		System.out.println(Files.isReadable(path));
		try {
			new LogParser(path).parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented"); // TODO
	}

}
