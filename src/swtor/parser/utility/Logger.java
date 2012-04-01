package swtor.parser.utility;

import swtor.parser.model.LogEntry;

public class Logger {

	private static boolean debug = false;

	public static void log(LogEntry entry) {
		log(entry.toString());
	}

	public static void log(String s) {
		System.out.println(s);
	}

	public static void debug(LogEntry entry) {
		if (debug)
			System.out.println(entry);
	}

	public static void debug(String[] arr) {
		if (debug) {
			for (String s : arr)
				System.out.println(s);
		}
	}

	public static void debug(String s) {
		if (debug)
			System.out.println(s);
	}

}
