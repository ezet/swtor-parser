package swtor.parser.utility;

import swtor.parser.model.LogEntry;

public class Logger {
	
	public static void log(LogEntry entry) {
		System.out.println(entry.toString());
		
	}

}
