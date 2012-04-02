package swtor.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import swtor.parser.filter.InputFilter;
import swtor.parser.filter.NullAbilityFilter;
import swtor.parser.model.LogEntry;
import swtor.parser.parser.LogEntryParser;
import swtor.parser.parser.ParserFactory;
import swtor.parser.utility.Logger;

public class LogParser implements Parser {

	private LogEntryParser parser = ParserFactory.getInstance();;
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

	public List<LogEntry> getLog() {
		return log;
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
		addInputFilter(new NullAbilityFilter());
		int size = estimateLines();
		long start = System.currentTimeMillis();
		Logger.debug("parsing file: " + path);
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
			log = new ArrayList<>(size);
			int currentLine = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				Logger.debug("input: " + line);
				LogEntry entry = new LogEntry(++currentLine);
				parser.parse(entry, line);
				if (processInputFilters(entry)) {
					processOutputFilters(entry);
					log.add(entry);
				}
				Logger.debug("output: " + entry);
			}
		}
		long end = System.currentTimeMillis();
		Logger.debug("parse completed in: " + (end - start) + " ms.");
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

	private int estimateLines() {
		int lines = 10000;
		try {
			long size = Files.size(path);
			return (int) (size / 170L);
		} catch (IOException e) {
			// fail silently
		}
		return lines;
	}

}
