package swtor.parser.parser;

import swtor.parser.model.LogEntry;

public interface Parser {
	
	public void parse(LogEntry entry, String line);

}
