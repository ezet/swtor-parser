package swtor.parser.exception;

public class LogParserException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String line;
	private final String fileName;
	private final int lineNumber;

	public LogParserException(String fileName, int lineNumber, String line) {
		super("Could not parse line " + lineNumber + ": " + line);
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.line = line;
	}

	public LogParserException(String fileName, int lineNumber, String line, Throwable cause) {
		this(fileName, lineNumber, line);
		initCause(cause);
	}

	public String getLine() {
		return line;
	}

	public String getFileName() {
		return fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

}
