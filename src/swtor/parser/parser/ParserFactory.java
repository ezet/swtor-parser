package swtor.parser.parser;

public class ParserFactory {
	
	public static LogEntryParser getInstance() {
		// TODO implement
//		return new DefaultParser();
		return new SafeParser();
//		return new SimpleParser();
	}

}
