package swtor.parser.model;

import java.text.DateFormat;
import java.util.Calendar;

import swtor.parser.constant.EntryType;

public class LogEntry implements Model {

	private EntryType type;
	private final long lineNumber;
	private Calendar time = Calendar.getInstance();
	private Actor source = new Actor();
	private Actor target = new Actor();
	private Ability ability = new Ability();
	private CombatEvent event = new CombatEvent();
	private Result result = new Result();

	public LogEntry(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public long getLineNumber() {
		return lineNumber;
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

	public CombatEvent getEvent() {
		return event;
	}

	public void setEvent(CombatEvent event) {
		this.event = event;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String toString() {
		return String.format("ln:%s, time:%s, src:%s, trgt:%s, %s, %s, %s", lineNumber, DateFormat.getTimeInstance().format(time.getTime()), source, target, ability, event, result);
	}

}
