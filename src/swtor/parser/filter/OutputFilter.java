package swtor.parser.filter;

import swtor.parser.model.LogEntry;

public interface OutputFilter extends Filter {

	public void process(LogEntry entry);

}
