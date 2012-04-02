package swtor.parser.filter;

import swtor.parser.model.LogEntry;

public interface InputFilter extends Filter {
	
	public boolean process(LogEntry entry);

}
