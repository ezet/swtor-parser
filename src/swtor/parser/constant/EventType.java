package swtor.parser.constant;

public enum EventType {
	REMOVE_EFFECT, APPLY_EFFECT, SPEND, RESTORE, EVENT, UNKNOWN;

	public static EventType valueOfString(String string) {
		EventType type = UNKNOWN;
		if (string.equals("Event")) {
			type = EVENT;
		} else if (string.equals("ApplyEffect")) {
			type = APPLY_EFFECT;
		} else if (string.equals("RemoveEffect")) {
			type = REMOVE_EFFECT;
		} else if (string.equals("Spend")) {
			type = SPEND;
		} else if (string.equals("RESTORE")) {
			type = RESTORE;
		}
		return type;
	}
}