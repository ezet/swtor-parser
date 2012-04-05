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
	private List<InputFilter> inputfilters = new ArrayList<InputFilter>();

	public LogParser(String path) {
		this.file = new File(path);
	}

	public LogParser(URI uri) {
		this.file = new File(uri);
	}

	public LogParser(File file) {
		this.file = file;
	}

	public CombatLog getCombatLog() {
		return combatLog;
	}

	@Override
	public void addInputFilter(InputFilter filter) {
		inputfilters.add(filter);
	}

	@Override
	public void removeInputFilter(InputFilter filter) {
		inputfilters.remove(filter);
	}

	@Override
	public void clearInputFilters() {
		inputfilters.clear();
	}

	@Override
	public void addOutputFilter(OutputFilter filter) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeOutputFilter(OutputFilter filter) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clearOutputFilters() {
		// TODO Auto-generated method stub
	}

	@Override
	public void parse() throws IOException, LogParserException {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		addInputFilter(new NullAbilityFilter());
		int size = estimateLines();
		long start = System.currentTimeMillis();
		Logger.debug("parsing file: ", file);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file), (int) (file.length() < 8192 ? file.length() : 8192));
			entries = new ArrayList<LogEntry>(size);
			int currentLine = 0;
			String line;
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
		long end = System.currentTimeMillis();
		Logger.debug("parse completed in: " + (end - start) + " ms.");
		combatLog = new CombatLog(entries, file.getName());
	}

	private boolean processInputFilters(LogEntry entry) {
		boolean ignore = true;
		for (InputFilter i : inputfilters) {
			ignore = i.process(entry);
			if (!ignore) {
				break;
			}
		}
		return ignore;
	}

	private void processOutputFilters(LogEntry entry) {

	}

	private int estimateLines() {
		long size = file.length();
		return (int) (size / 175L);
	}

}
