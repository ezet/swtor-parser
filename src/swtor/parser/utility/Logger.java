package swtor.parser.utility;

public class Logger {

	private static boolean debug = false;
	private static int debugLevel = 0;

	public static void setDebugLevel(int level) {
		debugLevel = level;
	}

	public static void log(Object s) {
		System.out.println(s);
	}

	public static void log(Object[] arr) {
		for (Object o : arr)
			System.out.println(o);
	}

	public static void debug(Object o) {
		if (debug)
			log(o);
	}

	public static void debug(Object[] arr) {
		if (debug) {
			log(arr);
		}
	}

	public static void debug(Object s, int level) {
		if (level >= debugLevel) {
			System.out.println(s);
		}
	}

}
