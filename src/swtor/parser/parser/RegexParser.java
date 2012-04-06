package swtor.parser.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swtor.parser.constant.EffectType;
import swtor.parser.constant.EntryType;
import swtor.parser.constant.EventType;
import swtor.parser.constant.MitigationType;
import swtor.parser.model.LogEntry;

public class RegexParser implements LogEntryParser {

	private LogEntry entry;

	private final static String entryRegex = "\\[(.*?)\\]|<(.*)>";
	private final static Pattern entryPattern = Pattern.compile(entryRegex);

	private final String eventRegex = "\\[(.*?): (.*)\\]";
	private final Pattern eventPattern = Pattern.compile(eventRegex);

	private final static String resultRegex = "\\((.*?)(?:-(.*?))?(?:\\((.*)\\))?\\)";
	private final static Pattern resultPattern = Pattern.compile(resultRegex);

	private final static String idRegex = "^(.*?)(?: *\\{(\\d*)\\})? *$";
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
		parseActor(m.group(1), 0);
		m.find();
		parseActor(m.group(1), 1);
		m.find();
		parseAbility(m.group(1));
		m.usePattern(eventPattern);
		m.find();
		parseEventType(m.group(1));
		parseEventName(m.group(2));
		m.usePattern(resultPattern);
		m.find();
		parseValue(m.group(1));
		parseMitigate(m.group(2));
		parseAbsorb(m.group(3));
		m.usePattern(entryPattern);
		if (m.find()) {
			parseThreatDelta(m.group(2));
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

	private void parseActor(String part, int actor) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.find();
			String name = m.group(1);
			if (actor == 0) {
				entry.setSource(name);
				if (!name.isEmpty() && name.contains(":")) {
					entry.setSourceIsCompanion(true);
				}
				if (m.group(2) != null) {
					entry.setSourceId(Long.valueOf(m.group(2)));
				} else {
					entry.setSourceIsPlayer(true);
				}
			} else {
				entry.setTarget(name);
				if (!name.isEmpty() && name.contains(":")) {
					entry.setTargetIsCompanion(true);
				}
				if (m.group(2) != null) {
					entry.setTargetId(Long.valueOf(m.group(2)));
				} else {
					entry.setTargetIsPlayer(true);
				}
			}

		}
	}

	private void parseAbility(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.find();
			entry.setAbility(m.group(1));
			if (m.group(2) != null) {
				entry.setAbilityId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseEventType(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.find();
			entry.setEventType(EventType.valueOfString(m.group(1)));
			if (m.group(2) != null) {
				entry.setEventTypeId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseEventName(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.find();
			entry.setEventName(m.group(1));
			entry.setType(EntryType.valueOfString(m.group(1)));
			if (m.group(2) != null) {
				entry.setEventId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseValue(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = valuePattern.matcher(part);
			m.find();
			entry.setValue(Integer.valueOf(m.group(1)));
			if (part.contains("*"))
				entry.setCritical(true);
			if (m.group(2) != null) {
				entry.setEffectType(EffectType.valueOf(m.group(2).toUpperCase()));
			}
		}
	}

	private void parseMitigate(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.find();
			if (m.group(1) != null) {
				entry.setMitigationType(MitigationType.valueOf(m.group(1).toUpperCase()));
			}
			if (m.group(2) != null) {
				entry.setMitigateGameId(Long.valueOf(m.group(2).toUpperCase()));
			}
		}
	}

	private void parseAbsorb(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = valuePattern.matcher(part);
			m.find();
			entry.setAbsorbValue(Integer.valueOf(m.group(1)));
			entry.setAbsorbId(Long.valueOf(m.group(3)));
			entry.setAbsorb(true);
		}
	}

	private void parseThreatDelta(String part) {
		if (part != null && !part.isEmpty()) {
			entry.setThreatDelta(Integer.valueOf(part));
		}
	}
}
