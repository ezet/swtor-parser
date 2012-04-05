package swtor.parser.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swtor.parser.constant.DamageType;
import swtor.parser.constant.EntryType;
import swtor.parser.constant.EventType;
import swtor.parser.constant.MitigationType;
import swtor.parser.model.Actor;
import swtor.parser.model.CombatEvent;
import swtor.parser.model.LogEntry;
import swtor.parser.model.Result;

public class DefaultParser implements LogEntryParser {

	private LogEntry entry;

	private final static String entryRegex = "\\[(.*?)\\]|\\((.*?)\\)+|<.*>";
	private final static Pattern entryPattern = Pattern.compile(entryRegex);

	private final static String eventRegex = "\\[(.*?):(.*)\\]";
	private final static Pattern eventPattern = Pattern.compile(eventRegex);

	private final static String resultRegex = "^(.*?)(?:-(.*?))?(?:\\((.*)\\))?$";
	private final static Pattern resultPattern = Pattern.compile(resultRegex);

	private final static String idRegex = "^(.+?)(?> \\{(\\d*)\\})? *$";
	private final static Pattern idPattern = Pattern.compile(idRegex);

	private final static String valueRegex = "^(\\d+?)[\\*\\- ]*(?:(\\w+) \\{(\\d*)\\})? *$";
	private final static Pattern valuePattern = Pattern.compile(valueRegex);

	@Override
	public void parse(LogEntry entry, String line) throws Exception {
		this.entry = entry;
		Matcher m = entryPattern.matcher(line);
		m.find();
		parseTimestamp(m.group(1));
		m.find();
		parseActor(entry.getSource(), m.group(1));
		m.find();
		parseActor(entry.getTarget(), m.group(1));
		m.find();
		parseAbility(m.group(1));
		m.usePattern(eventPattern);
		m.find();
		parseEventType(m.group(1));
		parseEventName(m.group(2));
		m.usePattern(resultPattern);
		if (m.find()) {
			parseResult(m.group(3));
		}
		m.usePattern(entryPattern);
		if (m.find()) {
			parseThreatDelta(m.group(1));
		}
	}

	private void parseTimestamp(String part) throws ParseException {
		// TODO optimize, takes about 1.2sec.. why ? Do it manually ?
		SimpleDateFormat format;
		if (part.contains(" ")) {
			format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		} else {
			format = new SimpleDateFormat("HH:mm:ss.S");
		}
		Date time = format.parse(part);
		entry.getTime().setTime(time);
	}

	private void parseActor(Actor actor, String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			String name = m.group(1);
			actor.setName(name);
			if (!name.isEmpty() && name.contains(":")) {
				actor.setCompanion(true);
			}
			if (m.group(2) != null) {
				actor.setGameId(Long.valueOf(m.group(2)));
			} else {
				actor.setPlayer(true);
			}
		}
	}

	private void parseAbility(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			entry.getAbility().setName(m.group(1));
			if (m.group(2) != null) {
				entry.getAbility().setGameId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseEventType(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			CombatEvent event = entry.getEvent();
			event.setType(EventType.valueOfString(m.group(1)));
			if (m.group(2) != null) {
				event.setTypeId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseEventName(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			entry.getEvent().setName(m.group(1));
			entry.setType(EntryType.valueOfString(m.group(1)));
			if (m.group(2) != null) {
				entry.getEvent().setGameId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseResult(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher resultMatcher = resultPattern.matcher(part);
			resultMatcher.matches();
			parseValue(resultMatcher.group(1));
			parseMitigate(resultMatcher.group(2));
			parseAbsorb(resultMatcher.group(3));
		}
	}

	private void parseValue(String part) {
		Matcher m = valuePattern.matcher(part);
		m.matches();
		Result res = entry.getResult();
		res.setValue(Integer.valueOf(m.group(1)));
		if (part.contains("*"))
			res.setCritical(true);
		if (m.group(2) != null) {
			res.setDamageType(DamageType.valueOf(m.group(2).toUpperCase()));
		}
	}

	private void parseMitigate(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			if (m.group(1) != null) {
				entry.getResult().setMitigationType(MitigationType.valueOf(m.group(1).toUpperCase()));
			}
			if (m.group(2) != null) {
				entry.getResult().setMitigateGameId(Long.valueOf(m.group(2).toUpperCase()));
			}
		}
	}

	private void parseAbsorb(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = valuePattern.matcher(part);
			m.matches();
			entry.getResult().setAbsorbValue(Integer.valueOf(m.group(1)));
			entry.getResult().setAbsorbId(Long.valueOf(m.group(3)));
			entry.getResult().setAbsorb(true);
		}
	}

	private void parseThreatDelta(String part) {
		if (part != null && !part.isEmpty()) {
			entry.getResult().setThreatDelta(Integer.valueOf(part));
		}
	}
}
