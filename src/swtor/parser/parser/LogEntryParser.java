package swtor.parser.parser;

import swtor.parser.model.LogEntry;

public interface LogEntryParser {
	
	public void parse(LogEntry entry, String line);

}
