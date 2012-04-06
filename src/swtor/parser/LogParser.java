package swtor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import swtor.parser.exception.LogParserException;
import swtor.parser.filter.InputFilter;
import swtor.parser.filter.NullAbilityFilter;
import swtor.parser.filter.OutputFilter;
import swtor.parser.model.CombatLog;
import swtor.parser.model.LogEntry;
import swtor.parser.parser.LogEntryParser;
import swtor.parser.parser.ParserFactory;
import swtor.parser.util.Logger;

public class LogParser implements Parser {

	private LogEntryParser parser = ParserFactory.getInstance();
	private CombatLog combatLog;
	private File file;
	private final List<InputFilter> inputFilters = new ArrayList<InputFilter>();
	private final List<OutputFilter> outputFilters = new ArrayList<OutputFilter>();
	
	{
		addInputFilter(new NullAbilityFilter());
	}
	
	public LogParser() {
		
	}

	public LogParser(String path) {
		setSource(path);
	}

	public LogParser(URI uri) {
		setSource(uri);
	}

	public LogParser(File file) {
		setSource(file);
	}
	
	public void setSource(String path) {
		this.file = new File(path);
	}
	
	public void setSource(URI uri) {
		this.file = new File(uri);
	}
	
	public void setSource(File file) {
		this.file = file;
	}

	public CombatLog getCombatLog() {
		return combatLog;
	}

	@Override
	public void addInputFilter(InputFilter filter) {
		inputFilters.add(filter);
	}

	@Override
	public void removeInputFilter(InputFilter filter) {
		inputFilters.remove(filter);
	}

	@Override
	public void clearInputFilters() {
		inputFilters.clear();
	}

	@Override
	public void addOutputFilter(OutputFilter filter) {
		outputFilters.add(filter);
	}

	@Override
	public void removeOutputFilter(OutputFilter filter) {
		outputFilters.remove(filter);
	}

	@Override
	public void clearOutputFilters() {
		outputFilters.clear();
	}
	
	public LogEntryParser getParser() {
		return parser;
	}
	
	public void setParser(LogEntryParser parser) {
		this.parser = parser;
	}

	@Override
	public void parse() throws IOException, LogParserException {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		int size = estimateLines();
		long start = System.currentTimeMillis();
		Logger.debug("parsing file: ", file);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file), (int) (file.length() < 8192 ? file.length() : 8192));
			entries = new ArrayList<LogEntry>(size);
			int currentLine = 0;
			String line;
			fireLogStart();
			while ((line = reader.readLine()) != null) {
				Logger.debug(3, "input: " + line);
				LogEntry entry = new LogEntry(++currentLine);
				try {
					parser.parse(entry, line);
				} catch (Exception e) {
					throw new LogParserException(file.getAbsolutePath(), currentLine, line, e);
				}
				if (processInputFilters(entry)) {
					processOutputFilters(entry);
					entries.add(entry);
				}
				Logger.debug(3, "output: ", entry);
			}
		} finally {
			if (reader != null)
				reader.close();
		}
		fireLogEnd();
		long end = System.currentTimeMillis();
		Logger.debug("parse completed in: " + (end - start) + " ms.");
		combatLog = new CombatLog(entries, file.getName());
	}
	
	private void fireLogStart() {
		for (final InputFilter f : inputFilters) {
			f.onLogStart();
		}
		for (final OutputFilter f : outputFilters) {
			f.onLogStart();
		}
	}
	
	private void fireLogEnd() {
		for (final InputFilter f : inputFilters) {
			f.onLogEnd();
		}
		for (final OutputFilter f : outputFilters) {
			f.onLogEnd();
		}
	}

	private boolean processInputFilters(LogEntry entry) {
		boolean include = true;
		for (final InputFilter i : inputFilters) {
			include = i.process(entry);
			if (!include) {
				break;
			}
		}
		return include;
	}

	private void processOutputFilters(LogEntry entry) {
		for (final OutputFilter f : outputFilters) {
			f.process(entry);
		}

	}

	private int estimateLines() {
		long size = file.length();
		return (int) (size / 175L);
	}

}
