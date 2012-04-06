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

public class SafeParser implements LogEntryParser {

	private LogEntry entry;

	private final static String regex = "\\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*):(.*)\\] \\((.*?)(?:-(.*?))?(?:\\((.*)\\))?\\)(?: <(-?\\d*)>)? *$";
	private final static Pattern entryPattern = Pattern.compile(regex);

	private final static String idRegex = "(.+?)(?: \\{(\\d*)\\})? *$";
	private final static Pattern idPattern = Pattern.compile(idRegex);

	private final static String valueRegex = "^(\\d+?)[\\*\\- ]*(?:(\\w+) \\{(\\d*)\\})? *$";
	private final static Pattern valuePattern = Pattern.compile(valueRegex);

	@Override
	public void parse(LogEntry entry, String line) throws Exception {
		this.entry = entry;
		Matcher m = entryPattern.matcher(line);
		m.matches();
		parseTimestamp(m.group(1));
		parseActor(m.group(2), 0);
		parseActor(m.group(3), 1);
		parseAbility(m.group(4));
		parseEventType(m.group(5));
		parseEventName(m.group(6));
		parseResult(m.group(7));
		parseMitigate(m.group(8));
		parseAbsorb(m.group(9));
		parseThreatDelta(m.group(10));
	}

	private void parseTimestamp(String part) throws ParseException {
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
			m.matches();
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
			m.matches();
			entry.setAbility(m.group(1));
			if (m.group(2) != null) {
				entry.setAbilityId(Long.valueOf(m.group(2)));
			}
		}
	}

	private void parseEventType(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			if (m.group(2) != null) {
				entry.setEventTypeId(Long.valueOf(m.group(2)));
			}
			entry.setEventType(EventType.valueOfId(entry.getEventTypeId(), m.group(1)));
		}
	}

	private void parseEventName(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			entry.setEventName(m.group(1));
			if (m.group(2) != null) {
				entry.setEventId(Long.valueOf(m.group(2)));
				entry.setType(EntryType.valueOfId(entry.getEventId(), m.group(1)));
			}
		}
	}

	private void parseResult(String part) {
		if (part != null && !part.isEmpty()) {

			Matcher m = valuePattern.matcher(part);
			m.matches();
			entry.setValue(Integer.valueOf(m.group(1)));
			if (part.contains("*"))
				entry.setCritical(true);
			if (m.group(2) != null) {
				entry.setEffectId(Long.valueOf(m.group(3)));
				entry.setEffectType(EffectType.valueOfId(entry.getEffectId(), m.group(2)));
			}
		}
	}

	private void parseMitigate(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = idPattern.matcher(part);
			m.matches();
			// if (m.group(1) != null && m.group(2) != null) {
			entry.setMitigationId(Long.valueOf(m.group(2).toUpperCase()));
			entry.setMitigationType(MitigationType.valueOfId(entry.getMitigationId(), m.group(1)));
			// }
		}
	}

	private void parseAbsorb(String part) {
		if (part != null && !part.isEmpty()) {
			Matcher m = valuePattern.matcher(part);
			m.matches();
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
