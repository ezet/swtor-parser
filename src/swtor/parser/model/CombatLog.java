package swtor.parser.model;

import java.util.List;

public class CombatLog {
	
	private final List<LogEntry> entries;
	
	private String fileName;
	private String date;

	public CombatLog(List<LogEntry> entries) {
		this.entries = entries;
	}
	
	public CombatLog(List<LogEntry> entries, String fileName) {
		this(entries);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<LogEntry> getEntries() {
		return entries;
	}
	
	
}
