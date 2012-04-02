package swtor.parser.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import swtor.parser.constant.DamageType;
import swtor.parser.constant.EntryType;
import swtor.parser.constant.EventType;
import swtor.parser.constant.MitigationType;
import swtor.parser.model.Actor;
import swtor.parser.model.CombatEvent;
import swtor.parser.model.LogEntry;
import swtor.parser.model.Result;

public class SimpleParser implements LogEntryParser {

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

	public SimpleParser() {

	}

	public void parse(LogEntry entry, String line) {
		this.entry = entry;
		String[] parts = objectSeparator.split(line.replace("[", ""));
		parseTimestamp(parts[0]);
		parseActor(entry.getSource(), parts[1]);
		parseActor(entry.getTarget(), parts[2]);
		parseAbility(parts[3]);
		parseEvent(parts[4]);
		parseResult(parts[5]);
		if (parts.length > 6) {
			parseThreatDelta(parts[6]);
		}
	}

	private void parseTimestamp(String part) {
		SimpleDateFormat format;
		if (part.contains(" ")) {
			format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		} else {
			format = new SimpleDateFormat("HH:mm:ss.S");
		}
		try {
			Date time = format.parse(part);
			entry.getTime().setTime(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void parseActor(Actor actor, String string) {
		if (!string.isEmpty()) {
			String[] parts = idSplit.split(string);
			actor.setName(parts[0].trim());
			if (parts.length > 1) {
				actor.setGameId(Long.valueOf(parts[1].substring(0, parts[1].length() - 1)));
				actor.setNpc(true);
			}
		}
	}

	private void parseAbility(String part) {
		if (!part.isEmpty()) {
			String[] parts = idSplit.split(part);
			entry.getAbility().setName(parts[0].trim());
			entry.getAbility().setGameId(Long.valueOf(parts[1].substring(0, parts[1].length() - 1)));
		}
	}

	private void parseEvent(String part) {
		if (!part.isEmpty()) {
			CombatEvent event = entry.getEvent();
			String parts[] = propertySeparator.split(part);
			event.setType(EventType.valueOfString(parts[0]));
			event.setTypeId(Long.valueOf(parts[1]));
			event.setName(parts[2]);
			event.setGameId(Long.valueOf(parts[3]));
			entry.setType(EntryType.valueOfString(parts[2]));
		}
	}

	private void parseResult(String part) {
		if (part.length() > 3) {
			String[] parts = resultSplit.split(part);
			Result res = entry.getResult();
			long id = 0;
			String value = parts[0].replaceAll("\\)", "");
			if (parts[0].charAt(value.length() - 1) == '*') {
				res.setCritical(true);
				res.setValue(Integer.valueOf(value.substring(1, value.length() - 1)));
			} else {
				res.setValue(Integer.valueOf(value.substring(1, value.length())));

			}
			if (parts.length > 2) {
				id = Long.valueOf(parts[2].substring(1, parts[2].length() - 2));
				if (parts[1].charAt(0) == '-') {
					res.setMitigationType(MitigationType.valueOf(parts[1].substring(1).toUpperCase()));
					res.setMitigateId(id);
				} else {
					res.setEffectType(DamageType.valueOf(parts[1].toUpperCase()));
					res.setEffectId(id);
				}
			}
			if (parts.length > 3) {
				if (parts[3].charAt(0) == '-') {
					id = Long.valueOf(parts[2].substring(1, parts[2].length() - 2));
					res.setMitigationType(MitigationType.valueOf(parts[3].substring(1).toUpperCase()));
					res.setMitigateId(id);
				} else {
					res.setAbsorb(true);
					res.setAbsorbValue(Integer.valueOf(parts[3].substring(1)));
					id = Long.valueOf(parts[5].substring(1, parts[5].indexOf('}')));
					res.setAbsorbId(id);
				}
			}
			if (parts.length > 6) {
				res.setAbsorb(true);
				res.setAbsorbValue(Integer.valueOf(parts[5].substring(1)));
				id = Long.valueOf(parts[7].substring(1, parts[7].indexOf('}')));
				res.setAbsorbId(id);
			}

		}

	}

	private void parseThreatDelta(String part) {
		if (!part.isEmpty()) {
			int dThreat = Integer.valueOf(part.substring(0, part.length() - 1));
			entry.getResult().setThreatDelta(dThreat);
		}
	}
}
