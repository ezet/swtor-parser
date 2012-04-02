/**
 * 
 */
package swtor.parser.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		Path path;
		path = Paths.get("D:/Dev/Projects/SwtorParser/SampleLogs/");
		try (DirectoryStream<Path> dir = Files.newDirectoryStream(path);) {
			for (Path p : dir) {
				Logger.log(p);
				new LogParser(p).parse();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO error");
		}
		
	}
}
