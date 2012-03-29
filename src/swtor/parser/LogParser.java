package swtor.parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import swtor.parser.model.LogEntry;
import swtor.parser.parser.Parser;
import swtor.parser.parser.ParserFactory;

public class LogParser implements LogParserInterface {

	private InputStreamReader isr;
	private BufferedReader bReader;
	private Parser parser;
	private List<LogEntry> log;

	public LogParser(InputStream is) {
		isr = new InputStreamReader(is);
		bReader = new BufferedReader(isr);
		parser = ParserFactory.getInstance();
	}

	public LogParser(String filePath) {

	}

	public void parse() throws IOException {
//		int lines = count();
		log = new ArrayList<LogEntry>();
		// TODO implement static sizing by lines or size
		String line;
		int currentLine = -1;
		while ((line = bReader.readLine()) != null) {
			LogEntry entry = new LogEntry(++currentLine);
			parser.parse(entry, line);
			log.add(entry);
		}
		bReader.close();
	}

	private int countChar(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			while ((readChars = is.read(c)) != -1) {
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
			}
			return count;
		} finally {
			is.close();
		}
	}

	private int count() throws IOException {
		BufferedReader reader = new BufferedReader(isr);
		int lines = -1;
		while (reader.readLine() != null)
			++lines;
		return lines;
	}

}
