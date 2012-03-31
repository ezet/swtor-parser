package swtor.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import swtor.parser.filter.InputFilter;
import swtor.parser.model.LogEntry;
import swtor.parser.parser.Parser;
import swtor.parser.parser.ParserFactory;
import swtor.parser.utility.Logger;

public class LogParser implements LogParserInterface {

	private Parser parser = ParserFactory.getInstance();;
	private List<LogEntry> log = new ArrayList<>();
	private Path path;
	private List<InputFilter> inputfilters;
	
	public LogParser(String path) {
		this.path = Paths.get(path);
	}

	public LogParser(URI uri) {
		this.path = Paths.get(uri);
	}

	public LogParser(Path path) {
		this.path = path;
	}

	public void parse() throws IOException {
		int size = estimateSize();
		System.out.println(size);
		log = new ArrayList<>(size);
		String line;
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
			// TODO implement static sizing by size
			int currentLine = -1;
			while ((line = reader.readLine()) != null) {
				LogEntry entry = new LogEntry(++currentLine);
				parser.parse(entry, line);
				// TODO process input filters
				log.add(entry);
				// TODO process output filters
				Logger.log(entry);
			}
		}
	}

	private int estimateSize() {
		int lines = 100;
		try {
			return count();
		} catch (IOException e) {
			// fail silently
		}
		return lines;
	}

	@SuppressWarnings("unused")
	private int countChar(String filename) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
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
		}
	}

	private int count() throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
			int lines = -1;
			while (reader.readLine() != null)
				++lines;
			return lines;
		}
	}

}
