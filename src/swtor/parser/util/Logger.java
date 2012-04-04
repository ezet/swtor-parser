package swtor.parser.util;

public class Logger {

	private static int debugLevel = 0;

	public static void setDebugLevel(int level) {
		debugLevel = level;
	}

	public static void log(Object s) {
		System.out.println(s);
	}

	public static void log(Object... arr) {
		for (Object o : arr)
			System.out.println(o);
	}

	public static void debug(Object obj) {
		if (debugLevel > 0)
			log(obj);
	}

	public static void debug(Object... arr) {
		if (debugLevel > 0) {
			log(arr);
		}
	}

	public static void debug(int level, Object obj) {
		if (debugLevel >= level) {
			log(obj);
		}
	}

	public static void debug(int level, Object... arr) {
		if (debugLevel >= level) {
			log(arr);
		}
	}

}
