package swtor.parser.parser;

public class ParserFactory {
	
	public static LogEntryParser getInstance() {
		// TODO implement
		return new RegexParser();
//		return new SafeParser();
//		return new SplitParser();
	}

}
