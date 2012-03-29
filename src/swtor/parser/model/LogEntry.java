package swtor.parser.model;

import java.util.Calendar;

public class LogEntry {

	public enum LogEntryType {
		DAMAGE_DONE, HEALING_DONE, DAMAGE_RECEIVED, HEALING_RECEIVED, OTHER
	}

	private final long lineNumber;
	private LogEntryType type;
	private Calendar time;
	private Actor source;
	private Actor target;
	private Ability ability;
	private Event event;
	private Result result;
	
	public LogEntry(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public long getLineNumber() {
		return lineNumber;
	}

	public LogEntryType getType() {
		return type;
	}

	public void setType(LogEntryType type) {
		this.type = type;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public Actor getSource() {
		return source;
	}

	public void setSource(Actor source) {
		this.source = source;
	}

	public Actor getTarget() {
		return target;
	}

	public void setTarget(Actor target) {
		this.target = target;
	}

	public Ability getAbility() {
		return ability;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
