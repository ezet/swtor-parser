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
	private List<InputFilter> inputfilters = new ArrayList<>();

	public LogParser(String path) {
		this.path = Paths.get(path);
	}

	public LogParser(URI uri) {
		this.path = Paths.get(uri);
	}

	public LogParser(Path path) {
		this.path = path;
	}

	public void addInputFilter(InputFilter filter) {
		inputfilters.add(filter);
	}

	public void removeInputFilter(InputFilter filter) {
		inputfilters.remove(filter);
	}

	public void clearInputFilters() {
		inputfilters.clear();
	}

	public void parse() throws IOException {
		int size = estimateSize();
		System.out.println(size);
		System.out.println(count());
		log = new ArrayList<>(size);
		String line;
		long start = System.currentTimeMillis();
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
			// TODO implement static sizing by size

			int currentLine = -1;
			while ((line = reader.readLine()) != null) {
				Logger.debug(line);
				LogEntry entry = new LogEntry(++currentLine);
				parser.parse(entry, line);
				if (processInputFilters(entry)) {
					processOutputFilters(entry);
					log.add(entry);
				}
				Logger.debug(entry);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms.");
	}

	private void processOutputFilters(LogEntry entry) {

	}

	private boolean processInputFilters(LogEntry entry) {
		boolean result = true;
		for (InputFilter i : inputfilters) {
			result = i.process(entry);
			if (!result) {
				break;
			}
		}
		return result;
	}

	private int estimateSize() {
		int lines = 10000;
		try {
			long size = Files.size(path);
			return (int) (size / 170L);
		} catch (IOException e) {
			// fail silently
		}
		return lines;
	}

	private int countChar() throws IOException {
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
