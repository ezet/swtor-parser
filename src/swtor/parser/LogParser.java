package swtor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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
	private List<LogEntry> log = new ArrayList<LogEntry>();
	// private Path path;
	private File file;
	private List<InputFilter> inputfilters = new ArrayList<InputFilter>();

	public LogParser(String path) {
		// this.path = Paths.get(path);
		this.file = new File(path);
	}

	public LogParser(URI uri) {
		// this.path = Paths.get(uri);
		this.file = new File(uri);
	}
	
	public LogParser(File file) {
		this.file = file;
	}

	// public LogParser(Path path) {
	// this.path = path;
	// }

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
		 Logger.debug("parsing file: " + file);
//		 try (BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.defaultCharset())) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file), (int) (file.length() < 8192 ? file.length() : 8192));
			log = new ArrayList<LogEntry>(size);
			int currentLine = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				Logger.debug(3, "input: " + line);
				LogEntry entry = new LogEntry(++currentLine);
				parser.parse(entry, line);
				if (processInputFilters(entry)) {
					processOutputFilters(entry);
					log.add(entry);
				}
				Logger.debug(3, "output: ", entry);
			}
		} finally {
			if (reader != null)
				reader.close();
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
		// long size = Files.size(path);
		long size = file.length();
		return (int) (size / 175L);
	}

}
