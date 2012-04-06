package swtor.parser.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import swtor.parser.constant.EffectType;
import swtor.parser.constant.EntryType;
import swtor.parser.constant.EventType;
import swtor.parser.constant.MitigationType;
import swtor.parser.model.LogEntry;
import swtor.parser.util.Logger;

public class SplitParser implements LogEntryParser {

	private LogEntry entry;
	private static Pattern objectSeparator;
	private static Pattern propertySeparator;
	private static Pattern resultSplit;
	private static Pattern idSplit;

	static {
		objectSeparator = Pattern.compile("[\\]<] ?");
		propertySeparator = Pattern.compile(" ?[{}][ :]*");
		idSplit = Pattern.compile(" \\{");
		resultSplit = Pattern.compile(" ");
	}

	public void parse(LogEntry entry, String line) throws Exception {
		this.entry = entry;
		String[] parts = objectSeparator.split(line.replace("[", ""));
		parseTimestamp(parts[0]);
		parseSource(parts[1]);
		parseTarget(parts[2]);
		parseAbility(parts[3]);
		parseEvent(parts[4]);
		parseResult(parts[5]);
		if (parts.length > 6) {
			parseThreatDelta(parts[6]);
		}
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

	private void parseSource(String string) {
		if (!string.isEmpty()) {
			String[] parts = idSplit.split(string);
			entry.setSource(parts[0].trim());
			if (parts.length > 1) {
				if (parts[0].contains(":")) {
					entry.setSourceIsCompanion(true);
				}
				entry.setSourceId(Long.valueOf(parts[1].substring(0, parts[1].length() - 1)));
			} else {
				entry.setSourceIsPlayer(true);
			}
		}
	}

	private void parseTarget(String string) {
		if (!string.isEmpty()) {
			String[] parts = idSplit.split(string);
			entry.setTarget(parts[0].trim());
			if (parts.length > 1) {
				if (parts[0].contains(":")) {
					entry.setTargetIsCompanion(true);
				}
				entry.setTargetId(Long.valueOf(parts[1].substring(0, parts[1].length() - 1)));
			} else {
				entry.setTargetIsPlayer(true);
			}
		}
	}

	private void parseAbility(String part) {
		if (!part.isEmpty()) {
			String[] parts = idSplit.split(part);
			entry.setAbility(parts[0].trim());
			entry.setAbilityId(Long.valueOf(parts[1].substring(0, parts[1].length() - 1)));
		}
	}

	private void parseEvent(String part) {
		if (!part.isEmpty()) {
			String parts[] = propertySeparator.split(part);
			entry.setEventTypeId(Long.valueOf(parts[1]));
			entry.setEventType(EventType.valueOfId(entry.getEventTypeId(), parts[0]));
			entry.setEventName(parts[2]);
			entry.setEventId(Long.valueOf(parts[3]));
			entry.setType(EntryType.valueOfId(entry.getEventId(), parts[2]));
		}
	}

	private void parseResult(String part) {
		if (part.length() > 3) {
			String[] parts = resultSplit.split(part);
			long id = 0;
			String value = parts[0].replaceAll("\\)", "");
			if (parts[0].charAt(value.length() - 1) == '*') {
				entry.setCritical(true);
				entry.setValue(Integer.valueOf(value.substring(1, value.length() - 1)));
			} else {
				entry.setValue(Integer.valueOf(value.substring(1, value.length())));

			}
			if (parts.length > 2) {
				id = Long.valueOf(parts[2].substring(1, parts[2].length() - 1));
				if (parts[1].charAt(0) == '-') {
					entry.setMitigationId(id);
					entry.setMitigationType(MitigationType.valueOfId(entry.getMitigationId(), parts[1].substring(1).toUpperCase()));
				} else {
					entry.setEffectType(EffectType.valueOf(parts[1].toUpperCase()));
					entry.setEffectId(id);
				}
			}
			if (parts.length > 3) {
				if (parts[3].charAt(0) == '-') {
					id = Long.valueOf(parts[4].substring(1, parts[4].length() - 1));
					entry.setMitigationId(id);
					entry.setMitigationType(MitigationType.valueOfId(entry.getMitigationId(), parts[3].substring(1).toUpperCase()));
				} else {
					entry.setAbsorb(true);
					entry.setAbsorbValue(Integer.valueOf(parts[3].substring(1)));
					id = Long.valueOf(parts[5].substring(1, parts[5].indexOf('}')));
					entry.setAbsorbId(id);
				}
			}
			if (parts.length > 6) {
				entry.setAbsorb(true);
				entry.setAbsorbValue(Integer.valueOf(parts[5].substring(1)));
				id = Long.valueOf(parts[7].substring(1, parts[7].indexOf('}')));
				entry.setAbsorbId(id);
			}

		}

	}

	private void parseThreatDelta(String part) {
		if (!part.isEmpty()) {
			int dThreat = Integer.valueOf(part.substring(0, part.length() - 1));
			entry.setThreatDelta(dThreat);
		}
	}
}
