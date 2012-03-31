package swtor.parser.parser;

import java.util.regex.Pattern;

import swtor.parser.model.Actor;
import swtor.parser.model.CombatEvent;
import swtor.parser.model.CombatEvent.EventType;
import swtor.parser.model.LogEntry;
import swtor.parser.model.Result;
import swtor.parser.model.Result.EffectType;
import swtor.parser.model.Result.MitigationType;

public class SimpleParser implements Parser {

	private LogEntry entry;
	private static Pattern objectSeparator;
	private static Pattern propertySeparator;
	private static Pattern blankSeparator;

	static {
		objectSeparator = Pattern.compile("[(\\] )(\\) )]");
		objectSeparator = Pattern.compile("\\] |\\) ");

		objectSeparator = Pattern.compile("[\\]\\)>] ?");
		propertySeparator = Pattern.compile("[{:]");
		blankSeparator = Pattern.compile(" ");
	}

	private void print(String[] parts) {
		for (String s : parts) {
			System.out.println(s);
		}
	}

	public SimpleParser() {

	}

	public void parse(LogEntry entry, String line) {
		this.entry = entry;
		String[] parts = objectSeparator.split(line.replace("[", ""));
		print(parts);
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
	}

	private void parseActor(Actor actor, String string) {
		if (!string.isEmpty()) {
			string = string.replace("}", "");
			String[] parts = propertySeparator.split(string);
			actor.setName(parts[0].trim());
			if (parts.length > 1) {
				actor.setGameId(Long.valueOf(parts[1]));
				actor.setNpc(true);
			}
		}
	}

	private void parseAbility(String part) {
		if (!part.isEmpty()) {
			part = part.replace("}", "");
			String[] parts = propertySeparator.split(part);
			entry.getAbility().setName(parts[0].trim());
			entry.getAbility().setGameId(Long.valueOf(parts[1]));
		}
	}

	private void parseEvent(String part) {
		if (!part.isEmpty()) {
			CombatEvent event = entry.getEvent();
			part = part.replace("}", "");
			String parts[] = propertySeparator.split(part);
			event.setType(EventType.valueOfString(parts[0].trim()));
			event.setTypeId(Long.valueOf(parts[1]));
			event.setName(parts[2].trim());
			event.setGameId(Long.valueOf(parts[3]));
		}
	}

	private void parseResult(String part) {
		if (part.length() > 1) {
			String[] parts = blankSeparator.split(part);
			print(parts);
			Result res = entry.getResult();
			long id = 0;
			if (parts[0].charAt(parts[0].length() - 1) == '*') {
				res.setCritical(true);
				res.setValue(Integer.valueOf(parts[0].substring(1, parts[0].length() - 1)));
			} else {
				res.setValue(Integer.valueOf(parts[0].substring(1)));
				
			}
			if (parts.length > 2) {
				id = Long.valueOf(parts[2].substring(1, parts[2].length() - 2));
				if (parts[1].charAt(0) == '-') {
					res.setMitigationType(MitigationType.valueOf(parts[1].substring(1).toUpperCase()));
					res.setMitigateId(id);
				} else {
					res.setEffectType(EffectType.valueOf(parts[1].toUpperCase()));
					res.setEffectId(id);
				}
			}
			if (parts.length > 4) {
				if (parts[3].charAt(0) == '-') {
					id = Long.valueOf(parts[2].substring(1, parts[2].length() - 2));
					res.setMitigationType(MitigationType.valueOf(parts[3].substring(1).toUpperCase()));
					res.setMitigateId(id);
				} else {
					res.setAbsorb(true);
					res.setAbsorbValue(Integer.valueOf(parts[3].substring(1)));
					id = Long.valueOf(parts[5].substring(1, parts[5].length() - 2));
					res.setAbsorbId(id);
				}
			}
			if (parts.length > 6) {
				res.setAbsorb(true);
				res.setAbsorbValue(Integer.valueOf(parts[5].substring(1)));
				id = Long.valueOf(parts[7].substring(1, parts[7].length() - 2));
				res.setAbsorbId(id);
			}

		}

	}

	private void parseThreatDelta(String part) {
		int dThreat = 0;
		if (part.length() > 2) {
			dThreat = Integer.valueOf(part.substring(1));
		}
		entry.getResult().setThreatDelta(dThreat);
	}
}
